package co.tmunited.bluebook.service.impl;


import co.tmunited.bluebook.config.Constants;
import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.PictureVO;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.repository.search.BedSearchRepository;
import co.tmunited.bluebook.repository.search.ChartSearchRepository;
import co.tmunited.bluebook.repository.search.PatientSearchRepository;
import co.tmunited.bluebook.repository.search.PictureSearchRepository;
import co.tmunited.bluebook.service.ChartService;
import co.tmunited.bluebook.service.TypeMaritalStatusService;
import co.tmunited.bluebook.service.util.ImageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * Service Implementation for managing Chart.
 */
@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    private final Logger log = LoggerFactory.getLogger(ChartServiceImpl.class);

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private ChartSearchRepository chartSearchRepository;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private BedRepository bedRepository;

    @Inject
    private AllergiesRepository allergiesRepository;

    @Inject
    private BedSearchRepository bedSearchRepository;

    @Inject
    private PatientSearchRepository patientSearchRepository;

    @Inject
    private TypeMaritalStatusService typeMaritalStatusService;

    @Inject
    private TypeLevelCareRepository typeLevelCareRepository;

    @Inject
    private PictureRepository pictureRepository;

    @Inject
    private PictureSearchRepository pictureSearchRepository;

    /**
     * Save a chart.
     *
     * @param chart the entity to save
     * @return the persisted entity
     */
    public Chart save(Chart chart) {
        log.debug("Request to save Chart : {}", chart);
        Chart result = new Chart();
        try {
            prepareChart(chart);
            Long bedId = null;
            if (chart.getBed() != null) {
//            chart.getBed().setChart(chart);
                Bed bedInDb = null;
                if (chart.getId() != null) {
                    bedInDb = bedRepository.findByChartId(chart.getId());
                }
                //If the bedId is not the same
                if (bedInDb != null && !bedInDb.getId().equals(chart.getBed().getId())) {
                    bedInDb.setChartId(null);
                    bedRepository.save(bedInDb);
                }

                if (bedInDb == null || !(bedInDb.getId().equals(chart.getBed().getId()))) {
                    if (chart.getId() != null) {
                        chart.getBed().setChartId(chart.getId());
                        bedRepository.save(chart.getBed());
                    } else {
                        bedId = chart.getBed().getId();
                        chart.setBed(null);
                    }
                }
            }

            result = chartRepository.save(chart);
            result.setPictureRef(chart.getPictureRef());
            if (bedId != null) {
                Bed bed = bedRepository.findOne(bedId);
                bed.setChartId(chart.getId());
                bedRepository.save(bed);
            }
            chartSearchRepository.save(result);
//            if (chart.getBed() != null) {
//                Bed bed = updateActiveChartInBed(result.getBed().getId(), chart.getId());
//                bedSearchRepository.save(bed);
//            }

            patientSearchRepository.save(result.getPatient());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }

    /**
     * Get all the charts.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Chart> findAll() {
        log.debug("Request to get all Charts");
        List<Chart> result = chartRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     * Get one chart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Chart findOne(Long id) {
        log.debug("Request to get Chart : {}", id);
        Chart chart = chartRepository.findOneWithEagerRelationships(id);
        chart.getInsurances().size();
        chart.setBed(bedRepository.findByChartId(chart.getId()));
//        chart.getIcd10S().size();
        chart.getDrugs().size();
        Set<Allergies> allergies = new HashSet<>(allergiesRepository.findAllByChartId(chart.getId()));
        chart.setAllergies(allergies);
        return chart;
    }

    /**
     * Get one chart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Chart findOneForSchedule(Long id) {
        log.debug("Request to get Chart : {}", id);
        Chart chart = new Chart();
        try {
            chart = chartRepository.findOneForSchedule(id);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return chart;
    }

    /**
     * Delete the  chart by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Chart : {}", id);
        Chart chart = chartRepository.findOneWithEagerRelationships(id);
        chart.setDelStatus(true);

        Chart result = chartRepository.save(chart);


        chartSearchRepository.save(result);
    }

    /**
     * Search for the chart corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Chart> search(String query) {
        log.debug("Request to search Charts for query {}", query);
        return StreamSupport
            .stream(chartSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<Chart> findAllByFacility(Long id) {
        log.debug("Request to get all Charts");
        List<Chart> result = chartRepository.findAllByDelStatusIsFalseAndFacilityId(id);
        result.stream().map(x -> {
            x.getIcd10S().size();
            return x;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Chart> findAllByFacilityWithoutBed(Long id, ZonedDateTime date) {
        log.debug("Request to get all Charts");
//        List<Chart> result = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndBedIdIsNullAndWaitingRoomIsFalseAndDischargeDateGreaterThan(id, date);
        List<Chart> result = chartRepository.findChartsWithoutBed(id, date);
        //Collect all chartIds
        List<Long> listChartIds = result.stream()
            .map(chart -> chart.getId())
            .collect(Collectors.toList());

        if (!listChartIds.isEmpty()) {
            //Get the list of beds with all chartIds
            List<Bed> beds = bedRepository.findAllByChartIds(listChartIds);
            result.stream().forEach(chart -> {
                Optional<Bed> findBed = beds.stream()
                    .filter(bed -> bed.getChartId().equals(chart.getId()))
                    .findFirst();
                if (findBed.isPresent()) {
                    chart.setBed(findBed.get());
                }
            });
        }

        return result;
    }

    @Override
    public List<Chart> findAllByFacilityWaitingRoomFalseAndDischargeDate(Long id, ZonedDateTime date) {
        log.debug("Request to get all patients in Current Patients");
        List<Chart> results = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThan(id, date);

        return results;
    }

    @Override
    public List<ChartVO> findAllByFacilityWaitingRoomFalseAndDischargeDateVO(Long id, ZonedDateTime date) {
        log.debug("Request to get all patients in Current Patients VO");

        List<ChartVO> results = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThanVO(id, date);
        fillChartWithBed(results);
        return results;
    }

    @Override
    public List<ChartVO> findAllByFacilityWaitingRoomFalseAndDischargeDateVOForGroupSession(Long id, ZonedDateTime date) {
        log.debug("Request to get all patients in findAllByFacilityWaitingRoomFalseAndDischargeDateVOForGroupSession");

        List<ChartVO> results = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThanVOForGroupSession(id, date);

        return results;
    }

    @Override
    public List<ChartVO> findAllByFacilityWaitingRoomTrue(Long id) {
        log.debug("Request to get all patients in Waiting Room");
        List<ChartVO> results = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsTrueVO(id);
        fillChartWithBed(results);

        return results;
    }

    public void fillChartWithBed(List<ChartVO> results) {
        //Collect all chartIds
        List<Long> listChartIds = results.stream()
            .map(chartVO -> chartVO.getId())
            .collect(Collectors.toList());
        if (!listChartIds.isEmpty()) {
            //Get the list of beds with all chartIds
            List<Bed> beds = bedRepository.findAllByChartIds(listChartIds);
            results.stream().forEach(chartVO -> {
                Optional<Bed> findBed = beds.stream()
                    .filter(bed -> bed.getChartId().equals(chartVO.getId()))
                    .findFirst();
                if (findBed.isPresent()) {
                    chartVO.setBed(findBed.get().getName());
                }
            });
        }
    }

    @Override
    public List<ChartVO> findAllArchive(Long id, ZonedDateTime date) {
        log.debug("Request to get all patients are Archive");
        List<ChartVO> results = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateLessThanVO(id, date);
//        fillChartWithBed(results);

        return results;
    }

    public List<Chart> findAllMedicationsByDay(Long id) {
//        List<Chart> result = chartRepository.findAllByDelStatusIsFalseAndFacilityId(id);
//        ZonedDateTime now = ZonedDateTime.now();
//        result.removeIf(chart -> chart.getChartToMedications().size() > 0);
//        result.stream().map(chart -> chart.getChartToMedications().removeIf(chartToMed -> chartToMed.getDatePrescription().compareTo(now) == 0));
        return null;
    }

    @Override
    public List<Chart> findAllByPatient(Long facId, Long patId) {
        List<Chart> result = chartRepository.findAllByDelStatusIsFalseAndFacilityIdAndPatientIdOrderByIdDesc(facId, patId);

        return result;
    }

    @Override
    public Page<ChartVO> findAllCharts(Long facId, Pageable page, Map<String, Object> mapQuery) {
        log.debug("findAllCharts info");
        ZonedDateTime now = ZonedDateTime.now();
        Page<ChartVO> result = chartRepository.findAllCharts(facId, now, page);
        log.debug("findAllCharts info end");
        return result;
    }

    @Override
    public Long countChartsExistInCurrentYear(Long id, ZonedDateTime start, ZonedDateTime end) {
        return chartRepository.countByFacilityIdAndCreatedDateBetween(id, start, end);
    }

    public void prepareChart(Chart chart) {
        log.debug("Preparing the Chart for bind the relations");

        if (chart.getTypeMaritalStatus() == null) {
            chart.setTypeMaritalStatus(typeMaritalStatusService.findOne(Long.valueOf(5)));
        }


        if (chart.getInsurances() != null) {
            chart.getInsurances().stream().map(insurance -> {
                insurance.setChart(chart);
                return insurance;
            }).collect(Collectors.toList());
        }

        if (chart.getIcd10S() != null) {
            chart.getIcd10S().stream().map(icd10 -> {
                Set<Chart> charts = new HashSet<>();
                charts.add(chart);
                icd10.setCharts(charts);
                return icd10;
            }).collect(Collectors.toList());
        }

        if (chart.getDrugs() != null) {
            chart.getDrugs().stream().map(drugs -> {
                drugs.getCharts().add(chart);
                return drugs;
            }).collect(Collectors.toList());
        }

        if (chart.getChartToLevelCares() != null) {
            chart.getChartToLevelCares().stream().map(chartToLOC -> {
                chartToLOC.setChart(chart);
                return chartToLOC;
            }).collect(Collectors.toList());
        }

        if (chart.getPatient().getId() != null) {
            Patient patient = patientRepository.findOne(chart.getPatient().getId());
            chart.setPatient(patient);
        }
        if (chart.getId() == null) {
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime firstDay = now.with(TemporalAdjusters.firstDayOfYear()); // XXXX-01-01
            ZonedDateTime lastDay = now.with(TemporalAdjusters.lastDayOfYear()); // XXXX-12-31

            Long count = chartRepository.countByFacilityIdAndCreatedDateBetween(chart.getFacility().getId(), firstDay, lastDay);
            chart.setMrNo("" + now.getYear() + "-" + (count + 1));

        }

        if (chart.getPictureRef() != null && chart.getPictureRef().getId() == null) {
            Picture picture = pictureRepository.save(chart.getPictureRef());
            pictureSearchRepository.save(picture);
            chart.setPictureRef(picture);
        }

        //Update or create front and back insurance picture
        if (CollectionUtils.isNotEmpty(chart.getInsurances())) {
            chart.getInsurances().stream().forEach(insurance -> {
                //Check if the front picture is new
                if (insurance.getFrontPicture() != null) { // && insurance.getFrontPicture().getId() == null
                    Picture pictureSaved = this.storePicture(insurance.getFrontPicture(), insurance, "FRONT");
                    insurance.setFrontPicture(pictureSaved);
                }
                //Check if the back picture is new
                if (insurance.getBackPicture() != null) { // && insurance.getBackPicture().getId() == null
                    //Saving picture
                    Picture pictureSaved = this.storePicture(insurance.getBackPicture(), insurance, "BACK");
                    insurance.setBackPicture(pictureSaved);
                }
            });

        }
    }

    public Picture storePicture(Picture pictureToStore, Insurance insurance, String type) {
        Picture pictureSaved = null;
        //check for delStatus = true
        if (pictureToStore.getDelStatus().equals(true)) {
            Picture basePicture = pictureRepository.getOne(pictureToStore.getId());
            //If the picture isnt the same we have to add the new one and delete the old one
            if (basePicture != null && !basePicture.getPicture().equals(pictureToStore.getPicture())) {
                Picture picture = new Picture();
                picture.setPicture(pictureToStore.getPicture());
                picture.setPictureContentType(pictureToStore.getPictureContentType());
                //Saving picture
                pictureSaved = pictureRepository.save(picture);
                //Save picture to elasticsearch
                pictureSearchRepository.save(pictureSaved);
            } else {
                if (type == "FRONT") {
                    //Set the insurance picture to null
                    insurance.setFrontPicture(new Picture());
                } else {
                    //Set the insurance picture to null
                    insurance.setBackPicture(new Picture());
                }
            }
            //Delete the old one
            pictureRepository.delete(pictureToStore);

        } else {
            //Saving picture
            pictureSaved = pictureRepository.save(pictureToStore);
        }
        //Save picture to elasticsearch
        if (pictureSaved != null) {
            pictureSearchRepository.save(pictureSaved);
        }
        return pictureSaved;
    }

    public Bed updateActiveChartInBed(Long bedId, Long chartId) {
        Bed bed = bedRepository.findOne(bedId);
        bed.setActualChart(chartId);
        Bed bedResult = bedRepository.save(bed);
        return bedResult;
    }

    public void preloadForms(Chart chart) {
        if (chart.getTypeLevelCare() != null && chart.getFacility() != null) {
            List<Forms> automaticForms = typeLevelCareRepository.findAllByFacilityAndLevelCareLoadedAutomatic(chart.getFacility().getId(), chart.getTypeLevelCare().getId());
            automaticForms.stream().map(form -> {
                chart.getChartToForms().add(prepareChartToForm(form));
                return form;
            }).collect(Collectors.toList());
        }
    }

    public ChartToForm prepareChartToForm(Forms form) {

        ChartToForm chartToForm = new ChartToForm();
        chartToForm.setName(form.getName());
        chartToForm.setStatus(FormStatus.Pending);
        chartToForm.setFormId(form.getId());
        chartToForm.setPatientSignatureRequired(form.isPatientSignatureRequired());
        chartToForm.setGuarantorSignatureRequired(form.isGuarantorSignatureRequired());
        chartToForm.setAllowAttachment(form.isAllowAttachment());
        chartToForm.setAllowRevocation(form.isAllowAttachment());
        chartToForm.setExpiresDays(form.getExpiresDays());
        chartToForm.setExpire(form.isExpires());
        chartToForm.setOnlyOne(form.isOnlyOnePerpatient());
        chartToForm.setTypePatientProcessId(form.getTypePatientProcess().getId());
        chartToForm.setContentHtml(form.getContentHtml());
        chartToForm.setLoadAutomatic(form.isLoadAutomatic());

        return chartToForm;
    }


    @Override
    public Picture updatePicture(PictureVO pictureVO) {

        log.debug("Update picture of Chart");

        if (pictureVO.getId() == null) {
            log.debug("***** Create New Picture *****");
            Picture saved = new Picture();

            String converted = ImageUtil.convertPicture(pictureVO.getPicture(), pictureVO.getPictureContentType().replace("image/", ""), Constants.PICTURE_WITH);
            saved.setPicture(converted);

            saved.setPictureContentType(pictureVO.getPictureContentType());

            saved = pictureRepository.save(saved);
            pictureSearchRepository.save(saved);

            if (pictureVO.getOwnerId() != null) {
                Chart chart = chartRepository.findOne(pictureVO.getOwnerId());
                chart.setPictureRef(saved);
                save(chart);
            }

            return saved;

        } else {
            log.debug("***** Update New Picture *****");
            Picture updated = pictureRepository.findOne(pictureVO.getId());
            updated.setPicture(ImageUtil.convertPicture(pictureVO.getPicture(), pictureVO.getPictureContentType().replace("image/", ""), Constants.PICTURE_WITH));
            updated.setPictureContentType(pictureVO.getPictureContentType());
            updated = pictureRepository.save(updated);
            pictureSearchRepository.save(updated);

            if (pictureVO.getOwnerId() != null) {
                Chart chart = chartRepository.findOne(pictureVO.getOwnerId());
                chart.setPictureRef(updated);
                save(chart);

            }

            return updated;
        }

    }

    public Patient findPatientByChart(Long id) {
        Patient patient = chartRepository.findPatientByChart(id);
        return patient;
    }


//    private List<PictureVO> getAllPictureVO(Long fId) {
//
//        List<Chart> chartList = chartRepository.findAllAllChartByFacilityId(fId);
//        log.debug("Updating picture for " + chartList.size() + " Charts");
//        List<PictureVO> pictures = new ArrayList<>();
//
//        chartList.stream().filter(x -> (x.getPictureRef() == null && x.getPicture() != null)).forEach(y -> {
//
//            PictureVO p = new PictureVO();
//            p.setOwnerId(y.getId());
//            p.setPicture(Base64.getEncoder().encodeToString(y.getPicture()));
//            p.setPictureContentType(y.getPictureContentType());
//
//            pictures.add(p);
//        });
//
//        return pictures;
//    }


//    @Transactional
//    public boolean updateAllPicture(Long fId) {
//
//        List<PictureVO> pictures = getAllPictureVO(fId);
//
//        try {
//
//            for (PictureVO p : pictures) {
//                updatePicture(p);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }


    public Chart findByChartToForm(Long id) {
        Chart chart = chartRepository.findByChartToForm(id);
        chart.getPatient();

        return chart;
    }


}




