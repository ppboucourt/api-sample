package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.PatientOrderTest;
import co.tmunited.bluebook.service.PatientOrderTestService;
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
import java.util.Set;

/**
 * REST controller for managing PatientOrderTest.
 */
@RestController
@RequestMapping("/api")
public class PatientOrderTestResource {

    private final Logger log = LoggerFactory.getLogger(PatientOrderTestResource.class);

    @Inject
    private PatientOrderTestService patientOrderTestService;

    /**
     * POST  /patient-order-tests : Create a new patientOrderTest.
     *
     * @param patientOrderTest the patientOrderTest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientOrderTest, or with status 400 (Bad Request) if the patientOrderTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-order-tests")
    @Timed
    public ResponseEntity<PatientOrderTest> createPatientOrderTest(@Valid @RequestBody PatientOrderTest patientOrderTest) throws URISyntaxException {
        log.debug("REST request to save PatientOrderTest : {}", patientOrderTest);
        if (patientOrderTest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientOrderTest", "idexists", "A new patientOrderTest cannot already have an ID")).body(null);
        }
        PatientOrderTest result = patientOrderTestService.save(patientOrderTest);
        return ResponseEntity.created(new URI("/api/patient-order-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientOrderTest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-order-tests : Updates an existing patientOrderTest.
     *
     * @param patientOrderTest the patientOrderTest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientOrderTest,
     * or with status 400 (Bad Request) if the patientOrderTest is not valid,
     * or with status 500 (Internal Server Error) if the patientOrderTest couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-order-tests")
    @Timed
    public ResponseEntity<PatientOrderTest> updatePatientOrderTest(@Valid @RequestBody PatientOrderTest patientOrderTest) throws URISyntaxException {
        log.debug("REST request to update PatientOrderTest : {}", patientOrderTest);
        if (patientOrderTest.getId() == null) {
            return createPatientOrderTest(patientOrderTest);
        }
        PatientOrderTest result = patientOrderTestService.save(patientOrderTest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientOrderTest", patientOrderTest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-order-tests : get all the patientOrderTests form a PatientOrder.
     */
    @GetMapping("/patient-order-tests-from-order/{id}")
    @Timed
    public List<PatientOrderTest> getAllPatientOrderTest(@PathVariable Long id) {
        log.debug("REST request to get all PatientOrderTests");
        return patientOrderTestService.findAll(id);
    }

    /**
     * GET  /patient-order-tests/:id : get the "id" patientOrderTest.
     *
     * @param id the id of the patientOrderTest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrderTest, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order-tests/{id}")
    @Timed
    public ResponseEntity<PatientOrderTest> getPatientOrderTest(@PathVariable Long id) {
        log.debug("REST request to get PatientOrderTest : {}", id);
        PatientOrderTest patientOrderTest = patientOrderTestService.findOne(id);
        return Optional.ofNullable(patientOrderTest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-order-tests/:id : delete the "id" patientOrderTest.
     *
     * @param id the id of the patientOrderTest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-order-tests/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientOrderTest(@PathVariable Long id) {
        log.debug("REST request to delete PatientOrderTest : {}", id);
        patientOrderTestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientOrderTest", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-order-tests?query=:query : search for the patientOrderTest corresponding
     * to the query.
     *
     * @param query the query of the patientOrderTest search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-order-tests")
    @Timed
    public List<PatientOrderTest> searchPatientOrderTests(@RequestParam String query) {
        log.debug("REST request to search PatientOrderTests for query {}", query);
        return patientOrderTestService.search(query);
    }

    /**
     * GET  /patient-order-test/:id : get the "id" patientOrderTest excluding all its items.
     *
     * @param id the id of the patientOrderTest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrderTest, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order-test/{id}")
    @Timed
    public ResponseEntity<PatientOrderTest> geTPatientOrderTestWithoutChild(@PathVariable Long id) {
        log.debug("REST request to get PatientOrderTest : {}", id);
        PatientOrderTest patientOrderTest = patientOrderTestService.getPatientOrderItemsForSchedules(id);
        return Optional.ofNullable(patientOrderTest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/patient-orders-test/get-patient-order-tests-items/{id}")
    @Timed
    public Set<PatientOrderItem> getPatientOrderTestItems(@PathVariable Long id) {
        log.info("REST request schedule getPatientOrderTestItems");
        return patientOrderTestService.getPatientOrderTestItems(id);
    }
}
