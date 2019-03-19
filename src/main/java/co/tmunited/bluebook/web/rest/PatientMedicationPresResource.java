package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientMedicationPres;
import co.tmunited.bluebook.service.PatientMedicationPresService;
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
 * REST controller for managing PatientMedicationPres.
 */
@RestController
@RequestMapping("/api")
public class PatientMedicationPresResource {

    private final Logger log = LoggerFactory.getLogger(PatientMedicationPresResource.class);

    @Inject
    private PatientMedicationPresService patientMedicationPresService;

    /**
     * POST  /patient-medication-pres : Create a new patientMedicationPres.
     *
     * @param patientMedicationPres the patientMedicationPres to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientMedicationPres, or with status 400 (Bad Request) if the patientMedicationPres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-medication-pres")
    @Timed
    public ResponseEntity<PatientMedicationPres> createPatientMedicationPres(@Valid @RequestBody PatientMedicationPres patientMedicationPres) throws URISyntaxException {
        log.debug("REST request to save PatientMedicationPres : {}", patientMedicationPres);
        if (patientMedicationPres.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientMedicationPres", "idexists", "A new patientMedicationPres cannot already have an ID")).body(null);
        }
        PatientMedicationPres result = patientMedicationPresService.save(patientMedicationPres);
        return ResponseEntity.created(new URI("/api/patient-medication-pres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientMedicationPres", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-medication-pres : Updates an existing patientMedicationPres.
     *
     * @param patientMedicationPres the patientMedicationPres to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientMedicationPres,
     * or with status 400 (Bad Request) if the patientMedicationPres is not valid,
     * or with status 500 (Internal Server Error) if the patientMedicationPres couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-medication-pres")
    @Timed
    public ResponseEntity<PatientMedicationPres> updatePatientMedicationPres(@Valid @RequestBody PatientMedicationPres patientMedicationPres) throws URISyntaxException {
        log.debug("REST request to update PatientMedicationPres : {}", patientMedicationPres);
        if (patientMedicationPres.getId() == null) {
            return createPatientMedicationPres(patientMedicationPres);
        }
        PatientMedicationPres result = patientMedicationPresService.save(patientMedicationPres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientMedicationPres", patientMedicationPres.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-medication-pres : get all the patientMedicationPres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientMedicationPres in body
     */
    @GetMapping("/patient-medication-pres/medication/{id}")
    @Timed
    public List<PatientMedicationPres> getAllPatientMedicationPres(@PathVariable Long id) {
        log.debug("REST request to get all PatientMedicationPres from " + id);

        return patientMedicationPresService.findAll(id);
    }

    /**
     * GET  /patient-medication-pres/:id : get the "id" patientMedicationPres.
     *
     * @param id the id of the patientMedicationPres to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientMedicationPres, or with status 404 (Not Found)
     */
    @GetMapping("/patient-medication-pres/{id}")
    @Timed
    public ResponseEntity<PatientMedicationPres> getPatientMedicationPres(@PathVariable Long id) {
        log.debug("REST request to get PatientMedicationPres : {}", id);
        PatientMedicationPres patientMedicationPres = patientMedicationPresService.findOne(id);
        return Optional.ofNullable(patientMedicationPres)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-medication-pres/:id : delete the "id" patientMedicationPres.
     *
     * @param id the id of the patientMedicationPres to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-medication-pres/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientMedicationPres(@PathVariable Long id) {
        log.debug("REST request to delete PatientMedicationPres : {}", id);
        patientMedicationPresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientMedicationPres", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-medication-pres?query=:query : search for the patientMedicationPres corresponding
     * to the query.
     *
     * @param query the query of the patientMedicationPres search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-medication-pres")
    @Timed
    public List<PatientMedicationPres> searchPatientMedicationPres(@RequestParam String query) {
        log.debug("REST request to search PatientMedicationPres for query {}", query);
        return patientMedicationPresService.search(query);
    }


}
