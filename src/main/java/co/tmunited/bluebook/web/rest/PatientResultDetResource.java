package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientResultDet;
import co.tmunited.bluebook.service.PatientResultDetService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing PatientResultDet.
 */
@RestController
@RequestMapping("/api")
public class PatientResultDetResource {

    private final Logger log = LoggerFactory.getLogger(PatientResultDetResource.class);

    @Inject
    private PatientResultDetService patientResultDetService;

    /**
     * GET  /patient-result-dets : get all the patientResultDets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientResultDets in body
     */
    @GetMapping("/patient-result-dets/{id}")
    @Timed
    public List<PatientResultDet> getAllPatientResultDets(@PathVariable Long id) {
        log.debug("REST request to get all PatientResultDets");
        return patientResultDetService.findAll(id);
    }

    /**
     * SEARCH  /_search/patient-result-dets?query=:query : search for the patientResultDet corresponding
     * to the query.
     *
     * @param query the query of the patientResultDet search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-result-dets")
    @Timed
    public List<PatientResultDet> searchPatientResultDets(@RequestParam String query) {
        log.debug("REST request to search PatientResultDets for query {}", query);
        return patientResultDetService.search(query);
    }
}
