package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.EvaluationContent;
import co.tmunited.bluebook.service.EvaluationContentService;
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
 * REST controller for managing EvaluationContent.
 */
@RestController
@RequestMapping("/api")
public class EvaluationContentResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationContentResource.class);
        
    @Inject
    private EvaluationContentService evaluationContentService;

    /**
     * POST  /evaluation-contents : Create a new evaluationContent.
     *
     * @param evaluationContent the evaluationContent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationContent, or with status 400 (Bad Request) if the evaluationContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-contents")
    @Timed
    public ResponseEntity<EvaluationContent> createEvaluationContent(@RequestBody EvaluationContent evaluationContent) throws URISyntaxException {
        log.debug("REST request to save EvaluationContent : {}", evaluationContent);
        if (evaluationContent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluationContent", "idexists", "A new evaluationContent cannot already have an ID")).body(null);
        }
        EvaluationContent result = evaluationContentService.save(evaluationContent);
        return ResponseEntity.created(new URI("/api/evaluation-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluationContent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-contents : Updates an existing evaluationContent.
     *
     * @param evaluationContent the evaluationContent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationContent,
     * or with status 400 (Bad Request) if the evaluationContent is not valid,
     * or with status 500 (Internal Server Error) if the evaluationContent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-contents")
    @Timed
    public ResponseEntity<EvaluationContent> updateEvaluationContent(@RequestBody EvaluationContent evaluationContent) throws URISyntaxException {
        log.debug("REST request to update EvaluationContent : {}", evaluationContent);
        if (evaluationContent.getId() == null) {
            return createEvaluationContent(evaluationContent);
        }
        EvaluationContent result = evaluationContentService.save(evaluationContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluationContent", evaluationContent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-contents : get all the evaluationContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationContents in body
     */
    @GetMapping("/evaluation-contents")
    @Timed
    public List<EvaluationContent> getAllEvaluationContents() {
        log.debug("REST request to get all EvaluationContents");
        return evaluationContentService.findAll();
    }

    /**
     * GET  /evaluation-contents/:id : get the "id" evaluationContent.
     *
     * @param id the id of the evaluationContent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationContent, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-contents/{id}")
    @Timed
    public ResponseEntity<EvaluationContent> getEvaluationContent(@PathVariable Long id) {
        log.debug("REST request to get EvaluationContent : {}", id);
        EvaluationContent evaluationContent = evaluationContentService.findOne(id);
        return Optional.ofNullable(evaluationContent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluation-contents/:id : delete the "id" evaluationContent.
     *
     * @param id the id of the evaluationContent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationContent(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationContent : {}", id);
        evaluationContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluationContent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-contents?query=:query : search for the evaluationContent corresponding
     * to the query.
     *
     * @param query the query of the evaluationContent search 
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-contents")
    @Timed
    public List<EvaluationContent> searchEvaluationContents(@RequestParam String query) {
        log.debug("REST request to search EvaluationContents for query {}", query);
        return evaluationContentService.search(query);
    }


}
