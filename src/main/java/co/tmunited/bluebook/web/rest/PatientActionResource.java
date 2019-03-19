package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientAction;
import co.tmunited.bluebook.domain.PatientActionPre;
import co.tmunited.bluebook.domain.PatientActionTake;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientActionTakeVO;
import co.tmunited.bluebook.domain.vo.PatientActionVO;
import co.tmunited.bluebook.service.PatientActionService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientAction.
 */
@RestController
@RequestMapping("/api")
public class PatientActionResource {

    private final Logger log = LoggerFactory.getLogger(PatientActionResource.class);

    @Inject
    private PatientActionService patientActionService;

    /**
     * POST  /patient-actions : Create a new patientAction.
     *
     * @param patientAction the patientAction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientAction, or with status 400 (Bad Request) if the patientAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-actions")
    @Timed
    public ResponseEntity<PatientAction> createPatientAction(@RequestBody PatientAction patientAction) throws URISyntaxException {
        log.debug("REST request to save PatientAction : {}", patientAction);
        if (patientAction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientAction", "idexists", "A new patientAction cannot already have an ID")).body(null);
        }
        PatientAction result = patientActionService.save(patientAction);
        return ResponseEntity.created(new URI("/api/patient-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientAction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-actions : Updates an existing patientAction.
     *
     * @param patientAction the patientAction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientAction,
     * or with status 400 (Bad Request) if the patientAction is not valid,
     * or with status 500 (Internal Server Error) if the patientAction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-actions")
    @Timed
    public ResponseEntity<PatientAction> updatePatientAction(@RequestBody PatientAction patientAction) throws URISyntaxException {
        log.debug("REST request to update PatientAction : {}", patientAction);
        if (patientAction.getId() == null) {
            return createPatientAction(patientAction);
        }
        PatientAction result = patientActionService.save(patientAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientAction", patientAction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-orders : get all the patientOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientOrders in body
     */
    @GetMapping("/patient-actions/by-chart/{id}")
    @Timed
    public List<PatientAction> findAllByPatient(@PathVariable Long id) {
        log.debug("REST request to get all PatientActions");

        return patientActionService.findAllByChart(id);
    }

//    /**
//     * GET  /patient-actions : get all the patientActions.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of patientActions in body
//     */
//    @GetMapping("/patient-actions")
//    @Timed
//    public List<PatientAction> getAllPatientActions() {
//        log.debug("REST request to get all PatientActions");
//        return patientActionService.findAll();
//    }

    /**
     * GET  /patient-actions/:id : get the "id" patientAction.
     *
     * @param id the id of the patientAction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientAction, or with status 404 (Not Found)
     */
    @GetMapping("/patient-actions/{id}")
    @Timed
    public ResponseEntity<PatientAction> getPatientAction(@PathVariable Long id) {
        log.debug("REST request to get PatientAction : {}", id);
        PatientAction patientAction = patientActionService.findOne(id);
        return Optional.ofNullable(patientAction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-actions/:id : delete the "id" patientAction.
     *
     * @param id the id of the patientAction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-actions/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientAction(@PathVariable Long id) {
        log.debug("REST request to delete PatientAction : {}", id);
        patientActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientAction", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-actions?query=:query : search for the patientAction corresponding
     * to the query.
     *
     * @param query the query of the patientAction search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-actions")
    @Timed
    public List<PatientAction> searchPatientActions(@RequestParam String query) {
        log.debug("REST request to search PatientActions for query {}", query);
        return patientActionService.search(query);
    }

    /**
     * Schedule Order Items
     */
    @PostMapping("/patient-actions/schedule")
    @Timed
    public ResponseEntity<PatientAction> schedulePatientAction(@RequestBody PatientAction patientAction) throws URISyntaxException {
        log.debug("Schedule PatientAction : {}");

        return Optional.ofNullable(patientActionService.schedulePatientAction(patientAction))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/patient-actions/today/{id}/{date}")
    @Timed
    public List<PatientActionTake> findAllActionsByDate(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request actions");

        return patientActionService.findAllActionsByDate(id, date);
    }

    @GetMapping("/patient-actions-vo/today/{id}/{date}")
    @Timed
    public List<PatientActionTakeVO> findAllActionsByDateVO(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request actions");

        return patientActionService.findAllActionsByDateVO(id, date);
    }

    @GetMapping("/patient-actions/today/as-needed/{id}")
    @Timed
    public List<PatientActionPre> findAllActionsAsNeededByDate(@PathVariable Long id) {
        log.debug("REST request actions");

        return patientActionService.findAllActionsAsNeededByDate(id);
    }

    @PostMapping("/patient-actions/collect")
    @Timed
    public ResponseEntity<Void> takeActions(@RequestBody CollectedBody collectedBody) {
        patientActionService.take(collectedBody);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/patient-actions/add/as-needed")
    @Timed
    public ResponseEntity<Void> addPatientActionTakeAsNeeded(@RequestBody CollectedBody collectedBody){
        patientActionService.addPatientActionTakeAsNeeded(collectedBody.getPatientId(), collectedBody.getZonedDateTime());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-actions/unsigned/{id}")
    @Timed
    public List<PatientAction> getUnsignedActions(@PathVariable Long id) {
        List<PatientAction> result = patientActionService.getUnsignedActions(id);
        return result;
    }

    @GetMapping("/patient-actions/unsigned/all/{id}")
    @Timed
    public List<PatientAction> getAllUnsignedActions(@PathVariable Long id) {
        return patientActionService.getAllUnsignedActions(id);
    }

    @PostMapping("/patient-actions/sign-orders")
    @Timed
    public ResponseEntity<Void> signActions(@RequestBody CollectedBody collectedBody) {
        patientActionService.signActions(collectedBody);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-actions/get-all-by-chart-vo/{id}")
    @Timed
    List<PatientActionVO> getAllPatientActionByChartVO(@PathVariable Long id) {
        log.debug("REST request getAllPatientActionByChartVO");
        return patientActionService.getAllPatientActionByChartVO(id);
    }

    @GetMapping("/patient-actions/patient-medication-cancel/{id}")
    @Timed
    PatientAction cancelPatientAction(@PathVariable Long id) {
        log.debug("REST request cancelPatientAction");
        return patientActionService.cancelPatientAction(id);
    }
}
