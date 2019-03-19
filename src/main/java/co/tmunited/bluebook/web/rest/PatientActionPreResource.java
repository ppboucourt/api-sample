package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientActionPre;
import co.tmunited.bluebook.service.PatientActionPreService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientActionPre.
 */
@RestController
@RequestMapping("/api")
public class PatientActionPreResource {

    private final Logger log = LoggerFactory.getLogger(PatientActionPreResource.class);

    @Inject
    private PatientActionPreService patientActionPreService;

    /**
     * POST  /patient-action-pres : Create a new patientActionPre.
     *
     * @param patientActionPre the patientActionPre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientActionPre, or with status 400 (Bad Request) if the patientActionPre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-action-pres")
    @Timed
    public ResponseEntity<PatientActionPre> createPatientActionPre(@Valid @RequestBody PatientActionPre patientActionPre) throws URISyntaxException {
        log.debug("REST request to save PatientActionPre : {}", patientActionPre);
        if (patientActionPre.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientActionPre", "idexists", "A new patientActionPre cannot already have an ID")).body(null);
        }
        PatientActionPre result = patientActionPreService.save(patientActionPre);
        return ResponseEntity.created(new URI("/api/patient-action-pres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientActionPre", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-action-pres : Updates an existing patientActionPre.
     *
     * @param patientActionPre the patientActionPre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientActionPre,
     * or with status 400 (Bad Request) if the patientActionPre is not valid,
     * or with status 500 (Internal Server Error) if the patientActionPre couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-action-pres")
    @Timed
    public ResponseEntity<PatientActionPre> updatePatientActionPre(@Valid @RequestBody PatientActionPre patientActionPre) throws URISyntaxException {
        log.debug("REST request to update PatientActionPre : {}", patientActionPre);
        if (patientActionPre.getId() == null) {
            return createPatientActionPre(patientActionPre);
        }
        PatientActionPre result = patientActionPreService.save(patientActionPre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientActionPre", patientActionPre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-action-pres : get all the patientActionPres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientActionPres in body
     */
    @GetMapping("/patient-action-pres/action/{id}")
    @Timed
    public List<PatientActionPre> getAllPatientActionPres(@PathVariable Long id) {
        log.debug("REST request to get all PatientActionPres");
        return patientActionPreService.findAll(id);
    }

    /**
     * GET  /patient-action-pres/:id : get the "id" patientActionPre.
     *
     * @param id the id of the patientActionPre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientActionPre, or with status 404 (Not Found)
     */
    @GetMapping("/patient-action-pres/{id}")
    @Timed
    public ResponseEntity<PatientActionPre> getPatientActionPre(@PathVariable Long id) {
        log.debug("REST request to get PatientActionPre : {}", id);
        PatientActionPre patientActionPre = patientActionPreService.findOne(id);
        return Optional.ofNullable(patientActionPre)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-action-pres/:id : delete the "id" patientActionPre.
     *
     * @param id the id of the patientActionPre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-action-pres/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientActionPre(@PathVariable Long id) {
        log.debug("REST request to delete PatientActionPre : {}", id);
        patientActionPreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientActionPre", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-action-pres?query=:query : search for the patientActionPre corresponding
     * to the query.
     *
     * @param query the query of the patientActionPre search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-action-pres")
    @Timed
    public List<PatientActionPre> searchPatientActionPres(@RequestParam String query) {
        log.debug("REST request to search PatientActionPres for query {}", query);
        return patientActionPreService.search(query);
    }
    /**
     * GET  /patient-medication-pres : get all the patientMedicationPres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientMedicationPres in body
     */
}
