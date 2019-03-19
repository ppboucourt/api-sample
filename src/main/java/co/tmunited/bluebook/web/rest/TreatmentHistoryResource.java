package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TreatmentHistory;
import co.tmunited.bluebook.service.TreatmentHistoryService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TreatmentHistory.
 */
@RestController
@RequestMapping("/api")
public class TreatmentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentHistoryResource.class);
        
    @Inject
    private TreatmentHistoryService treatmentHistoryService;

    /**
     * POST  /treatment-histories : Create a new treatmentHistory.
     *
     * @param treatmentHistory the treatmentHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentHistory, or with status 400 (Bad Request) if the treatmentHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatment-histories")
    @Timed
    public ResponseEntity<TreatmentHistory> createTreatmentHistory(@RequestBody TreatmentHistory treatmentHistory) throws URISyntaxException {
        log.debug("REST request to save TreatmentHistory : {}", treatmentHistory);
        if (treatmentHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("treatmentHistory", "idexists", "A new treatmentHistory cannot already have an ID")).body(null);
        }
        TreatmentHistory result = treatmentHistoryService.save(treatmentHistory);
        return ResponseEntity.created(new URI("/api/treatment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("treatmentHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatment-histories : Updates an existing treatmentHistory.
     *
     * @param treatmentHistory the treatmentHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentHistory,
     * or with status 400 (Bad Request) if the treatmentHistory is not valid,
     * or with status 500 (Internal Server Error) if the treatmentHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatment-histories")
    @Timed
    public ResponseEntity<TreatmentHistory> updateTreatmentHistory(@RequestBody TreatmentHistory treatmentHistory) throws URISyntaxException {
        log.debug("REST request to update TreatmentHistory : {}", treatmentHistory);
        if (treatmentHistory.getId() == null) {
            return createTreatmentHistory(treatmentHistory);
        }
        TreatmentHistory result = treatmentHistoryService.save(treatmentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("treatmentHistory", treatmentHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-histories : get all the treatmentHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentHistories in body
     */
    @GetMapping("/treatment-histories")
    @Timed
    public List<TreatmentHistory> getAllTreatmentHistories() {
        log.debug("REST request to get all TreatmentHistories");
        return treatmentHistoryService.findAll();
    }

    /**
     * GET  /treatment-histories/:id : get the "id" treatmentHistory.
     *
     * @param id the id of the treatmentHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentHistory, or with status 404 (Not Found)
     */
    @GetMapping("/treatment-histories/{id}")
    @Timed
    public ResponseEntity<TreatmentHistory> getTreatmentHistory(@PathVariable Long id) {
        log.debug("REST request to get TreatmentHistory : {}", id);
        TreatmentHistory treatmentHistory = treatmentHistoryService.findOne(id);
        return Optional.ofNullable(treatmentHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /treatment-histories/:id : delete the "id" treatmentHistory.
     *
     * @param id the id of the treatmentHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatment-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatmentHistory(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentHistory : {}", id);
        treatmentHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("treatmentHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/treatment-histories?query=:query : search for the treatmentHistory corresponding
     * to the query.
     *
     * @param query the query of the treatmentHistory search 
     * @return the result of the search
     */
    @GetMapping("/_search/treatment-histories")
    @Timed
    public List<TreatmentHistory> searchTreatmentHistories(@RequestParam String query) {
        log.debug("REST request to search TreatmentHistories for query {}", query);
        return treatmentHistoryService.search(query);
    }


}
