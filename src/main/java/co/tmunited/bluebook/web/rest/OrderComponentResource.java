package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.OrderComponent;
import co.tmunited.bluebook.service.OrderComponentService;
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
 * REST controller for managing OrderComponent.
 */
@RestController
@RequestMapping("/api")
public class OrderComponentResource {

    private final Logger log = LoggerFactory.getLogger(OrderComponentResource.class);
        
    @Inject
    private OrderComponentService orderComponentService;

    /**
     * POST  /order-components : Create a new orderComponent.
     *
     * @param orderComponent the orderComponent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderComponent, or with status 400 (Bad Request) if the orderComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-components")
    @Timed
    public ResponseEntity<OrderComponent> createOrderComponent(@Valid @RequestBody OrderComponent orderComponent) throws URISyntaxException {
        log.debug("REST request to save OrderComponent : {}", orderComponent);
        if (orderComponent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderComponent", "idexists", "A new orderComponent cannot already have an ID")).body(null);
        }
        OrderComponent result = orderComponentService.save(orderComponent);
        return ResponseEntity.created(new URI("/api/order-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderComponent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-components : Updates an existing orderComponent.
     *
     * @param orderComponent the orderComponent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderComponent,
     * or with status 400 (Bad Request) if the orderComponent is not valid,
     * or with status 500 (Internal Server Error) if the orderComponent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-components")
    @Timed
    public ResponseEntity<OrderComponent> updateOrderComponent(@Valid @RequestBody OrderComponent orderComponent) throws URISyntaxException {
        log.debug("REST request to update OrderComponent : {}", orderComponent);
        if (orderComponent.getId() == null) {
            return createOrderComponent(orderComponent);
        }
        OrderComponent result = orderComponentService.save(orderComponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderComponent", orderComponent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-components : get all the orderComponents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderComponents in body
     */
    @GetMapping("/order-components")
    @Timed
    public List<OrderComponent> getAllOrderComponents() {
        log.debug("REST request to get all OrderComponents");
        return orderComponentService.findAll();
    }

    /**
     * GET  /order-components/:id : get the "id" orderComponent.
     *
     * @param id the id of the orderComponent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderComponent, or with status 404 (Not Found)
     */
    @GetMapping("/order-components/{id}")
    @Timed
    public ResponseEntity<OrderComponent> getOrderComponent(@PathVariable Long id) {
        log.debug("REST request to get OrderComponent : {}", id);
        OrderComponent orderComponent = orderComponentService.findOne(id);
        return Optional.ofNullable(orderComponent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-components/:id : delete the "id" orderComponent.
     *
     * @param id the id of the orderComponent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-components/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderComponent(@PathVariable Long id) {
        log.debug("REST request to delete OrderComponent : {}", id);
        orderComponentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderComponent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-components?query=:query : search for the orderComponent corresponding
     * to the query.
     *
     * @param query the query of the orderComponent search 
     * @return the result of the search
     */
    @GetMapping("/_search/order-components")
    @Timed
    public List<OrderComponent> searchOrderComponents(@RequestParam String query) {
        log.debug("REST request to search OrderComponents for query {}", query);
        return orderComponentService.search(query);
    }


}
