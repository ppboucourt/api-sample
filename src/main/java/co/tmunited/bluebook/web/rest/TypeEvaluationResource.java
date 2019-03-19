package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeEvaluation;
import co.tmunited.bluebook.service.TypeEvaluationService;
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
 * REST controller for managing TypeEvaluation.
 */
@RestController
@RequestMapping("/api")
public class TypeEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(TypeEvaluationResource.class);
        
    @Inject
    private TypeEvaluationService typeEvaluationService;

    /**
     * POST  /type-evaluations : Create a new typeEvaluation.
     *
     * @param typeEvaluation the typeEvaluation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEvaluation, or with status 400 (Bad Request) if the typeEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-evaluations")
    @Timed
    public ResponseEntity<TypeEvaluation> createTypeEvaluation(@RequestBody TypeEvaluation typeEvaluation) throws URISyntaxException {
        log.debug("REST request to save TypeEvaluation : {}", typeEvaluation);
        if (typeEvaluation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeEvaluation", "idexists", "A new typeEvaluation cannot already have an ID")).body(null);
        }
        TypeEvaluation result = typeEvaluationService.save(typeEvaluation);
        return ResponseEntity.created(new URI("/api/type-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeEvaluation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-evaluations : Updates an existing typeEvaluation.
     *
     * @param typeEvaluation the typeEvaluation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeEvaluation,
     * or with status 400 (Bad Request) if the typeEvaluation is not valid,
     * or with status 500 (Internal Server Error) if the typeEvaluation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-evaluations")
    @Timed
    public ResponseEntity<TypeEvaluation> updateTypeEvaluation(@RequestBody TypeEvaluation typeEvaluation) throws URISyntaxException {
        log.debug("REST request to update TypeEvaluation : {}", typeEvaluation);
        if (typeEvaluation.getId() == null) {
            return createTypeEvaluation(typeEvaluation);
        }
        TypeEvaluation result = typeEvaluationService.save(typeEvaluation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeEvaluation", typeEvaluation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-evaluations : get all the typeEvaluations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeEvaluations in body
     */
    @GetMapping("/type-evaluations")
    @Timed
    public List<TypeEvaluation> getAllTypeEvaluations() {
        log.debug("REST request to get all TypeEvaluations");
        return typeEvaluationService.findAll();
    }

    /**
     * GET  /type-evaluations/:id : get the "id" typeEvaluation.
     *
     * @param id the id of the typeEvaluation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEvaluation, or with status 404 (Not Found)
     */
    @GetMapping("/type-evaluations/{id}")
    @Timed
    public ResponseEntity<TypeEvaluation> getTypeEvaluation(@PathVariable Long id) {
        log.debug("REST request to get TypeEvaluation : {}", id);
        TypeEvaluation typeEvaluation = typeEvaluationService.findOne(id);
        return Optional.ofNullable(typeEvaluation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-evaluations/:id : delete the "id" typeEvaluation.
     *
     * @param id the id of the typeEvaluation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-evaluations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete TypeEvaluation : {}", id);
        typeEvaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeEvaluation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-evaluations?query=:query : search for the typeEvaluation corresponding
     * to the query.
     *
     * @param query the query of the typeEvaluation search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-evaluations")
    @Timed
    public List<TypeEvaluation> searchTypeEvaluations(@RequestParam String query) {
        log.debug("REST request to search TypeEvaluations for query {}", query);
        return typeEvaluationService.search(query);
    }


}
