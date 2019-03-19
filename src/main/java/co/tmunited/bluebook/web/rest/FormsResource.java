package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.util.FormJson;
import co.tmunited.bluebook.domain.vo.ChartToFormVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.domain.vo.ResponseFormParsed;
import co.tmunited.bluebook.repository.ChartToFormRepository;
import co.tmunited.bluebook.repository.EvaluationTemplateRepository;
import co.tmunited.bluebook.repository.FormsRepository;
import co.tmunited.bluebook.repository.TypeLevelCareRepository;
import co.tmunited.bluebook.service.*;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Forms.
 */
@RestController
@RequestMapping("/api")
public class FormsResource {

    private final Logger log = LoggerFactory.getLogger(FormsResource.class);

    @Inject
    private FormsService formsService;

    @Inject
    private PatientService patientService;

    @Inject
    private InsuranceService insuranceService;

    @Inject
    private InsuranceCarrierService insuranceCarrierService;

    @Inject
    private ChartToFormService chartToFormService;

    @Inject
    private ContactsService contactsService;

    @Inject
    private ChartToFormRepository chartToFormRepository;

    @Inject
    private EvaluationService evaluationService;

    @Inject
    private TypeLevelCareRepository typeLevelCareRepository;

    @Inject
    private FormsRepository formsRepository;

    @Inject
    private EvaluationTemplateRepository evaluationTemplateRepository;

    @Inject
    private EvaluationTemplateService evaluationTemplateService;

    @Inject
    private ChartService chartService;

    /**
     * POST  /forms : Create a new forms.
     *
     * @param forms the forms to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forms, or with status 400 (Bad Request) if the forms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forms")
    @Timed
    public ResponseEntity<Forms> createForms(@RequestBody Forms forms) throws URISyntaxException {
        log.debug("REST request to save Forms : {}", forms);
        if (forms.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("forms", "idexists", "A new forms cannot already have an ID")).body(null);
        }
        Forms result = formsService.save(forms);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("forms", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms : Updates an existing forms.
     *
     * @param forms the forms to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forms,
     * or with status 400 (Bad Request) if the forms is not valid,
     * or with status 500 (Internal Server Error) if the forms couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forms")
    @Timed
    public ResponseEntity<Forms> updateForms(@RequestBody Forms forms) throws URISyntaxException {
        log.debug("REST request to update Forms : {}", forms);
        if (forms.getId() == null) {
            return createForms(forms);
        }
        Forms result = formsService.save(forms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("forms", forms.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms : get all the forms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forms in body
     */
    @GetMapping("/forms")
    @Timed
    public List<Forms> getAllForms() {
        log.debug("REST request to get all Forms");
        return formsService.findAll().stream().map(form -> {
            if(form.getContentHtml() == null) {
                form.setContentHtml(form.getContent());
                formsService.save(form);
            }
            return form;
        }).collect(Collectors.toList());
    }

    /**
     * GET  /forms/:id : get the "id" forms.
     *
     * @param id the id of the forms to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forms, or with status 404 (Not Found)
     */
    @GetMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Forms> getForms(@PathVariable Long id) {
        log.debug("REST request to get Forms : {}", id);
        Forms forms = formsService.findOne(id);
        return Optional.ofNullable(forms)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forms/:id : delete the "id" forms.
     *
     * @param id the id of the forms to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteForms(@PathVariable Long id) {
        log.debug("REST request to delete Forms : {}", id);
        formsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("forms", id.toString())).build();
    }

    /**
     * SEARCH  /_search/forms?query=:query : search for the forms corresponding
     * to the query.
     *
     * @param query the query of the forms search
     * @return the result of the search
     */
    @GetMapping("/_search/forms")
    @Timed
    public List<Forms> searchForms(@RequestParam String query) {
        log.debug("REST request to search Forms for query {}", query);
        return formsService.search(query);
    }

    /**
     * GET  /forms/{id} : get all the forms by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forms filtered by facility in body
     */
    @GetMapping("/forms-by-facility/{id}")
    @Timed
    public List<Forms> getAllFormsByFacility(@PathVariable Long id) {
        log.debug("REST request to get all Forms");
        return formsService.findAllByFacilityId(id).stream().map(form -> {
            if(form.getContentHtml() == null){
                form.setContentHtml(form.getContent());
                formsService.save(form);
            }
            return form;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param id of the chartToForm assigned to a patient
     * @return the parse data for the Consent Form
     * @throws URISyntaxException
     */
    @GetMapping("/form/parse/{id}")
    public ResponseEntity<ResponseFormParsed> parseFormatContract(@PathVariable Long id) throws URISyntaxException, IOException {


        ChartToFormVO parserData = chartToFormService.findOneVOChart(id);
        String newFormatText;
        Chart chart = new Chart();
        ResponseFormParsed formParsed = new ResponseFormParsed();
        try {
            if(parserData.getChart() != null)
                chart = chartService.findOne(parserData.getChart());
            Patient patient = patientService.findOne(chart.getPatient().getId());

            Insurance insurance = insuranceService.findByChartIdAndOrder(chart.getId(), Integer.valueOf(1));
            Facility facility = chart.getFacility();
            if(insurance != null){
                InsuranceCarrier insuranceCarrier = insuranceCarrierService.findOne(insurance.getInsuranceCarrier().getId());
            }else{
                insurance = new Insurance();
                insurance.setInsuranceCarrier(new InsuranceCarrier());
                insurance.setInsuranceLevel(new InsuranceLevel());
            }

            LocalDate birthdate = patient.getDateBirth().toLocalDate();
            Long year = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);

            String fullName = patient.getFirstName() + " " + patient.getLastName();
            String patientAddress = chart.getAddress() + " " + chart.getAddressTwo() + " " + chart.getCity() + " " + chart.getZip();
            String facilityAddress = "";
            facilityAddress = facility.getStreet() + " " + facility.getStreet_two() + " " + facility.getCity() + " " +
                facility.getState() + " " + facility.getZip();

            String checkBox = "<input type=\"checkbox\" name=\"checkConsent\" checked=\"checked\"></input>";
            String textBox = "<input type=\"text\" name=\"textConsent\" style=\"width: 25%\"></input>";
            String strongText = "<p><strong>  </strong></p>";

            String referDischarge = referDischarge(strongText, chart);

            String contacts = buildingContactsTable(contactsService.findByChart(chart.getId()));//Building the contacts table

            String carrierName = (insurance.getInsuranceCarrier() != null) ? insurance.getInsuranceCarrier().getName(): "";
            String policyNumber = (insurance.getPolicyNumber() != null) ? insurance.getPolicyNumber(): "";
            String levelName = (insurance.getInsuranceLevel() != null) ? insurance.getInsuranceLevel().getName(): "";
            String bedName = (chart.getBed() != null) ? chart.getBed().getName():"";
            String ssn = (patient.getSocial() != null) ? patient.getSocial(): "";
            String email = (chart.getEmail() != null) ? chart.getEmail(): "";
            String phone = (chart.getPhone() != null) ? chart.getPhone(): "";
            String program = (chart.getTypePatientPrograms() != null) ? chart.getTypePatientPrograms().getName(): "";
            String facilityCountry = (facility.getCounty() != null)? facility.getCounty(): "";
            String facilityPhone = (facility.getPhone() != null)? facility.getPhone(): "";
            String facilityFax = (facility.getFax() != null)? facility.getFax(): "";
            String formContent = (parserData.getContentHtml() != null)? parserData.getContentHtml() : "";
            String mrNo = (chart != null)?chart.getMrNo(): "";


            /**
             * MessageFormat. format funtion:
             * the first parameter(content) is the text it going to be parsed, the content have the numbers({1}, {2}, ...etc) into the text
             * for be replaced with the rest of the parameter in the order we put it next to the first parameter.
             * The first value next to the first parameter is the number 1(position 0) and so sucesive.
             *
             */
            newFormatText = MessageFormat.format(formContent,
                bedName, mrNo, fullName,
                patient.getDateBirth().toLocalDate(), patient.getSex(), Integer.valueOf(year.toString()),
                ssn, chart.getAdmissionDate().toLocalDate(), patientAddress,
                email, phone, program,
                carrierName, policyNumber, levelName,
                facility.getName(), facilityAddress, facilityCountry,
                facilityPhone, facilityFax, checkBox, textBox, contacts, referDischarge);

            String formatTextBase = Base64.getEncoder().encodeToString(newFormatText.getBytes());
            formParsed = new ResponseFormParsed();
            formParsed.setBody(formatTextBase);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(formParsed, HttpStatus.OK);
    }

    private String referDischarge(String text, Chart chart){
        String x = text;
        String discharge = (chart.getDischargeTo() != null)?chart.getDischargeTo():"N/A";
        String refer = (chart.getReferrer() != null)?chart.getReferrer():"N/A";
        x = x.substring(0, 11) + discharge + " , " + refer + x.substring(11, x.length());
        return x;
    }

    private String buildingContactsTable(List<Contacts> contacts) {
        if(contacts != null && contacts.size() > 0){
            String table = "<table border=\"0\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px\">" +
                "<tbody>" +
                "<tr>" +
                "<td><strong>Name</strong></td>" +
                "<td><strong>Phone</strong></td>" +
                "<td><strong>Address</strong></td>" +
                "</tr>" +
                "</tbody>" +
                "</table>";

            for (int i = 0; i < contacts.size(); i++) {
                String row = "<tr> </tr>";

                String nameVal = (contacts.get(i).getFullName() != null)?contacts.get(i).getFullName():"Empty";
                String phoneVal = (contacts.get(i).getPhone() != null)?contacts.get(i).getPhone():"Empty";
                String addressVal = (contacts.get(i).getAddress() != null)?contacts.get(i).getAddress():"Empty";

                String name = "<td id=\"name\"> " + nameVal + " </td>";
                String phone = "<td id=\"phone\"> " + phoneVal + " </td>";
                String address = "<td id=\"address\"> " + addressVal + " </td>";

                row = row.substring(0, 4) + name + " " + phone + " " + address + row.substring(4, row.length());

                int rowIndex = table.lastIndexOf("</tr>");//Find where place for another row
                table = table.substring(0, rowIndex) + row + table.substring(rowIndex, table.length());
            }
            return table;
        }else
            return "Empty Contacts";
    }

    @GetMapping("/forms/patient-process/{ppId}/{facId}")
    @Timed
    public List<FormVO> getFormsByPatientProcess(@PathVariable Long ppId, @PathVariable Long facId) {
        List<FormVO> result = formsService.getFormsByPatientProcessAndFacility(ppId, facId);
        return result;
    }

    static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit){
        return unit.between(d1, d2);
    }

    /**
     * Bring all forms(consents and evaluations) by patient and patientProcess
     * @param chId
     * @param ppId
     * @return list of FormVO
     */
    @GetMapping("/forms/all-forms-chart/{chId}/{ppId}")
    public List<FormVO> loadFormsByPatientProcessAndFacility(@PathVariable Long chId, @PathVariable Long ppId) {

        List<FormVO> result = new ArrayList<>();
        List<FormVO> forms = chartToFormService.findAllPatientProcessInJsonForm(chId, ppId);
        List<FormVO> evaluations = evaluationService.findAllByPatientProcessAndChart(chId, ppId);

        if (!forms.isEmpty())
            result.addAll(forms);
        if (!evaluations.isEmpty())
            result.addAll(evaluations);

        return result;
    }

    /**
     *
     *
     * @param ppId
     * @param facId
     * @param lcId
     * @return
     */
    @GetMapping("/forms/all-forms-level-care/{ppId}/{facId}/{lcId}")
    public List<FormVO> getAllFormsByPatientProcessFacilityLevelCare(@PathVariable Long ppId, @PathVariable Long facId, @PathVariable Long lcId) {
        log.debug("REST request to get all Forms filtered by patientProcess, facility and levelcare, {}, {}, {}");
        return formsService.findAllByPatientProcessFacilityAndLevelCare(ppId, facId, lcId);
    }

    /**
     * Insert all typeLevelcare into forms, based on the facility you want do this.
     * @param id FacilityId
     */
    @GetMapping("forms/assign-to-facility/{id}")
    public ResponseEntity<Void> insertLevelCareIntoForms(@PathVariable Long id) throws URISyntaxException, IOException {
        //Load and Update Forms
        List<Forms> formsByFacility = formsRepository.findAllByFacilityWithLevelCare(id);

        List<TypeLevelCare> levelCaresByFacility = typeLevelCareRepository.findAllByFacilityIdAndDelStatusIsFalse(id);
        formsByFacility.stream().map(form -> {
            if (form.getTypeLevelCares().size() == 0) {
                form.getTypeLevelCares().addAll(levelCaresByFacility);
                formsService.save(form);
            }
            return form;
        }).collect(Collectors.toList());

        //Load and Update Evaluations Template
        List<EvaluationTemplate> evaluationTemplateFacility = evaluationTemplateRepository.findAllByFacilityWithLevelCare(id);
        evaluationTemplateFacility.stream().map(evaluation -> {
            if (evaluation.getTypeLevelCares().size() == 0) {
                evaluation.getTypeLevelCares().addAll(levelCaresByFacility);
                evaluationTemplateService.save(evaluation);
            }
            return evaluation;
        }).collect(Collectors.toList());

        return ResponseEntity.ok().headers(HeaderUtil.createAlert("Forms Updated to Facility", id.toString())).build();
    }


    /**
     * Migrating data from chartToForm's jsonData to the new fields into the entity.
     */
    @GetMapping("forms/migrate-json")
    public void migrateJsonDataToEntityFields(){
        List<ChartToForm> list = chartToFormService.findAll();
        list.stream().map(form -> {

            ObjectMapper mapper = new ObjectMapper();
            FormJson formJson = new FormJson();

            try {
                formJson = mapper.readValue(form.getJsonData(), FormJson.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                form.setFormId(formJson.getId());
                form.setPatientSignatureRequired(formJson.getPatientSignatureRequired());
                form.setGuarantorSignatureRequired(formJson.getGuarantorSignatureRequired());
                form.setAllowAttachment(formJson.getAllowAttachment());
                form.setAllowRevocation(formJson.getAllowRevocation());
                form.setExpiresDays(formJson.getExpiresDays());
                form.setExpire(formJson.getExpires());
                form.setOnlyOne(formJson.getOnlyOnePerpatient());
                form.setTypePatientProcessId(formJson.getTypePatientProcessId());
                form.setLoadAutomatic(formJson.getLoadAutomatic());

//                chartToFormService.save(form);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return form;
        }).collect(Collectors.toList());
        Integer size = list.size();
    }

}
