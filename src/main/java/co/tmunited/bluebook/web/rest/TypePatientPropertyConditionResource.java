package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePatientPropertyCondition;
import co.tmunited.bluebook.service.TypePatientPropertyConditionService;
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
 * REST controller for managing TypePatientPropertyCondition.
 */
@RestController
@RequestMapping("/api")
public class TypePatientPropertyConditionResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientPropertyConditionResource.class);
        
    @Inject
    private TypePatientPropertyConditionService typePatientPropertyConditionService;

    /**
     * POST  /type-patient-property-conditions : Create a new typePatientPropertyCondition.
     *
     * @param typePatientPropertyCondition the typePatientPropertyCondition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePatientPropertyCondition, or with status 400 (Bad Request) if the typePatientPropertyCondition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-patient-property-conditions")
    @Timed
    public ResponseEntity<TypePatientPropertyCondition> createTypePatientPropertyCondition(@RequestBody TypePatientPropertyCondition typePatientPropertyCondition) throws URISyntaxException {
        log.debug("REST request to save TypePatientPropertyCondition : {}", typePatientPropertyCondition);
        if (typePatientPropertyCondition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePatientPropertyCondition", "idexists", "A new typePatientPropertyCondition cannot already have an ID")).body(null);
        }
        TypePatientPropertyCondition result = typePatientPropertyConditionService.save(typePatientPropertyCondition);
        return ResponseEntity.created(new URI("/api/type-patient-property-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePatientPropertyCondition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-patient-property-conditions : Updates an existing typePatientPropertyCondition.
     *
     * @param typePatientPropertyCondition the typePatientPropertyCondition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePatientPropertyCondition,
     * or with status 400 (Bad Request) if the typePatientPropertyCondition is not valid,
     * or with status 500 (Internal Server Error) if the typePatientPropertyCondition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-patient-property-conditions")
    @Timed
    public ResponseEntity<TypePatientPropertyCondition> updateTypePatientPropertyCondition(@RequestBody TypePatientPropertyCondition typePatientPropertyCondition) throws URISyntaxException {
        log.debug("REST request to update TypePatientPropertyCondition : {}", typePatientPropertyCondition);
        if (typePatientPropertyCondition.getId() == null) {
            return createTypePatientPropertyCondition(typePatientPropertyCondition);
        }
        TypePatientPropertyCondition result = typePatientPropertyConditionService.save(typePatientPropertyCondition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePatientPropertyCondition", typePatientPropertyCondition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-patient-property-conditions : get all the typePatientPropertyConditions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientPropertyConditions in body
     */
    @GetMapping("/type-patient-property-conditions")
    @Timed
    public List<TypePatientPropertyCondition> getAllTypePatientPropertyConditions() {
        log.debug("REST request to get all TypePatientPropertyConditions");
        return typePatientPropertyConditionService.findAll();
    }

    /**
     * GET  /type-patient-property-conditions/:id : get the "id" typePatientPropertyCondition.
     *
     * @param id the id of the typePatientPropertyCondition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePatientPropertyCondition, or with status 404 (Not Found)
     */
    @GetMapping("/type-patient-property-conditions/{id}")
    @Timed
    public ResponseEntity<TypePatientPropertyCondition> getTypePatientPropertyCondition(@PathVariable Long id) {
        log.debug("REST request to get TypePatientPropertyCondition : {}", id);
        TypePatientPropertyCondition typePatientPropertyCondition = typePatientPropertyConditionService.findOne(id);
        return Optional.ofNullable(typePatientPropertyCondition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-patient-property-conditions/:id : delete the "id" typePatientPropertyCondition.
     *
     * @param id the id of the typePatientPropertyCondition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-patient-property-conditions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePatientPropertyCondition(@PathVariable Long id) {
        log.debug("REST request to delete TypePatientPropertyCondition : {}", id);
        typePatientPropertyConditionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePatientPropertyCondition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-patient-property-conditions?query=:query : search for the typePatientPropertyCondition corresponding
     * to the query.
     *
     * @param query the query of the typePatientPropertyCondition search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-patient-property-conditions")
    @Timed
    public List<TypePatientPropertyCondition> searchTypePatientPropertyConditions(@RequestParam String query) {
        log.debug("REST request to search TypePatientPropertyConditions for query {}", query);
        return typePatientPropertyConditionService.search(query);
    }


}
