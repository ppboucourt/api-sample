package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.EvaluationItems;
import co.tmunited.bluebook.service.EvaluationItemsService;
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
 * REST controller for managing EvaluationItems.
 */
@RestController
@RequestMapping("/api")
public class EvaluationItemsResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationItemsResource.class);
        
    @Inject
    private EvaluationItemsService evaluationItemsService;

    /**
     * POST  /evaluation-items : Create a new evaluationItems.
     *
     * @param evaluationItems the evaluationItems to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationItems, or with status 400 (Bad Request) if the evaluationItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-items")
    @Timed
    public ResponseEntity<EvaluationItems> createEvaluationItems(@RequestBody EvaluationItems evaluationItems) throws URISyntaxException {
        log.debug("REST request to save EvaluationItems : {}", evaluationItems);
        if (evaluationItems.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluationItems", "idexists", "A new evaluationItems cannot already have an ID")).body(null);
        }
        EvaluationItems result = evaluationItemsService.save(evaluationItems);
        return ResponseEntity.created(new URI("/api/evaluation-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluationItems", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-items : Updates an existing evaluationItems.
     *
     * @param evaluationItems the evaluationItems to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationItems,
     * or with status 400 (Bad Request) if the evaluationItems is not valid,
     * or with status 500 (Internal Server Error) if the evaluationItems couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-items")
    @Timed
    public ResponseEntity<EvaluationItems> updateEvaluationItems(@RequestBody EvaluationItems evaluationItems) throws URISyntaxException {
        log.debug("REST request to update EvaluationItems : {}", evaluationItems);
        if (evaluationItems.getId() == null) {
            return createEvaluationItems(evaluationItems);
        }
        EvaluationItems result = evaluationItemsService.save(evaluationItems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluationItems", evaluationItems.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-items : get all the evaluationItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationItems in body
     */
    @GetMapping("/evaluation-items")
    @Timed
    public List<EvaluationItems> getAllEvaluationItems() {
        log.debug("REST request to get all EvaluationItems");
        return evaluationItemsService.findAll();
    }

    /**
     * GET  /evaluation-items/:id : get the "id" evaluationItems.
     *
     * @param id the id of the evaluationItems to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationItems, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-items/{id}")
    @Timed
    public ResponseEntity<EvaluationItems> getEvaluationItems(@PathVariable Long id) {
        log.debug("REST request to get EvaluationItems : {}", id);
        EvaluationItems evaluationItems = evaluationItemsService.findOne(id);
        return Optional.ofNullable(evaluationItems)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluation-items/:id : delete the "id" evaluationItems.
     *
     * @param id the id of the evaluationItems to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationItems(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationItems : {}", id);
        evaluationItemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluationItems", id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-items?query=:query : search for the evaluationItems corresponding
     * to the query.
     *
     * @param query the query of the evaluationItems search 
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-items")
    @Timed
    public List<EvaluationItems> searchEvaluationItems(@RequestParam String query) {
        log.debug("REST request to search EvaluationItems for query {}", query);
        return evaluationItemsService.search(query);
    }


}
