package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.PatientToShift;
import co.tmunited.bluebook.service.PatientToShiftService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PatientToShift.
 */
@RestController
@RequestMapping("/api")
public class PatientToShiftResource {

    private final Logger log = LoggerFactory.getLogger(PatientToShiftResource.class);
        
    @Inject
    private PatientToShiftService patientToShiftService;

    /**
     * POST  /patient-to-shifts : Create a new patientToShift.
     *
     * @param patientToShift the patientToShift to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientToShift, or with status 400 (Bad Request) if the patientToShift has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-to-shifts")
    @Timed
    public ResponseEntity<PatientToShift> createPatientToShift(@Valid @RequestBody PatientToShift patientToShift) throws URISyntaxException {
        log.debug("REST request to save PatientToShift : {}", patientToShift);
        if (patientToShift.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientToShift", "idexists", "A new patientToShift cannot already have an ID")).body(null);
        }
        PatientToShift result = patientToShiftService.save(patientToShift);
        return ResponseEntity.created(new URI("/api/patient-to-shifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientToShift", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-to-shifts : Updates an existing patientToShift.
     *
     * @param patientToShift the patientToShift to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientToShift,
     * or with status 400 (Bad Request) if the patientToShift is not valid,
     * or with status 500 (Internal Server Error) if the patientToShift couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-to-shifts")
    @Timed
    public ResponseEntity<PatientToShift> updatePatientToShift(@Valid @RequestBody PatientToShift patientToShift) throws URISyntaxException {
        log.debug("REST request to update PatientToShift : {}", patientToShift);
        if (patientToShift.getId() == null) {
            return createPatientToShift(patientToShift);
        }
        PatientToShift result = patientToShiftService.save(patientToShift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientToShift", patientToShift.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-to-shifts : get all the patientToShifts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientToShifts in body
     */
    @GetMapping("/patient-to-shifts")
    @Timed
    public List<PatientToShift> getAllPatientToShifts() {
        log.debug("REST request to get all PatientToShifts");
        return patientToShiftService.findAll();
    }

    /**
     * GET  /patient-to-shifts/:id : get the "id" patientToShift.
     *
     * @param id the id of the patientToShift to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientToShift, or with status 404 (Not Found)
     */
    @GetMapping("/patient-to-shifts/{id}")
    @Timed
    public ResponseEntity<PatientToShift> getPatientToShift(@PathVariable Long id) {
        log.debug("REST request to get PatientToShift : {}", id);
        PatientToShift patientToShift = patientToShiftService.findOne(id);
        return Optional.ofNullable(patientToShift)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-to-shifts/:id : delete the "id" patientToShift.
     *
     * @param id the id of the patientToShift to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-to-shifts/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientToShift(@PathVariable Long id) {
        log.debug("REST request to delete PatientToShift : {}", id);
        patientToShiftService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientToShift", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-to-shifts?query=:query : search for the patientToShift corresponding
     * to the query.
     *
     * @param query the query of the patientToShift search 
     * @return the result of the search
     */
    @GetMapping("/_search/patient-to-shifts")
    @Timed
    public List<PatientToShift> searchPatientToShifts(@RequestParam String query) {
        log.debug("REST request to search PatientToShifts for query {}", query);
        return patientToShiftService.search(query);
    }


}
