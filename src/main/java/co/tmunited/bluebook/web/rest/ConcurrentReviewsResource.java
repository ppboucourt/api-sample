package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ConcurrentReviews;
import co.tmunited.bluebook.service.ConcurrentReviewsService;
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
 * REST controller for managing ConcurrentReviews.
 */
@RestController
@RequestMapping("/api")
public class ConcurrentReviewsResource {

    private final Logger log = LoggerFactory.getLogger(ConcurrentReviewsResource.class);

    @Inject
    private ConcurrentReviewsService concurrentReviewsService;

    /**
     * POST  /concurrent-reviews : Create a new concurrentReviews.
     *
     * @param concurrentReviews the concurrentReviews to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concurrentReviews, or with status 400 (Bad Request) if the concurrentReviews has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concurrent-reviews")
    @Timed
    public ResponseEntity<ConcurrentReviews> createConcurrentReviews(@Valid @RequestBody ConcurrentReviews concurrentReviews) throws URISyntaxException {
        log.debug("REST request to save ConcurrentReviews : {}", concurrentReviews);
        if (concurrentReviews.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("concurrentReviews", "idexists", "A new concurrentReviews cannot already have an ID")).body(null);
        }
        ConcurrentReviews result = concurrentReviewsService.save(concurrentReviews);
        return ResponseEntity.created(new URI("/api/concurrent-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("concurrentReviews", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concurrent-reviews : Updates an existing concurrentReviews.
     *
     * @param concurrentReviews the concurrentReviews to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concurrentReviews,
     * or with status 400 (Bad Request) if the concurrentReviews is not valid,
     * or with status 500 (Internal Server Error) if the concurrentReviews couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concurrent-reviews")
    @Timed
    public ResponseEntity<ConcurrentReviews> updateConcurrentReviews(@Valid @RequestBody ConcurrentReviews concurrentReviews) throws URISyntaxException {
        log.debug("REST request to update ConcurrentReviews : {}", concurrentReviews);
        if (concurrentReviews.getId() == null) {
            return createConcurrentReviews(concurrentReviews);
        }
        ConcurrentReviews result = concurrentReviewsService.save(concurrentReviews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("concurrentReviews", concurrentReviews.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concurrent-reviews : get all the concurrentReviews.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of concurrentReviews in body
     */
    @GetMapping("/concurrent-reviews")
    @Timed
    public List<ConcurrentReviews> getAllConcurrentReviews() {
        log.debug("REST request to get all ConcurrentReviews");
        return concurrentReviewsService.findAll();
    }

    /**
     * GET  /concurrent-reviews/:id : get the "id" concurrentReviews.
     *
     * @param id the id of the concurrentReviews to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concurrentReviews, or with status 404 (Not Found)
     */
    @GetMapping("/concurrent-reviews/{id}")
    @Timed
    public ResponseEntity<ConcurrentReviews> getConcurrentReviews(@PathVariable Long id) {
        log.debug("REST request to get ConcurrentReviews : {}", id);
        ConcurrentReviews concurrentReviews = concurrentReviewsService.findOne(id);
        return Optional.ofNullable(concurrentReviews)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /concurrent-reviews/:id : delete the "id" concurrentReviews.
     *
     * @param id the id of the concurrentReviews to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concurrent-reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteConcurrentReviews(@PathVariable Long id) {
        log.debug("REST request to delete ConcurrentReviews : {}", id);
        concurrentReviewsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("concurrentReviews", id.toString())).build();
    }

    /**
     * SEARCH  /_search/concurrent-reviews?query=:query : search for the concurrentReviews corresponding
     * to the query.
     *
     * @param query the query of the concurrentReviews search
     * @return the result of the search
     */
    @GetMapping("/_search/concurrent-reviews")
    @Timed
    public List<ConcurrentReviews> searchConcurrentReviews(@RequestParam String query) {
        log.debug("REST request to search ConcurrentReviews for query {}", query);
        return concurrentReviewsService.search(query);
    }


    /**
     * GET  /concurrent-reviewsByChart/:id : get the "id" chart.
     *
     * @param id the id of the chart
     * @return the List<ConcurrentReviews>
     */
    @GetMapping("/concurrent-reviews/chart/{id}")
    @Timed
    public List<ConcurrentReviews> getConcurrentReviewsByChart(@PathVariable Long id) {
        log.debug("REST request to get ConcurrentReviews in Chart: {}", id);

        return concurrentReviewsService.findAllByDelStatusIsFalseAndChartConcurrentReviewsById(id);
    }
}
