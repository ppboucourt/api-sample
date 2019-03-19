package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.vo.PatientOrderItemVO;
import co.tmunited.bluebook.service.PatientOrderItemService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientOrderItem.
 */
@RestController
@RequestMapping("/api")
public class PatientOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(PatientOrderItemResource.class);

    @Inject
    private PatientOrderItemService patientOrderItemService;

    /**
     * POST  /patient-order-items : Create a new patientOrderItem.
     *
     * @param patientOrderItem the patientOrderItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientOrderItem, or with status 400 (Bad Request) if the patientOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-order-items")
    @Timed
    public ResponseEntity<PatientOrderItem> createPatientOrderItem(@RequestBody PatientOrderItem patientOrderItem) throws URISyntaxException {
        log.debug("REST request to save PatientOrderItem : {}", patientOrderItem);
        if (patientOrderItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientOrderItem", "idexists", "A new patientOrderItem cannot already have an ID")).body(null);
        }
        PatientOrderItem result = patientOrderItemService.save(patientOrderItem);
        return ResponseEntity.created(new URI("/api/patient-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientOrderItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-order-items : Updates an existing patientOrderItem.
     *
     * @param patientOrderItem the patientOrderItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientOrderItem,
     * or with status 400 (Bad Request) if the patientOrderItem is not valid,
     * or with status 500 (Internal Server Error) if the patientOrderItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-order-items")
    @Timed
    public ResponseEntity<PatientOrderItem> updatePatientOrderItem(@RequestBody PatientOrderItem patientOrderItem) throws URISyntaxException {
        log.debug("REST request to update PatientOrderItem : {}", patientOrderItem);
        if (patientOrderItem.getId() == null) {
            return createPatientOrderItem(patientOrderItem);
        }
        PatientOrderItem result = patientOrderItemService.save(patientOrderItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientOrderItem", patientOrderItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-order-items : get all the patientOrderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientOrderItems in body
     */
    @GetMapping("/patient-order-items")
    @Timed
    public List<PatientOrderItem> getAllPatientOrderItems() {
        log.debug("REST request to get all PatientOrderItems");
        return patientOrderItemService.findAll();
    }

    /**
     * GET  /patient-order-items/:id : get the "id" patientOrderItem.
     *
     * @param id the id of the patientOrderItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrderItem, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order-items/{id}")
    @Timed
    public ResponseEntity<PatientOrderItem> getPatientOrderItem(@PathVariable Long id) {
        log.debug("REST request to get PatientOrderItem : {}", id);
        PatientOrderItem patientOrderItem = patientOrderItemService.findOne(id);
        return Optional.ofNullable(patientOrderItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-order-items/:id : delete the "id" patientOrderItem.
     *
     * @param id the id of the patientOrderItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete PatientOrderItem : {}", id);
        patientOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientOrderItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-order-items?query=:query : search for the patientOrderItem corresponding
     * to the query.
     *
     * @param query the query of the patientOrderItem search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-order-items")
    @Timed
    public List<PatientOrderItem> searchPatientOrderItems(@RequestParam String query) {
        log.debug("REST request to search PatientOrderItems for query {}", query);
        return patientOrderItemService.search(query);
    }

    @GetMapping("/patient-order-items/cancel/{id}")
    @Timed
    public ResponseEntity<Void> cancelPatientOrderItem(@PathVariable Long id) {
        patientOrderItemService.save(patientOrderItemService.findOne(id).canceled(true));

        return ResponseEntity.ok().build();
    }

    /**
     * GET  /all-order-items : get all the patientOrderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientOrderItems in body
     */
    @GetMapping("/all-order-items")
    @Timed
    public List<PatientOrderItem> findAllOrderItems() {
        log.debug("REST request to get all PatientOrderItems");
        return patientOrderItemService.findAllOrderItems();
    }

    /**
     * GET  /all-order-items : get all the patientOrderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientOrderItems in body
     */
    @GetMapping("/order-items-collected/{id}")
    @Timed
    public List<PatientOrderItemVO> findAllOrderItemsCollectedByChart(@PathVariable Long id) {
        log.debug("REST request to get all PatientOrderItems");
        return patientOrderItemService.findAllOrderItemsCollectedByChart(id);
    }

    /**
     * GET  /patient-order-items/:id : get the "id" patientOrderItem.
     *
     * @param id the id of the patientOrderItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrderItem, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order-item-sent/{id}")
    @Timed
    public ResponseEntity getPatientOrderItemSent(@PathVariable Long id) {
        log.debug("REST request for sending PatientOrderItem : {}", id);

        return Optional.ofNullable(patientOrderItemService.markAsSent(id))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patient-orders-items-to-process")
    @Timed
    public List<PatientOrderItem> getPatientOrderItemsToProcess() {
        log.debug("REST request to get Order Items to process.");
        List<PatientOrderItem> patientOrderItems = new ArrayList<>();
        patientOrderItems = patientOrderItemService.getPatientOrderItemsToProcess();

        return patientOrderItems;
    }
}
