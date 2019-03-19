package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.PatientOrder;
import co.tmunited.bluebook.domain.PatientOrderTest;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientScheduleDataVO;
import co.tmunited.bluebook.domain.vo.PrintBarcode;
import co.tmunited.bluebook.service.PatientOrderService;
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
import java.util.Set;

/**
 * REST controller for managing PatientOrder.
 */
@RestController
@RequestMapping("/api")
public class PatientOrderResource {

    private final Logger log = LoggerFactory.getLogger(PatientOrderResource.class);

    @Inject
    private PatientOrderService patientOrderService;

    /**
     * POST  /patient-orders : Create a new patientOrder.
     *
     * @param patientOrder the patientOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientOrder, or with status 400 (Bad Request) if the patientOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-orders")
    @Timed
    public ResponseEntity<PatientOrder> createPatientOrder(@RequestBody PatientOrder patientOrder) throws URISyntaxException {
        log.debug("REST request to save PatientOrder : {}", patientOrder);
        if (patientOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientOrder", "idexists", "A new patientOrder cannot already have an ID")).body(null);
        }
        PatientOrder result = patientOrderService.save(patientOrder);
        return ResponseEntity.created(new URI("/api/patient-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-orders : Updates an existing patientOrder.
     *
     * @param patientOrder the patientOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientOrder,
     * or with status 400 (Bad Request) if the patientOrder is not valid,
     * or with status 500 (Internal Server Error) if the patientOrder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-orders")
    @Timed
    public ResponseEntity<PatientOrder> updatePatientOrder(@RequestBody PatientOrder patientOrder) throws URISyntaxException {
        log.debug("REST request to update PatientOrder : {}", patientOrder);
        if (patientOrder.getId() == null) {
            return createPatientOrder(patientOrder);
        }
        PatientOrder result = patientOrderService.save(patientOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientOrder", patientOrder.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /patient-orders : get all the patientOrders.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of patientOrders in body
//     */
//    @GetMapping("/patient-orders")
//    @Timed
//    public List<PatientOrder> getAllPatientOrders() {
//        log.debug("REST request to get all PatientOrders");
//        return patientOrderService.findAll();
//    }

    /**
     * GET  /patient-orders/:id : get the "id" patientOrder.
     *
     * @param id the id of the patientOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrder, or with status 404 (Not Found)
     */
    @GetMapping("/patient-orders/{id}")
    @Timed
    public ResponseEntity<PatientOrder> getPatientOrder(@PathVariable Long id) {
        log.debug("REST request to get PatientOrder : {}", id);
        PatientOrder patientOrder = patientOrderService.findOne(id);
        return Optional.ofNullable(patientOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-orders/:id : delete the "id" patientOrder.
     *
     * @param id the id of the patientOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientOrder(@PathVariable Long id) {
        log.debug("REST request to delete PatientOrder : {}", id);
        patientOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientOrder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-orders?query=:query : search for the patientOrder corresponding
     * to the query.
     *
     * @param query the query of the patientOrder search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-orders")
    @Timed
    public List<PatientOrder> searchPatientOrders(@RequestParam String query) {
        log.debug("REST request to search PatientOrders for query {}", query);

        return patientOrderService.search(query);
    }

    /**
     * GET  /patient-orders : get all the patientOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientOrders in body
     */
    @GetMapping("/orders-by-chart/{id}")
    @Timed
    public List<PatientOrder> findAllByPatient(@PathVariable Long id) {
        log.debug("REST request to get all PatientOrders");

        return patientOrderService.findAllByChart(id);
    }

    /**
     * Schedule Order Items
     */
    @PostMapping("/patient-orders-schedule")
    @Timed
    public ResponseEntity<PatientOrder> schedulePatientOrder(@RequestBody PatientOrder patientOrder) throws URISyntaxException {
        log.debug("Schedule PatientOrder : {}");

        return Optional.ofNullable(patientOrderService.schedulePatientOrder(patientOrder))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/cancel-patient-order/{id}")
    @Timed
    public ResponseEntity<Void> cancelPatientOrder(@PathVariable Long id) {
        log.info(" Request CancelPatientOrder : {}", id);
        patientOrderService.cancelPatientOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/finish-patient-order/{id}")
    @Timed
    public ResponseEntity<Void> finishPatientOrder(@PathVariable Long id) {
        patientOrderService.finishPatientOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-orders/today/{id}/{date}")
    @Timed
    public List<PatientOrder> findAllOrdersSchedulesByDate(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request schedule orders");

        return patientOrderService.findAllOrdersSchedulesByDate(id, date);
    }

//    @GetMapping("/patient-orders-today/{id}")
//    @Timed
//    public List<PatientOrder> findAllTodayOrders(@PathVariable Long id) {
//        log.debug("REST request today orders schedule");
//
//        return patientOrderService.findAllTodayOrders(id);
//    }

    @PostMapping("/patient-order-collect")
    @Timed
    public ResponseEntity<Void> collect(@RequestBody CollectedBody collectedBody) {
        patientOrderService.collect(collectedBody);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/count-orders-not-collected-by-date/{id}/{date}")
    @Timed
    public ResponseEntity<Long> countOrdersNotCollectedByDate(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request schedule and not collected orders");

        return new ResponseEntity<>(patientOrderService.countOrdersNotCollectedByDate(id, date), HttpStatus.OK);
    }

    /**
     * GET  /patient-order/:id : get the "id" patientOrder excluding.
     *
     * @param id the id of the patientOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrder, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order/{id}")
    @Timed
    public ResponseEntity<PatientOrder> getPatientOrderWithoutChild(@PathVariable Long id) {
        log.debug("REST request to get PatientOrder : {}", id);
        PatientOrder patientOrder = patientOrderService.findOne(id);
        patientOrder.getPatientOrderTests().clear();
        patientOrder.setChart(null);
        return Optional.ofNullable(patientOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patient-orders/unsigned/{id}")
    @Timed
    public List<PatientOrder> getUnsignedOrders(@PathVariable Long id) {
        return patientOrderService.getUnsignedOrders(id);
    }

    @GetMapping("/patient-orders/unsigned/all/{id}")
    @Timed
    public List<PatientOrder> getAllUnsignedOrders(@PathVariable Long id) {
        return patientOrderService.getAllUnsignedOrders(id);
    }

    @PostMapping("/patient-orders/sign-orders")
    @Timed
    public ResponseEntity<Void> signOrders(@RequestBody CollectedBody collectedBody) {
        patientOrderService.signOrders(collectedBody);

        return ResponseEntity.ok().build();
    }

    /**
     * GET  /patient-order-for-schedule/:id : get the "id" patientOrder excluding.
     *
     * @param id the id of the patientOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientOrder, or with status 404 (Not Found)
     */
    @GetMapping("/patient-order-for-schedule/{id}")
    @Timed
    public ResponseEntity<PatientOrder> getPatientOrderWithAll(@PathVariable Long id) {
        log.debug("REST request to get PatientOrder getPatientOrderWithAll : {}", id);
        PatientOrder patientOrder = patientOrderService.findOneWithAll(id);
        log.debug(" Patient : {}", patientOrder.getChart().getPatient().getId());

       // patientOrder.getPatientOrderTests().clear();
        return Optional.ofNullable(patientOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/patient-orders/schedule/{id}/{date}")
    @Timed
    public List<PatientScheduleDataVO> findAllPatientScheduleDataByFacility(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request schedule orders");

        return patientOrderService.findAllPatientScheduleDataByFacility(id, date);
    }

    @PostMapping("/patient-orders/change-draw-day")
    @Timed
    public ResponseEntity<Void> changeDrawDay(@RequestBody CollectedBody collectedBody) {
        patientOrderService.changeDrawDay(collectedBody);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-orders/get-signed-by/{id}")
    @Timed
    public Employee getSignedBy(@PathVariable Long id) {
        log.debug("REST request schedule getSignedBy");
        return patientOrderService.getSignedBy(id);
    }

    @PostMapping("/patient-orders/get-barcodes")
    @Timed
    public List<PrintBarcode> getBarcodes(@RequestBody CollectedBody collectedBody) {
        return patientOrderService.getBarcodes(collectedBody);
    }

    @GetMapping("/patient-orders/get-barcode/{id}/{date}")
    @Timed
    public CollectedBody getBarcode(@PathVariable Long id, @PathVariable LocalDate date) {
        return patientOrderService.getBarcode(id, date);
    }

}
