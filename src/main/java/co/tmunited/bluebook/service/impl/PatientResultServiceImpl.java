package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.OrderStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientResultVO;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.repository.search.PatientResultDetSearchRepository;
import co.tmunited.bluebook.repository.search.PatientResultSearchRepository;
import co.tmunited.bluebook.service.PatientOrderService;
import co.tmunited.bluebook.service.PatientResultService;
import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.service.util.StorageUtil;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * Service Implementation for managing PatientResult.
 */
@Service
@Transactional
public class PatientResultServiceImpl implements PatientResultService {

    private final Logger log = LoggerFactory.getLogger(PatientResultServiceImpl.class);

    @Inject
    private PatientResultRepository patientResultRepository;

    @Inject
    private PatientResultSearchRepository patientResultSearchRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private PatientOrderRepository patientOrderRepository;

    @Inject
    private PatientResultFileRepository patientResultFileRepository;

    @Inject
    private FacilityRepository facilityRepository;

    @Value("${spring.application.name}")
    private String appName;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private UserService userService;

    @Inject
    private PatientOrderService patientOrderService;

    @Inject
    private PatientResultDetSearchRepository patientResultDetSearchRepository;

    @Inject
    private PatientResultDetRepository patientResultDetRepository;

    /**
     * Save a patientResult.
     *
     * @param patientResult the entity to save
     * @return the persisted entity
     */
    public PatientResult save(PatientResult patientResult) {
        log.debug("Request to save PatientResult : {}", patientResult);
        PatientResult result = patientResultRepository.save(patientResult);
        patientResultSearchRepository.save(result);

        return result;
    }

    /**
     * Get all the patientResults.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResult> findAll() {
        log.debug("Request to get all PatientResults");
        List<PatientResult> result = patientResultRepository.findAllByDelStatusIsFalse().stream().map(x -> {
            x.getOrder().getId();
            return x;
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * Get one patientResult by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientResult findOne(Long id) {
        log.debug("Request to get PatientResult : {}", id);
        PatientResult patientResult = patientResultRepository.findOne(id);
        patientResult.getPatientResultFiles().size();
        patientResult.getOrder().getId();

        return patientResult;
    }

    /**
     * Delete the  patientResult by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientResult : {}", id);
        PatientResult patientResult = patientResultRepository.findOne(id);
        patientResult.setDelStatus(true);
        PatientResult result = patientResultRepository.save(patientResult);

        patientResultSearchRepository.save(result);
    }

    /**
     * Search for the patientResult corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResult> search(String query) {
        log.debug("Request to search PatientResults for query {}", query);
        List<PatientResult> patientResults = StreamSupport
            .stream(patientResultSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        List<Long> listIds = patientResults.stream()
            .map(patientResult -> patientResult.getId())
            .collect(Collectors.toList());
        Set<PatientResultDet> patientResultDets = patientResultDetRepository.findAllByResultIds(listIds);

        patientResults.stream().forEach(patientResult -> {
            Set<PatientResultDet> patientResultDets1 = patientResultDets.stream()
                .filter(patientResultDet -> patientResultDet.getPatientResult().getId().equals(patientResult.getId()))
                .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(patientResultDets1)) {
                patientResult.setResultDets(patientResultDets1);
            }
        });
        return patientResults;
    }

    @Transactional(readOnly = true)
    public List<PatientResult> findAllByClinic(Long id, ZonedDateTime starDate, ZonedDateTime endDate) {
        log.debug("Request to get all PatientResults by Clinic " + id);
        List<PatientResult> result = patientResultRepository.findAllByClinic(id, starDate, endDate).stream().map(x -> {
            x.getOrder().getId();

            return x;
        }).collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<PatientResult> findAllByClinic(Long id) {
        log.debug("Request to get all PatientResults by Clinic " + id);
        List<PatientResult> result = patientResultRepository.findAllByClinic(id).stream().map(x -> {
            x.getOrder().getId();

            return x;
        }).collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<PatientResultVO> findAllByPatientId(Long id) {
        List<PatientResultVO> result = null;
        try {
            log.debug("Request to get all findAllByPatientId by patient12 " + id);
            result = patientResultRepository.findAllByPatientId(id);

            List<Long> listIds = result.stream()
                .map(patientResult -> patientResult.getId())
                .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(listIds) && listIds.size() > 0) {
                Set<PatientResultDet> patientResultDets = patientResultDetRepository.findAllByResultIds(listIds);

                result.stream().forEach(patientResult -> {
                    Set<PatientResultDet> patientResultDets1 = patientResultDets.stream()
                        .filter(patientResultDet -> patientResultDet.getPatientResult().getId().equals(patientResult.getId()))
                        .collect(Collectors.toSet());

                    if (!CollectionUtils.isEmpty(patientResultDets1)) {
                        patientResult.setPatientResultDets(patientResultDets1);
                    }
                });
            }

            log.debug("Request to get all findAllByPatientId by patient12 end" + id);
        } catch (Exception ex) {
            log.debug("Error in Request to get all findAllByPatientId by patient12 " + id);
            System.out.print(ex.getStackTrace());
        }

        return result;
    }

    @Transactional
    public PatientResult processPatientResult(PatientResultExtended patientResultExtended) {
        //Process PDF
        try {
            //Generate uuid for storing the file.
//            String uuid = UUID.randomUUID().toString();
            //Create tmp file.
            Path path = Files.createTempFile(patientResultExtended.getFileName(),
                jHipsterProperties.getFileStorage().getFileNameExtension());
            File file = path.toFile();
            // writing file data.
            final byte[] decodedPdf = Base64.getMimeDecoder().decode(patientResultExtended.getBase64Pdf());
            FileUtils.writeByteArrayToFile(file, decodedPdf);
            //Get mime type
            String mimeType = Files.probeContentType(file.toPath());
            log.debug("Process patient result PDF ContentType: " + mimeType);
            if (mimeType == null) {
                mimeType = "application/pdf";
            }
            //Delete the tmp file.
            file.delete();
            PatientResult patientResult = null;
            if (patientResultExtended.getAccessionNumber() != null) {
                //Find if a PatientResult exists.
                patientResult = findOneByAccessionNumber(patientResultExtended.getAccessionNumber());
                //Seek for orderId
                PatientOrder patientOrder = patientOrderRepository.findOrderByBarcode(
                    patientResultExtended.getAccessionNumber());
                if (patientResult != null) {
                    //Update abnormal flag.
                    patientResult.setAbnormal(patientResult.isAbnormal() || patientResultExtended.isAbnormal());
                    //There is a patientResult
                    for (PatientResultDet patientResultDetExt : patientResultExtended.getResultDets()) {
                        if (!existResultDet(patientResultDetExt, patientResult.getResultDets())) {
                            //We add the new result
                            patientResultDetExt.setPatientResult(patientResult);
                            patientResult.getResultDets().add(patientResultDetExt);
                        }
                    }
                    patientResult.setOrder(patientOrder);
                    patientResult = patientResultRepository.save(patientResult);
                    //Update search index
                    patientResultSearchRepository.save(patientResult);
                } else {
                    patientResult = createPatientResult(patientResultExtended, patientOrder);
                    patientResult = patientResultRepository.save(patientResult);
                    //Update search index
                    patientResultSearchRepository.save(patientResult);
                }
            } else {
                patientResult = createPatientResult(patientResultExtended, null);
                patientResult = patientResultRepository.save(patientResult);
                //Update search index
                patientResultSearchRepository.save(patientResult);
            }
            //Prepare File Object for sending to the FS service
            FileDTO fileDTO = new FileDTO();
            fileDTO.setUuid(patientResultExtended.getUuid());
            fileDTO.setName(patientResultExtended.getFileName() +
                jHipsterProperties.getFileStorage().getFileNameExtension());
            fileDTO.setApp(appName);
            fileDTO.setFileContentType(mimeType);
            fileDTO.setFile(patientResultExtended.getBase64Pdf());
            switch (jHipsterProperties.getFileStorage().getType()) {
                case "fileSystem":
                    if (!StorageUtil.storeFileToLocal(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                        jHipsterProperties.getFileStorage().getFileSystem().getProcessedResult() +
                        StorageUtil.generateFolderName(), fileDTO)) {
                        //Can not upload file to FS
                        log.error("Failed to upload file to local FileSystem.");
                    }
                    break;
                case "remote":
                    if (!StorageUtil.storeFileToServer(new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                        jHipsterProperties.getFileStorage().getRemote().getUrlFiles()), fileDTO)) {
                        //Can not upload file to FS
                        log.error("Failed to upload file to FS.");
                    }
                    break;
                default:
                    if (!StorageUtil.storeFileToLocal(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                        jHipsterProperties.getFileStorage().getFileSystem().getProcessedResult() +
                        StorageUtil.generateFolderName(), fileDTO)) {
                        //Can not upload file to FS
                        log.error("Failed to upload file to local FileSystem.");
                    }
                    break;
            }
            //Save file to the DB
            PatientResultFile patientResultFile = new PatientResultFile();
            patientResultFile.setName(patientResultExtended.getFileName() +
                jHipsterProperties.getFileStorage().getFileNameExtension());
            patientResultFile.setUuid(patientResultExtended.getUuid());
            patientResultFile.setPatientResult(patientResult);
            patientResultFileRepository.save(patientResultFile);
            return patientResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PatientResult createPatientResult(PatientResultExtended patientResultExtended, PatientOrder patientOrder) {
        //There is not a patientResult
        PatientResult patientResultTmp = patientResultExtended.getParent();
        //Update abnormal flag.
        patientResultTmp.setAbnormal(patientResultExtended.isAbnormal());
        patientResultTmp.getResultDets().stream().map(x -> {
            x.setPatientResult(patientResultTmp);
            return x;
        }).collect(Collectors.toList());
        patientResultTmp.setOrder(patientOrder);
        return patientResultTmp;
    }

    private void storeFileToServer(FileDTO fileDTO) {

        try {
            if (FileRestClient.getInstance().uploadFile(
                new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                    jHipsterProperties.getFileStorage().getRemote().getUrlFiles()), fileDTO)) {
                //Do something, all is OK
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeFileToLocal(FileDTO fileDTO) {

        try {
            if (FileRestClient.getInstance().uploadFile(
                new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                    jHipsterProperties.getFileStorage().getRemote().getUrlFiles()), fileDTO)) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean existResultDet(PatientResultDet element, Set<PatientResultDet> list) {

        for (PatientResultDet item : list) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public PatientResult findOneByAccessionNumber(String barcode) {
        return patientResultRepository.findOneByAccessionNumber(barcode);
    }

    @Override
    public List<PatientResult> getMonthlyResultByClinic(Long id, String status) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime first = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.of(0, 0, 0));

        return patientResultRepository.getMonthlyResultByClinic(id, first, now, status).stream().map(r -> {
            r.getOrder().getId();

            return r;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PatientResult> getUnassignedResultByClinic(Long id) {
        Facility facility = facilityRepository.findOne(id);

        return patientResultRepository.findAllByAccountAndOrderIdIsNullAndDelStatusIsFalse(facility.getAccount());
    }

    @Override
    @Transactional
    public void assign(CollectedBody collectedBody) {
        Employee employee = userService.getEmployeeWithAuthorities();
        Chart chart = chartRepository.findOne(collectedBody.getPatientId());

        collectedBody.getIds().stream().forEach(
            resultId -> {
                PatientOrder order = new PatientOrder();
                order.setChart(chart);
                order.setEmployee(employee);
                order.setOrdStatus(OrderStatus.GENERATED);
                patientOrderService.save(order);

                PatientResult result = patientResultRepository.findOne(Long.valueOf(resultId));
                result.setOrder(order);
                save(result);
            }
        );
    }

    @Override
    @Transactional
    public List<PatientResult> getCriticalResultByClinic(Long id) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime first = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.of(0, 0, 0));

        return patientResultRepository.getCriticalResultByClinic(id, first, now).stream().map(r -> {
            r.getOrder().getId();

            return r;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PatientResult> getNonPerformTestByClinic(Long id) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime first = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.of(0, 0, 0));

        return patientResultRepository.getNonPerformTestByClinic(id, first, now).stream().map(r -> {
            r.getOrder().getId();

            return r;
        }).collect(Collectors.toList());
    }
}
