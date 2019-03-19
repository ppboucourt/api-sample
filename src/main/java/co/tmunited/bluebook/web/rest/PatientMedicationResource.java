package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientMedication;
import co.tmunited.bluebook.domain.PatientMedicationPres;
import co.tmunited.bluebook.domain.PatientMedicationTake;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientMedicationDetailsVO;
import co.tmunited.bluebook.domain.vo.PatientMedicationVO;
import co.tmunited.bluebook.service.PatientMedicationService;
import co.tmunited.bluebook.web.rest.util.BooleanWrapper;
import co.tmunited.bluebook.web.rest.util.FaxWrapper;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * REST controller for managing PatientMedication.
 */
@RestController
@RequestMapping("/api")
public class PatientMedicationResource {

    private final Logger log = LoggerFactory.getLogger(PatientMedicationResource.class);

    @Inject
    private PatientMedicationService patientMedicationService;

    /**
     * POST  /patient-medications : Create a new patientMedication.
     *
     * @param patientMedication the patientMedication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientMedication, or with status 400 (Bad Request) if the patientMedication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-medications")
    @Timed
    public ResponseEntity<PatientMedication> createPatientMedication(@RequestBody PatientMedication patientMedication) throws URISyntaxException {
        log.debug("REST request to save PatientMedication : {}", patientMedication);
        if (patientMedication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientMedication", "idexists", "A new patientMedication cannot already have an ID")).body(null);
        }
        PatientMedication result = patientMedicationService.save(patientMedication);
        return ResponseEntity.created(new URI("/api/patient-medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientMedication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-medications : Updates an existing patientMedication.
     *
     * @param patientMedication the patientMedication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientMedication,
     * or with status 400 (Bad Request) if the patientMedication is not valid,
     * or with status 500 (Internal Server Error) if the patientMedication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-medications")
    @Timed
    public ResponseEntity<PatientMedication> updatePatientMedication(@RequestBody PatientMedication patientMedication) throws URISyntaxException {
        log.debug("REST request to update PatientMedication : {}", patientMedication);
        if (patientMedication.getId() == null) {
            return createPatientMedication(patientMedication);
        }
        PatientMedication result = patientMedicationService.save(patientMedication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientMedication", patientMedication.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /patient-medications : get all the patientMedications.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of patientMedications in body
//     */
//    @GetMapping("/patient-medications")
//    @Timed
//    public List<PatientMedication> getAllPatientMedications() {
//        log.debug("REST request to get all PatientMedications");
//        return patientMedicationService.findAll();
//    }

    /**
     * GET  /patient-medications : get all the patientMedications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientMedications in body
     */
    @GetMapping("/patient-medications/by-chart/{id}")
    @Timed
    public List<PatientMedicationVO> findAllByPatient(@PathVariable Long id) {
        log.debug("REST request to get all PatientMedication");

       // return patientMedicationService.findAllByChart(id);
        return patientMedicationService.findAllByChartVO(id);
    }

    /**
     * GET  /patient-medications/:id : get the "id" patientMedication.
     *
     * @param id the id of the patientMedication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientMedication, or with status 404 (Not Found)
     */
    @GetMapping("/patient-medications/{id}")
    @Timed
    public ResponseEntity<PatientMedication> getPatientMedication(@PathVariable Long id) {
        log.debug("REST request to get PatientMedication : {}", id);
        PatientMedication patientMedication = patientMedicationService.findOne(id);
        return Optional.ofNullable(patientMedication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-medications/:id : delete the "id" patientMedication.
     *
     * @param id the id of the patientMedication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-medications/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientMedication(@PathVariable Long id) {
        log.debug("REST request to delete PatientMedication : {}", id);
        patientMedicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientMedication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-medications?query=:query : search for the patientMedication corresponding
     * to the query.
     *
     * @param query the query of the patientMedication search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-medications")
    @Timed
    public List<PatientMedication> searchPatientMedications(@RequestParam String query) {
        log.debug("REST request to search PatientMedications for query {}", query);
        return patientMedicationService.search(query);
    }

    /**
     * Schedule Medication Items
     */
    @PostMapping("/patient-medications/schedule")
    @Timed
    public ResponseEntity<PatientMedication> schedulePatientMedication(@RequestBody PatientMedication patientMedication) throws URISyntaxException {
        log.debug("Schedule PatientMedication : {}");

        patientMedication.getPatientMedicationPress().stream().map(press -> {
            press.setStaringDate(press.getStaringDate().withZoneSameInstant(ZoneOffset.UTC));
//            System.out.println(press);
            return press;
        }).collect(Collectors.toList());

        return Optional.ofNullable(patientMedicationService.schedulePatientMedication(patientMedication))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/patient-medications/today/{id}/{date}")
    @Timed
    public List<PatientMedicationTake> findAllMedicationsByDate(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request medications");

        return patientMedicationService.findAllMedicationsByDate(id, date);
    }

    @GetMapping("/patient-medications/today/as-needed/{id}")
    @Timed
    public List<PatientMedicationPres> findAllMedicationsAsNeededByDate(@PathVariable Long id) {
        log.debug("REST request medications");

        return patientMedicationService.findAllMedicationsAsNeededByDate(id);
    }

    @PostMapping("/patient-medications/collect")
    @Timed
    public ResponseEntity<Void> takeMedications(@RequestBody CollectedBody collectedBody) {
        patientMedicationService.take(collectedBody);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/patient-medications/add/as-needed")
    @Timed
    public ResponseEntity<Void> addPatientMedicationTakeAsNeeded(@RequestBody CollectedBody collectedBody){
        patientMedicationService.addPatientMedicationTakeAsNeeded(collectedBody.getPatientId(), collectedBody.getZonedDateTime());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-medications/unsigned/{id}")
    @Timed
    public List<PatientMedication> getUnsignedMedication(@PathVariable Long id) {
        return patientMedicationService.getUnsignedMedications(id);
    }

    @GetMapping("/patient-medications/unsigned/all/{id}")
    @Timed
    public List<PatientMedication> getAllUnsignedMedication(@PathVariable Long id) {
        return patientMedicationService.getAllUnsignedMedications(id);
    }

    @PostMapping("/patient-medications/sign-medications")
    @Timed
    public ResponseEntity<Void> signMedication(@RequestBody CollectedBody collectedBody) {
        patientMedicationService.signMedications(collectedBody);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-medication-by-take/{id}")
    @Timed
    public PatientMedication findPatientMedicationsByTake(@PathVariable Long id) {
        log.debug("REST request patient-medication-by-take");

        return patientMedicationService.findPatientMedicationByTake(id);
    }

    @GetMapping("/patient-medication-by-press/{id}")
    @Timed
    public PatientMedication findPatientMedicationsByPress(@PathVariable Long id) {
        log.debug("REST request patient-medication-by-press");

        return patientMedicationService.findPatientMedicationByPress(id);
    }

//    @GetMapping("/patient-medication/generate-pres-pdf/{id}")
//    @Timed
//    public String generatePrescriptionPDF(@PathVariable Long id) throws Exception {
//        log.debug("REST request /patient-medication/generate-pres-pdf/{id}");
//        return patientMedicationService.generatePrescriptionPDF(id);
//    }


    @GetMapping("/patient-medication-cancel/{id}")
    @Timed
    public PatientMedication findPatientMedicationsCancel(@PathVariable Long id) {
        log.debug("REST request patient-medication-cancel");

        return patientMedicationService.cancelPatientMedication(id);
    }


    @GetMapping("/patient-medication-details-by-press-id/{id}")
    @Timed
    public PatientMedicationDetailsVO findPatientMedicationsDetailsByPress(@PathVariable Long id) {
        log.debug("REST request patient-medication-details-by-press-id");

        return patientMedicationService.findPatientMedicationDetailsByPress(id);
    }

    @PostMapping("/patient-medication/send-efax")
    @Timed
    public ResponseEntity<BooleanWrapper> sendFax(@RequestBody FaxWrapper faxWrapper) {

        Boolean  send = patientMedicationService.sendFax(faxWrapper);

        return Optional.ofNullable(new BooleanWrapper(send))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
    }


}
