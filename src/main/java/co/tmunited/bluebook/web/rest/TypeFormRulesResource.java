package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeFormRules;
import co.tmunited.bluebook.service.TypeFormRulesService;
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
 * REST controller for managing TypeFormRules.
 */
@RestController
@RequestMapping("/api")
public class TypeFormRulesResource {

    private final Logger log = LoggerFactory.getLogger(TypeFormRulesResource.class);
        
    @Inject
    private TypeFormRulesService typeFormRulesService;

    /**
     * POST  /type-form-rules : Create a new typeFormRules.
     *
     * @param typeFormRules the typeFormRules to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeFormRules, or with status 400 (Bad Request) if the typeFormRules has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-form-rules")
    @Timed
    public ResponseEntity<TypeFormRules> createTypeFormRules(@Valid @RequestBody TypeFormRules typeFormRules) throws URISyntaxException {
        log.debug("REST request to save TypeFormRules : {}", typeFormRules);
        if (typeFormRules.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeFormRules", "idexists", "A new typeFormRules cannot already have an ID")).body(null);
        }
        TypeFormRules result = typeFormRulesService.save(typeFormRules);
        return ResponseEntity.created(new URI("/api/type-form-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeFormRules", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-form-rules : Updates an existing typeFormRules.
     *
     * @param typeFormRules the typeFormRules to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeFormRules,
     * or with status 400 (Bad Request) if the typeFormRules is not valid,
     * or with status 500 (Internal Server Error) if the typeFormRules couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-form-rules")
    @Timed
    public ResponseEntity<TypeFormRules> updateTypeFormRules(@Valid @RequestBody TypeFormRules typeFormRules) throws URISyntaxException {
        log.debug("REST request to update TypeFormRules : {}", typeFormRules);
        if (typeFormRules.getId() == null) {
            return createTypeFormRules(typeFormRules);
        }
        TypeFormRules result = typeFormRulesService.save(typeFormRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeFormRules", typeFormRules.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-form-rules : get all the typeFormRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeFormRules in body
     */
    @GetMapping("/type-form-rules")
    @Timed
    public List<TypeFormRules> getAllTypeFormRules() {
        log.debug("REST request to get all TypeFormRules");
        return typeFormRulesService.findAll();
    }

    /**
     * GET  /type-form-rules/:id : get the "id" typeFormRules.
     *
     * @param id the id of the typeFormRules to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeFormRules, or with status 404 (Not Found)
     */
    @GetMapping("/type-form-rules/{id}")
    @Timed
    public ResponseEntity<TypeFormRules> getTypeFormRules(@PathVariable Long id) {
        log.debug("REST request to get TypeFormRules : {}", id);
        TypeFormRules typeFormRules = typeFormRulesService.findOne(id);
        return Optional.ofNullable(typeFormRules)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-form-rules/:id : delete the "id" typeFormRules.
     *
     * @param id the id of the typeFormRules to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-form-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeFormRules(@PathVariable Long id) {
        log.debug("REST request to delete TypeFormRules : {}", id);
        typeFormRulesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeFormRules", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-form-rules?query=:query : search for the typeFormRules corresponding
     * to the query.
     *
     * @param query the query of the typeFormRules search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-form-rules")
    @Timed
    public List<TypeFormRules> searchTypeFormRules(@RequestParam String query) {
        log.debug("REST request to search TypeFormRules for query {}", query);
        return typeFormRulesService.search(query);
    }


}
