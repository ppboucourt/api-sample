package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientActionTake;
import co.tmunited.bluebook.service.PatientActionTakeService;
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
 * REST controller for managing PatientActionTake.
 */
@RestController
@RequestMapping("/api")
public class PatientActionTakeResource {

    private final Logger log = LoggerFactory.getLogger(PatientActionTakeResource.class);

    @Inject
    private PatientActionTakeService patientActionTakeService;

    /**
     * POST  /patient-action-takes : Create a new patientActionTake.
     *
     * @param patientActionTake the patientActionTake to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientActionTake, or with status 400 (Bad Request) if the patientActionTake has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-action-takes")
    @Timed
    public ResponseEntity<PatientActionTake> createPatientActionTake(@RequestBody PatientActionTake patientActionTake) throws URISyntaxException {
        log.debug("REST request to save PatientActionTake : {}", patientActionTake);
        if (patientActionTake.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientActionTake", "idexists", "A new patientActionTake cannot already have an ID")).body(null);
        }
        PatientActionTake result = patientActionTakeService.save(patientActionTake);
        return ResponseEntity.created(new URI("/api/patient-action-takes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientActionTake", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-action-takes : Updates an existing patientActionTake.
     *
     * @param patientActionTake the patientActionTake to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientActionTake,
     * or with status 400 (Bad Request) if the patientActionTake is not valid,
     * or with status 500 (Internal Server Error) if the patientActionTake couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-action-takes")
    @Timed
    public ResponseEntity<PatientActionTake> updatePatientActionTake(@RequestBody PatientActionTake patientActionTake) throws URISyntaxException {
        log.debug("REST request to update PatientActionTake : {}", patientActionTake);
        if (patientActionTake.getId() == null) {
            return createPatientActionTake(patientActionTake);
        }
        PatientActionTake result = patientActionTakeService.save(patientActionTake);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientActionTake", patientActionTake.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-action-takes : get all the patientActionTakes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientActionTakes in body
     */
    @GetMapping("/patient-action-takes")
    @Timed
    public List<PatientActionTake> getAllPatientActionTakes() {
        log.debug("REST request to get all PatientActionTakes");
        return patientActionTakeService.findAll();
    }

    /**
     * GET  /patient-action-takes/:id : get the "id" patientActionTake.
     *
     * @param id the id of the patientActionTake to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientActionTake, or with status 404 (Not Found)
     */
    @GetMapping("/patient-action-takes/{id}")
    @Timed
    public ResponseEntity<PatientActionTake> getPatientActionTake(@PathVariable Long id) {
        log.debug("REST request to get PatientActionTake : {}", id);
        PatientActionTake patientActionTake = patientActionTakeService.findOne(id);
        return Optional.ofNullable(patientActionTake)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-action-takes/:id : delete the "id" patientActionTake.
     *
     * @param id the id of the patientActionTake to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-action-takes/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientActionTake(@PathVariable Long id) {
        log.debug("REST request to delete PatientActionTake : {}", id);
        patientActionTakeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientActionTake", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-action-takes?query=:query : search for the patientActionTake corresponding
     * to the query.
     *
     * @param query the query of the patientActionTake search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-action-takes")
    @Timed
    public List<PatientActionTake> searchPatientActionTakes(@RequestParam String query) {
        log.debug("REST request to search PatientActionTakes for query {}", query);
        return patientActionTakeService.search(query);
    }

    @GetMapping("/patient-action-takes/cancel/{id}")
    @Timed
    public ResponseEntity<Void> cancelPatientMedicationTakes(@PathVariable Long id) {
        patientActionTakeService.save(patientActionTakeService.findOne(id).canceled(true));

        return ResponseEntity.ok().build();
    }
}
