package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.Constants;
import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.FaxState;
import co.tmunited.bluebook.domain.enumeration.PatientMedicationStatus;
import co.tmunited.bluebook.domain.enumeration.PatientMedicationTakeStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientMedicationDetailsVO;
import co.tmunited.bluebook.domain.vo.PatientMedicationVO;
import co.tmunited.bluebook.repository.EmployeeRepository;
import co.tmunited.bluebook.repository.PatientMedicationPresRepository;
import co.tmunited.bluebook.repository.PatientMedicationRepository;
import co.tmunited.bluebook.repository.PatientMedicationTakeRepository;
import co.tmunited.bluebook.repository.search.PatientMedicationSearchRepository;
import co.tmunited.bluebook.repository.search.PatientMedicationTakeSearchRepository;
import co.tmunited.bluebook.service.*;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.util.FaxWrapper;
import com.titicservices.dialer.core.MessageSender;
import com.titicservices.dialer.core.domain.FaxMessage;
import com.titicservices.dialer.core.domain.FaxResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientMedication.
 */
@Service
@Transactional
public class PatientMedicationServiceImpl implements PatientMedicationService {

    private final Logger log = LoggerFactory.getLogger(PatientMedicationServiceImpl.class);

    @Inject
    private PatientMedicationRepository patientMedicationRepository;

    @Inject
    private PatientMedicationSearchRepository patientMedicationSearchRepository;

    @Inject
    private PatientMedicationPresRepository patientMedicationPresRepository;

    @Inject
    private PatientMedicationTakeService patientMedicationTakeService;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    private FileService fileService;

    @Inject
    AllergiesService allergiesService;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private FaxSendLogService faxSendLogService;


    /**
     * Save a patientMedication.
     *
     * @param patientMedication the entity to save
     * @return the persisted entity
     */
    public PatientMedication save(PatientMedication patientMedication) {
        log.debug("Request to save PatientMedication : {}", patientMedication);

        if (patientMedication.getId() == null) {

            if (!CollectionUtils.isEmpty(patientMedication.getPatientMedicationPress())) {
                patientMedication.getPatientMedicationPress().stream().map(prescription -> {
                    prescription.setPatientMedication(patientMedication);

                    if (!CollectionUtils.isEmpty(prescription.getPatientMedicationTakes())) {
                        prescription.getPatientMedicationTakes().stream().map(item -> {
                            item.setPatientMedicationPres(prescription);

                            return item;
                        }).collect(Collectors.toList());
                    }

                    return prescription;

                }).collect(Collectors.toList());
            }
        }

        //Save PressTake
        PatientMedication result = patientMedicationRepository.save(patientMedication);

        if (!CollectionUtils.isEmpty(result.getPatientMedicationPress())) {
            result.getPatientMedicationPress().stream().forEach(p -> {

                if (!CollectionUtils.isEmpty(p.getPatientMedicationTakes())) {
                    p.getPatientMedicationTakes().stream().forEach(pt -> {
                        pt.setPatientMedicationPresId(p.getId());
                        pt.setScheduleDate(pt.getScheduleDate().plusNanos(500));
                        pt.setCollectedDate(pt.getCollectedDate().plusNanos(500));
                        patientMedicationTakeService.save(pt);
                    });
                }

            });
        }


        patientMedicationSearchRepository.save(result);

        return result;
    }

    /**
     * Get all the patientMedications.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedication> findAll() {
        log.debug("Request to get all PatientMedications");
        List<PatientMedication> result = patientMedicationRepository.findAll();

        return result;
    }

    /**
     * Get one patientMedication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientMedication findOne(Long id) {
        log.debug("Request to get PatientMedication : {}", id);
        PatientMedication patientMedication = patientMedicationRepository.findOne(id);
        return patientMedication;
    }

    /**
     * Delete the  patientMedication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientMedication : {}", id);
        patientMedicationRepository.delete(id);
        patientMedicationSearchRepository.delete(id);
    }

    /**
     * Search for the patientMedication corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedication> search(String query) {
        log.debug("Request to search PatientMedications for query {}", query);
        return StreamSupport
            .stream(patientMedicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<PatientMedication> findAllByChart(Long id) {
        return patientMedicationRepository.findAllByChartId(id);
    }

    @Override
    public List<PatientMedicationVO> findAllByChartVO(Long id) {
        return patientMedicationRepository.findAllByChartIdVO(id);
    }

    @Override
    public PatientMedication schedulePatientMedication(PatientMedication patientMedication) {
        ZonedDateTime now = ZonedDateTime.now();

        for (PatientMedicationPres test : patientMedication.getPatientMedicationPress()) {
            test.getPatientMedicationTakes().clear();

            if (!test.getAsNeeded()) {
                if (test.getStaringDate().compareTo(now) >= 0) {
                    if (test.getEndDate().compareTo(test.getStaringDate()) >= 0) {
                        test.setPatientMedicationTakes(getScheduled(test).getPatientMedicationTakes());
                    }
                }
            }
        }

        return patientMedication;
    }

    @Inject
    PatientMedicationTakeRepository patientMedicationTakeRepository;

    @Override
    public List<PatientMedicationTake> findAllMedicationsByDate(Long id, LocalDate date) {

        ZonedDateTime initDate = date.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endDate = date.atTime(23, 59, 59).atZone(ZoneId.systemDefault());

        log.debug("findAllMedicationsByDate query {} {}", initDate, endDate);
        List<PatientMedicationTake> patientMedicationTakes = patientMedicationTakeRepository.findAllMedicationsByDate(id, initDate, endDate);

        log.debug("findAllMedicationsByDate query end {} {}", initDate, endDate);
        return patientMedicationTakes.stream().map(
            take -> {
                take.setMedication(take.getPatientMedicationPres().getMedications().getMedication());
                take.setDose(take.getPatientMedicationPres().getDose());

                Routes route = take.getPatientMedicationPres().getPatientMedication().getRoute();
                take.setRoute(route != null ? route.getName() : "");
                return take;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public void take(CollectedBody collectedBody) {
        ZonedDateTime collected = ZonedDateTime.now();

        collectedBody.getIds().stream().forEach(
            order -> {
                PatientMedicationTake action = patientMedicationTakeRepository.findOne(Long.valueOf(order));

                if (action.getPatientMedicationPres().getPatientMedication().getMedicationStatus() == PatientMedicationStatus.SCHEDULED) {
                    action.setCollected(true);
                    action.setCollectedDate(collected);

                    patientMedicationTakeRepository.save(action);
                }
            }
        );
    }

    @Override
    public List<PatientMedicationPres> findAllMedicationsAsNeededByDate(long id) {
        return patientMedicationPresRepository.findAllMedicationAsNeededByDate(id);
    }

    private PatientMedicationPres getScheduled(PatientMedicationPres test) {
        int i = 0;
        PatientMedicationTake item = null;

        ZonedDateTime plusDay = test.getStaringDate().plusHours(test.getHours() * i);

        while (plusDay.withZoneSameInstant(ZoneId.of("UTC")).compareTo(test.getEndDate()) <= 0) {
            item = new PatientMedicationTake();
            item.setPatientMedicationPres(test);
            item.setScheduleDate(plusDay);
            item.setCollectedDate(plusDay);
            item.setMedicationTakeStatus(PatientMedicationTakeStatus.SCHEDULED);
            test.addPatientMedicationTake(item);

            i++;
            plusDay = test.getStaringDate().plusHours(test.getHours() * i);
        }
//        test.setEndDate(item.getScheduleDate());

        test.setPatientMedicationTakes(test.getPatientMedicationTakes().stream().sorted(Comparator.comparing(PatientMedicationTake::getScheduleDate))
            .collect(Collectors.toCollection(TreeSet::new)));
        return test;
    }

    @Inject
    UserService userService;

    @Inject
    PatientMedicationTakeSearchRepository patientMedicationTakeSearchRepository;

    @Override
    public void addPatientMedicationTakeAsNeeded(long id, ZonedDateTime date) {
        Employee employee = userService.getEmployeeWithAuthorities();

        PatientMedicationPres presc = patientMedicationPresRepository.findOne(id);

        if (presc == null) {
            return;
        }

        PatientMedicationTake take = new PatientMedicationTake();
        take.setPatientMedicationPres(presc);
        take.setPatientMedicationPresId(presc.getId());
        take.setAdministerBy(employee);
        take.setCollected(true);
        take.setCanceled(false);
        take.setCollectedDate(date);
        take.setScheduleDate(date);
        take.setMedicationTakeStatus(PatientMedicationTakeStatus.ADMINISTERED);

        patientMedicationTakeRepository.save(take);
        patientMedicationTakeSearchRepository.save(take);
    }

    @Transactional(readOnly = true)
    public List<PatientMedication> getUnsignedMedications(Long id) {
        Employee employee = userService.getEmployeeWithAuthorities();

        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            return patientMedicationRepository.findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(id, employee.getId());
        }

        return new ArrayList<>();
    }

    @Override
    public List<PatientMedication> getAllUnsignedMedications(Long id) {
        List<PatientMedication> result = patientMedicationRepository.findAllBySignedIsFalseAndDelStatusIsFalse(id);
        return result;
    }

    @Override
    @Transactional
    public void signMedications(CollectedBody collectedBody) {
        Employee employee = userService.getEmployeeWithAuthorities();
        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            collectedBody.getIds().stream().forEach(
                medicationId -> {
                    PatientMedication medication = findOne(medicationId);
                    if (medication.getSignedBy().getId() == employee.getId()) {
                        medication.setSigned(true);
                        medication.setSignatureDate(LocalDate.now());

                        save(medication);
                    }
                }
            );
        }
    }


    @Override
    @Transactional
    public PatientMedication findPatientMedicationByTake(@Param("id") Long id) {
        return patientMedicationRepository.findPatientMedicationByTake(id);
    }

    @Override
    public String generatePrescriptionPDF(FaxWrapper faxWrapper) throws Exception {
//        Employee employee = userService.getEmployeeWithAuthorities();
        PatientMedicationVO patientMedicationVO = patientMedicationRepository.findPatientMedicationToGeneratePDF(faxWrapper.getPatientMedicationId());
        List<Allergies> allergiesList = allergiesService.findAllByChartId(patientMedicationVO.getChartId());
        patientMedicationVO.setAllergiesList(allergiesList);
        return manipulatePdf(patientMedicationVO, faxWrapper);
    }

    private String manipulatePdf(PatientMedicationVO patientMedicationVO, FaxWrapper faxWrapper) throws Exception {
        File tempFile = File.createTempFile("pdf-template", ".pdf");
        tempFile.deleteOnExit();

        String fileName = tempFile.getAbsolutePath();

        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream("report/pharmacy_order_fax.pdf");

        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        File file = new File(fileName);
        PDDocument pdfDocument = PDDocument.load(file);
        pdfDocument.getNumberOfPages();

        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        //Sent At
        acroForm.getField("sendAt").setValue(patientMedicationVO.getFacilityName());
        //Respond Fax
        acroForm.getField("respondFaxTo").setValue(Constants.FAX_FROM_NUMBER);

        //TO
        acroForm.getField("TO").setValue(faxWrapper.getServiceProviderName());
        //From
        acroForm.getField("FROM").setValue(patientMedicationVO.getFacilityName() + " \r" +
            patientMedicationVO.getFacilityAddrStreet() + " " + patientMedicationVO.getFacilityAddrCity() + " " +
            patientMedicationVO.getFacilityAddrState() + " " + patientMedicationVO.getFacilityAddrZip());
        //Patient Information
        acroForm.getField("name").setValue(patientMedicationVO.getPatientName());
        acroForm.getField("mrNumber").setValue(patientMedicationVO.getMrNumber());
        String dateOfBirth = (patientMedicationVO.getPatientBirthday() != null) ? patientMedicationVO.getPatientBirthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "";
        acroForm.getField("dateOfBirth").setValue(dateOfBirth);
        acroForm.getField("birthSex").setValue(patientMedicationVO.getPatientGender().toString());
        acroForm.getField("address").setValue(patientMedicationVO.getPatientAddress());
        //Set Allergies
        List<Allergies> allergiesList = patientMedicationVO.getAllergiesList();
        Collector<Allergies, StringJoiner, String> allergieNameCollector =
            Collector.of(
                () -> new StringJoiner(" | "),                          // supplier
                (acc, allergy) -> acc.add(allergy.getAllergen().toUpperCase()),  // accumulator
                (allergy1, allergy2) -> allergy1.merge(allergy2),                // combiner
                StringJoiner::toString);                                         // finisher
        String allergiesNames = allergiesList
            .stream()
            .collect(allergieNameCollector);
        acroForm.getField("allergies").setValue(allergiesNames);

        //Insurance
        acroForm.getField("policy").setValue(patientMedicationVO.getPolicyNumber());
        acroForm.getField("groupId").setValue(patientMedicationVO.getGroupNumber());
        acroForm.getField("phone").setValue(patientMedicationVO.getInsurancePhone());
        acroForm.getField("type").setValue(patientMedicationVO.getInsuranceType());
        acroForm.getField("planType").setValue(patientMedicationVO.getPlanNumber());
        acroForm.getField("relationship").setValue(patientMedicationVO.getInsuranceRelation());
        String insuranceDob = (patientMedicationVO.getInsuranceDob() != null) ? patientMedicationVO.getInsuranceDob().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "";
        acroForm.getField("dob").setValue(insuranceDob);
        acroForm.getField("subscriber").setValue(patientMedicationVO.getInsuranceSubscriber());

        //Medications orders
        String duration = "";
        if (patientMedicationVO.getMedStartingDate() != null && patientMedicationVO.getMedEndDate() != null) {
            Period diff = Period.between(patientMedicationVO.getMedStartingDate().toLocalDate(), patientMedicationVO.getMedEndDate().toLocalDate());
            duration = ((diff.getYears() > 0) ? diff.getYears() + " year(s) " : "") +
                ((diff.getMonths() > 0) ? diff.getMonths() + " month(s) " : "") +
                ((diff.getDays() > 0) ? diff.getDays() + " day(s) " : "");
        }
        acroForm.getField("medication").setValue(
            patientMedicationVO.getMedication() + " " + ((patientMedicationVO.getMedDose() != null) ? patientMedicationVO.getMedDose() : "") + ", " +
                ((patientMedicationVO.getMedRoute() != null) ? patientMedicationVO.getMedRoute() : "") +
                ", every " + patientMedicationVO.getMedHours() + " hours " + ((!duration.equals("")) ? "for " + duration : "")
        );
        String medJustification = patientMedicationVO.getMedJustification();
        acroForm.getField("justification").setValue((medJustification != null) ? medJustification : "");
        String startDate = (patientMedicationVO.getMedStartingDate() != null) ? patientMedicationVO.getMedStartingDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:hm a")) : "";
        acroForm.getField("startDate").setValue(startDate);
        String endDate = (patientMedicationVO.getMedEndDate() != null) ? patientMedicationVO.getMedEndDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) : "";
        acroForm.getField("endDate").setValue(endDate);
//        acroForm.getField("duration").setValue(String.valueOf(patientMedicationVO.getMedHours()));
        acroForm.getField("duration").setValue(duration);
        String amountDispense = (patientMedicationVO.getMedAmountToDispense() != null) ? patientMedicationVO.getMedAmountToDispense() : "Not given";
        acroForm.getField("dispenseAmount").setValue(amountDispense);
        String refills = (patientMedicationVO.getMedRefills() != null) ? patientMedicationVO.getMedRefills() : "0";
        acroForm.getField("numberOfRefills").setValue(refills);

        //Footer
        String enteredMedDate = (patientMedicationVO.getEnteredMedDate() != null) ? patientMedicationVO.getEnteredMedDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) : "";
        acroForm.getField("enteredBy").setValue(
            patientMedicationVO.getEnteredEmployeeName() + ", " + patientMedicationVO.getEnteredEmployeeTitle() + ", " +
                enteredMedDate + " via " + patientMedicationVO.getMedVia() + ", read back and verified "
        );
        acroForm.getField("orderedBy").setValue(
            patientMedicationVO.getPrescriberName() + ", " + patientMedicationVO.getPhysicianTitle() + ", NPI: " + patientMedicationVO.getNpi() +
                ", DEA: " + patientMedicationVO.getDeaNumber()
        );

//        dateField.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Instantiate the stream you want to use.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfDocument.save(baos);
        pdfDocument.close();

        byte[] bytes = baos.toByteArray();

        String encodedPdf = Base64.getEncoder().encodeToString(bytes);
        //Persist the PDF file to Mongo
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(patientMedicationVO.getId().toString());
        fileDTO.setName("medication_" + patientMedicationVO.getId());
        fileDTO.setSize(Integer.toString(baos.size()));
        fileDTO.setFileContentType("application/pdf");
        fileDTO.setFile(encodedPdf);
        co.tmunited.bluebook.domain.File filePersisted = fileService.attachFile(fileDTO);

//        Close the streams
        inputStream.close();
        baos.close();
        return filePersisted.getUuid();
    }

    @Override
    @Transactional
    public PatientMedication findPatientMedicationByPress(@Param("id") Long id) {
        return patientMedicationRepository.findPatientMedicationByPress(id);
    }


    @Transactional
    public PatientMedication cancelPatientMedication(@Param("id") Long id) {
        PatientMedication patientMedication = patientMedicationRepository.findOne(id);

        try {
            if (patientMedication != null) {

                if (!CollectionUtils.isEmpty(patientMedication.getPatientMedicationPress())) {
                    patientMedication.getPatientMedicationPress().stream().map(press -> {

                        if (CollectionUtils.isEmpty(press.getPatientMedicationTakes())) {
                            press.getPatientMedicationTakes().stream().map(take -> {
                                take.setCanceled(true);
                                return take;
                            }).collect(Collectors.toList());
                        }

                        return press;
                    }).collect(Collectors.toList());

                    patientMedication.setMedicationStatus(PatientMedicationStatus.CANCELED);
                    patientMedication.setEndDate(LocalDate.now());
                }

                return patientMedication;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new PatientMedication();
        }
        return new PatientMedication();
    }


    @Override
    public PatientMedicationDetailsVO findPatientMedicationDetailsByPress(@PathVariable Long id) {

        PatientMedicationPres medicationPres = patientMedicationPresRepository.findOne(id);

        medicationPres.getMedications().getId();

        PatientMedicationDetailsVO detailsVO = new PatientMedicationDetailsVO();
        PatientMedication pm = medicationPres.getPatientMedication();

        detailsVO.setPmId(pm.getId());
        detailsVO.setPmpId(id);
        detailsVO.setMedication(medicationPres.getMedications().getMedication());
        detailsVO.setAsNeeded(medicationPres.getAsNeeded());
        detailsVO.setCreateDate(medicationPres.getCreatedDate());
        detailsVO.setStartDate(medicationPres.getStaringDate());
        detailsVO.setEndDate(medicationPres.getEndDate());
        detailsVO.setPhysicianFirstName(pm.getSignedBy() != null ? pm.getSignedBy().getFirstName() : "");
        detailsVO.setPhysicianLastName(pm.getSignedBy() != null ? pm.getSignedBy().getLastName() : "");
        detailsVO.setStatus(pm.getMedicationStatus());
        detailsVO.setDispenseAmount(medicationPres.getAmountToDispense());
        detailsVO.setAdministerAmount(medicationPres.getAmountToDispense());
        detailsVO.setDea(pm.getSignedBy() != null ? pm.getSignedBy().getDeaNumber() : "");
        detailsVO.setDosageForm(medicationPres.getDosegeForm());
        detailsVO.setDose(medicationPres.getDose());
        detailsVO.setOrderedBy(pm.getSignedBy() != null ? (pm.getSignedBy().getFirstName() + " " + pm.getSignedBy().getLastName()) : "");

        Employee employee = employeeRepository.findOneByUserLogin(pm.getCreatedBy()).get();

        detailsVO.setEnteredBy(employee.getFirstName() + " " + employee.getLastName());
        detailsVO.setNpi(pm.getSignedBy() != null ? pm.getSignedBy().getNpiNumber() : "");
        detailsVO.setVia(pm.getVia() != null ? pm.getVia().getName() : "");
        detailsVO.setNumberRefills(pm.getRefills());
        detailsVO.setRoute(pm.getRoute() != null ? pm.getRoute().getName() : "");

        detailsVO.setFrecuency(medicationPres.getHours() != null ? medicationPres.getHours().toString() : "");

        return detailsVO;
    }


    public Boolean sendFax(FaxWrapper faxWrapper) {

        try {
            Employee employee = userService.getEmployeeWithAuthorities();
            String token = faxWrapper.getToken();
            PatientMedication patientMedication = findOne(faxWrapper.getPatientMedicationId());
            patientMedication.getChart().getId();

            //Generate pdf and uuid
            String uuid = generatePrescriptionPDF(faxWrapper);
            MessageSender sender = MessageSender.getInstance();

            String pdfUrl = faxWrapper.getDomain() + jHipsterProperties.getFileStorage().getRemote().getUrlGetFax() + "/";

            String plusOne = "+1";
            String faxNumber = faxWrapper.getFaxNumber().startsWith(plusOne) ? faxWrapper.getFaxNumber() : plusOne + faxWrapper.getFaxNumber();
            faxNumber = faxNumber.replaceAll("-", "");

            log.debug("Send Fax to: " + faxNumber);

            FaxSendLog faxSendLog = new FaxSendLog();
            faxSendLog.setDelStatus(false);
            faxSendLog.setFaxFromNumber(Constants.FAX_FROM_NUMBER);
            faxSendLog.setFaxToNumber(faxWrapper.getFaxNumber());
            faxSendLog.setSendDate(ZonedDateTime.now());
            faxSendLog.setSendBy(employee);
            faxSendLog.setToName(faxWrapper.getServiceProviderName());
            faxSendLog.setChart(patientMedication.getChart());

            faxSendLog.setPdfUuid(uuid);
            faxSendLog.setMediaUrl(pdfUrl); //Without id and token

            faxSendLog = faxSendLogService.save(faxSendLog);


            pdfUrl = pdfUrl + faxSendLog.getId() + "/" + token;
            log.info(pdfUrl);
            log.info(faxWrapper.getFaxNumber());

            FaxMessage faxMessage = new FaxMessage(Constants.FAX_FROM_NUMBER, faxNumber, pdfUrl);
            FaxResponse faxResponse = sender.sendFax(faxMessage);

            log.info(faxResponse.getSId());
            log.info(faxResponse.getStatus());

            //Here, is necessary save to database!!!
            FaxState state = find(faxResponse.getStatus());
            if (faxResponse.getSId() != null && state != null) {
                faxSendLog.setFaxState(state);
                faxSendLog.setSid(faxResponse.getSId());

                faxSendLogService.save(faxSendLog);

                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static FaxState find(String val) {
        return Arrays.stream(FaxState.values())
            .filter(e -> e.toString().replace("_", "").toLowerCase().equals(val))
            .findAny()
            .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", val)));
    }


//
//    @Override
//    public ResponseEntity<Resource> download(Long id) throws IOException {
//        PatientResultFile patientResultFile = patientResultFileRepository.findOne(id);
//
//        File tmp = null;
//        try {
//            tmp = createTmpFile(patientResultFile.getUuid());
//        } catch (Exception e) {
//            //Send to client a error pdf file.
//            tmp = createTmpErrorFile();
//            e.printStackTrace();
//        }
//
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tmp.toPath()));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "file_result.pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        headers.setContentLength(resource.getByteArray().length);
//
//        return ResponseEntity.ok().headers(headers).body(resource);
//
//    }


}
