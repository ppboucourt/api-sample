package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.OrderComponent;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.AllOrders;
import co.tmunited.bluebook.service.AllOrdersService;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AllOrders.
 */
@RestController
@RequestMapping("/api")
public class AllOrdersResource {

    private final Logger log = LoggerFactory.getLogger(AllOrdersResource.class);

    @Inject
    private AllOrdersService allOrdersService;

    /**
     * POST  /all-orders : Create a new allOrders.
     *
     * @param allOrders the allOrders to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allOrders, or with status 400 (Bad Request) if the allOrders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/all-orders")
    @Timed
    public ResponseEntity<AllOrders> createAllOrders(@Valid @RequestBody AllOrders allOrders) throws URISyntaxException {
        log.debug("REST request to save AllOrders : {}", allOrders);
        if (allOrders.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("allOrders", "idexists", "A new allOrders cannot already have an ID")).body(null);
        }
        AllOrders result = allOrdersService.save(allOrders);
        return ResponseEntity.created(new URI("/api/all-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("allOrders", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /all-orders : Updates an existing allOrders.
     *
     * @param allOrders the allOrders to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allOrders,
     * or with status 400 (Bad Request) if the allOrders is not valid,
     * or with status 500 (Internal Server Error) if the allOrders couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/all-orders")
    @Timed
    public ResponseEntity<AllOrders> updateAllOrders(@Valid @RequestBody AllOrders allOrders) throws URISyntaxException {
        log.debug("REST request to update AllOrders : {}", allOrders);
        if (allOrders.getId() == null) {
            return createAllOrders(allOrders);
        }
        AllOrders result = allOrdersService.save(allOrders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("allOrders", allOrders.getId().toString()))
            .body(result);
    }

    /**
     * GET  /all-orders : get all the allOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of allOrders in body
     */
    @GetMapping("/all-orders")
    @Timed
    public List<AllOrders> getAllAllOrders() {
        log.debug("REST request to get all AllOrders");
        return allOrdersService.findAll();
    }

    /**
     * GET  /all-orders/:id : get the "id" allOrders.
     *
     * @param id the id of the allOrders to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allOrders, or with status 404 (Not Found)
     */
    @GetMapping("/all-orders/{id}")
    @Timed
    public ResponseEntity<AllOrders> getAllOrders(@PathVariable Long id) {
        log.debug("REST request to get AllOrders : {}", id);
        AllOrders allOrders = allOrdersService.findOne(id);
        for (Iterator<OrderComponent> iterator = allOrders.getOrderComponents().iterator(); iterator.hasNext();) {
            OrderComponent s =  iterator.next();
            if (s.getDelStatus()) {
                iterator.remove();
            }
        }
        return Optional.ofNullable(allOrders)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /all-orders/:id : delete the "id" allOrders.
     *
     * @param id the id of the allOrders to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/all-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllOrders(@PathVariable Long id) {
        log.debug("REST request to delete AllOrders : {}", id);
        allOrdersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("allOrders", id.toString())).build();
    }

    /**
     * SEARCH  /_search/all-orders?query=:query : search for the allOrders corresponding
     * to the query.
     *
     * @param query the query of the allOrders search
     * @return the result of the search
     */
    @GetMapping("/_search/all-orders")
    @Timed
    public List<AllOrders> searchAllOrders(@RequestParam String query) {
        log.debug("REST request to search AllOrders for query {}", query);
        return allOrdersService.search(query);
    }


}
