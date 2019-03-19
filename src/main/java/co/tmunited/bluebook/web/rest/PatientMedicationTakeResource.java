package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientMedicationTake;
import co.tmunited.bluebook.service.PatientMedicationTakeService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientMedicationTake.
 */
@RestController
@RequestMapping("/api")
public class PatientMedicationTakeResource {

    private final Logger log = LoggerFactory.getLogger(PatientMedicationTakeResource.class);

    @Inject
    private PatientMedicationTakeService patientMedicationTakeService;

    /**
     * POST  /patient-medication-takes : Create a new patientMedicationTake.
     *
     * @param patientMedicationTake the patientMedicationTake to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientMedicationTake, or with status 400 (Bad Request) if the patientMedicationTake has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-medication-takes")
    @Timed
    public ResponseEntity<PatientMedicationTake> createPatientMedicationTake(@RequestBody PatientMedicationTake patientMedicationTake) throws URISyntaxException {
        log.debug("REST request to save PatientMedicationTake : {}", patientMedicationTake);
        if (patientMedicationTake.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientMedicationTake", "idexists", "A new patientMedicationTake cannot already have an ID")).body(null);
        }
        PatientMedicationTake result = patientMedicationTakeService.save(patientMedicationTake);
        return ResponseEntity.created(new URI("/api/patient-medication-takes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientMedicationTake", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-medication-takes : Updates an existing patientMedicationTake.
     *
     * @param patientMedicationTake the patientMedicationTake to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientMedicationTake,
     * or with status 400 (Bad Request) if the patientMedicationTake is not valid,
     * or with status 500 (Internal Server Error) if the patientMedicationTake couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-medication-takes")
    @Timed
    public ResponseEntity<PatientMedicationTake> updatePatientMedicationTake(@RequestBody PatientMedicationTake patientMedicationTake) throws URISyntaxException {
        log.debug("REST request to update PatientMedicationTake : {}", patientMedicationTake);
        if (patientMedicationTake.getId() == null) {
            return createPatientMedicationTake(patientMedicationTake);
        }
        PatientMedicationTake result = patientMedicationTakeService.save(patientMedicationTake);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientMedicationTake", patientMedicationTake.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-medication-takes : get all the patientMedicationTakes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientMedicationTakes in body
     */
    @GetMapping("/patient-medication-takes")
    @Timed
    public List<PatientMedicationTake> getAllPatientMedicationTakes() {
        log.debug("REST request to get all PatientMedicationTakes");
        return patientMedicationTakeService.findAll();
    }

    /**
     * GET  /patient-medication-takes/:id : get the "id" patientMedicationTake.
     *
     * @param id the id of the patientMedicationTake to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientMedicationTake, or with status 404 (Not Found)
     */
    @GetMapping("/patient-medication-takes/{id}")
    @Timed
    public ResponseEntity<PatientMedicationTake> getPatientMedicationTake(@PathVariable Long id) {
        log.debug("REST request to get PatientMedicationTake : {}", id);
        PatientMedicationTake patientMedicationTake = patientMedicationTakeService.findOne(id);
        return Optional.ofNullable(patientMedicationTake)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-medication-takes/:id : delete the "id" patientMedicationTake.
     *
     * @param id the id of the patientMedicationTake to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-medication-takes/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientMedicationTake(@PathVariable Long id) {
        log.debug("REST request to delete PatientMedicationTake : {}", id);
        patientMedicationTakeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientMedicationTake", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-medication-takes?query=:query : search for the patientMedicationTake corresponding
     * to the query.
     *
     * @param query the query of the patientMedicationTake search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-medication-takes")
    @Timed
    public List<PatientMedicationTake> searchPatientMedicationTakes(@RequestParam String query) {
        log.debug("REST request to search PatientMedicationTakes for query {}", query);
        return patientMedicationTakeService.search(query);
    }

    @GetMapping("/patient-medication-takes/cancel/{id}")
    @Timed
    public ResponseEntity<Void> cancelPatientMedicationTakes(@PathVariable Long id) {
        patientMedicationTakeService.save(patientMedicationTakeService.findOne(id).canceled(true));

        return ResponseEntity.ok().build();
    }
}
