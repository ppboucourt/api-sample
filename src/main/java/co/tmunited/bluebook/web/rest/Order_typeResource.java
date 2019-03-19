package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Order_type;
import co.tmunited.bluebook.service.Order_typeService;
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
 * REST controller for managing Order_type.
 */
@RestController
@RequestMapping("/api")
public class Order_typeResource {

    private final Logger log = LoggerFactory.getLogger(Order_typeResource.class);
        
    @Inject
    private Order_typeService order_typeService;

    /**
     * POST  /order-types : Create a new order_type.
     *
     * @param order_type the order_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new order_type, or with status 400 (Bad Request) if the order_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-types")
    @Timed
    public ResponseEntity<Order_type> createOrder_type(@Valid @RequestBody Order_type order_type) throws URISyntaxException {
        log.debug("REST request to save Order_type : {}", order_type);
        if (order_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("order_type", "idexists", "A new order_type cannot already have an ID")).body(null);
        }
        Order_type result = order_typeService.save(order_type);
        return ResponseEntity.created(new URI("/api/order-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("order_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-types : Updates an existing order_type.
     *
     * @param order_type the order_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated order_type,
     * or with status 400 (Bad Request) if the order_type is not valid,
     * or with status 500 (Internal Server Error) if the order_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-types")
    @Timed
    public ResponseEntity<Order_type> updateOrder_type(@Valid @RequestBody Order_type order_type) throws URISyntaxException {
        log.debug("REST request to update Order_type : {}", order_type);
        if (order_type.getId() == null) {
            return createOrder_type(order_type);
        }
        Order_type result = order_typeService.save(order_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("order_type", order_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-types : get all the order_types.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of order_types in body
     */
    @GetMapping("/order-types")
    @Timed
    public List<Order_type> getAllOrder_types() {
        log.debug("REST request to get all Order_types");
        return order_typeService.findAll();
    }

    /**
     * GET  /order-types/:id : get the "id" order_type.
     *
     * @param id the id of the order_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the order_type, or with status 404 (Not Found)
     */
    @GetMapping("/order-types/{id}")
    @Timed
    public ResponseEntity<Order_type> getOrder_type(@PathVariable Long id) {
        log.debug("REST request to get Order_type : {}", id);
        Order_type order_type = order_typeService.findOne(id);
        return Optional.ofNullable(order_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-types/:id : delete the "id" order_type.
     *
     * @param id the id of the order_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrder_type(@PathVariable Long id) {
        log.debug("REST request to delete Order_type : {}", id);
        order_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("order_type", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-types?query=:query : search for the order_type corresponding
     * to the query.
     *
     * @param query the query of the order_type search 
     * @return the result of the search
     */
    @GetMapping("/_search/order-types")
    @Timed
    public List<Order_type> searchOrder_types(@RequestParam String query) {
        log.debug("REST request to search Order_types for query {}", query);
        return order_typeService.search(query);
    }


}
