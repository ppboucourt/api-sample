package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.OrderFrequency;
import co.tmunited.bluebook.repository.OrderFrequencyRepository;
import co.tmunited.bluebook.repository.search.OrderFrequencySearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing OrderFrequency.
 */
@RestController
@RequestMapping("/api")
public class OrderFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(OrderFrequencyResource.class);

    @Inject
    private OrderFrequencyRepository orderFrequencyRepository;

    @Inject
    private OrderFrequencySearchRepository orderFrequencySearchRepository;

    /**
     * POST  /order-frequencies : Create a new orderFrequency.
     *
     * @param orderFrequency the orderFrequency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderFrequency, or with status 400 (Bad Request) if the orderFrequency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-frequencies")
    @Timed
    public ResponseEntity<OrderFrequency> createOrderFrequency(@RequestBody OrderFrequency orderFrequency) throws URISyntaxException {
        log.debug("REST request to save OrderFrequency : {}", orderFrequency);
        if (orderFrequency.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderFrequency", "idexists", "A new orderFrequency cannot already have an ID")).body(null);
        }
        OrderFrequency result = orderFrequencyRepository.save(orderFrequency);
        orderFrequencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/order-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderFrequency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-frequencies : Updates an existing orderFrequency.
     *
     * @param orderFrequency the orderFrequency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderFrequency,
     * or with status 400 (Bad Request) if the orderFrequency is not valid,
     * or with status 500 (Internal Server Error) if the orderFrequency couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-frequencies")
    @Timed
    public ResponseEntity<OrderFrequency> updateOrderFrequency(@RequestBody OrderFrequency orderFrequency) throws URISyntaxException {
        log.debug("REST request to update OrderFrequency : {}", orderFrequency);
        if (orderFrequency.getId() == null) {
            return createOrderFrequency(orderFrequency);
        }
        OrderFrequency result = orderFrequencyRepository.save(orderFrequency);
        orderFrequencySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderFrequency", orderFrequency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-frequencies : get all the orderFrequencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderFrequencies in body
     */
    @GetMapping("/order-frequencies")
    @Timed
    public List<OrderFrequency> getAllOrderFrequencies() {
        log.debug("REST request to get all OrderFrequencies");
        List<OrderFrequency> orderFrequencies = orderFrequencyRepository.findAll();
        return orderFrequencies;
    }

    /**
     * GET  /order-frequencies/:id : get the "id" orderFrequency.
     *
     * @param id the id of the orderFrequency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderFrequency, or with status 404 (Not Found)
     */
    @GetMapping("/order-frequencies/{id}")
    @Timed
    public ResponseEntity<OrderFrequency> getOrderFrequency(@PathVariable Long id) {
        log.debug("REST request to get OrderFrequency : {}", id);
        OrderFrequency orderFrequency = orderFrequencyRepository.findOne(id);
        return Optional.ofNullable(orderFrequency)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-frequencies/:id : delete the "id" orderFrequency.
     *
     * @param id the id of the orderFrequency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-frequencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderFrequency(@PathVariable Long id) {
        log.debug("REST request to delete OrderFrequency : {}", id);
        orderFrequencyRepository.delete(id);
        orderFrequencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderFrequency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-frequencies?query=:query : search for the orderFrequency corresponding
     * to the query.
     *
     * @param query the query of the orderFrequency search
     * @return the result of the search
     */
    @GetMapping("/_search/order-frequencies")
    @Timed
    public List<OrderFrequency> searchOrderFrequencies(@RequestParam String query) {
        log.debug("REST request to search OrderFrequencies for query {}", query);
        return StreamSupport
            .stream(orderFrequencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /order-frequencies : get all the orderFrequencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderFrequencies in body
     */
    @GetMapping("/order-frequencies-by-order-type/{orderType}")
    @Timed
    public List<OrderFrequency> getAllOrderFrequenciesByOrderType(@PathVariable String orderType) {
        log.debug("REST request to get all OrderFrequencies by order type " + orderType);
        switch (orderType) {
            case "STANDING": {
                return orderFrequencyRepository.findAllByDaysLessThanAndDaysGreaterThan(361, 1);
            }
            case "RECURRING": {
                return orderFrequencyRepository.findAllByDaysLessThanAndDaysGreaterThan(180, 1);
            }
            default: {
                return orderFrequencyRepository.findAllByDaysLessThanAndDaysGreaterThan(1, -1);
            }
        }
    }
}
