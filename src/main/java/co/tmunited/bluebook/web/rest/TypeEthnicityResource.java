package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeEthnicity;
import co.tmunited.bluebook.service.TypeEthnicityService;
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
 * REST controller for managing TypeEthnicity.
 */
@RestController
@RequestMapping("/api")
public class TypeEthnicityResource {

    private final Logger log = LoggerFactory.getLogger(TypeEthnicityResource.class);
        
    @Inject
    private TypeEthnicityService typeEthnicityService;

    /**
     * POST  /type-ethnicities : Create a new typeEthnicity.
     *
     * @param typeEthnicity the typeEthnicity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEthnicity, or with status 400 (Bad Request) if the typeEthnicity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-ethnicities")
    @Timed
    public ResponseEntity<TypeEthnicity> createTypeEthnicity(@Valid @RequestBody TypeEthnicity typeEthnicity) throws URISyntaxException {
        log.debug("REST request to save TypeEthnicity : {}", typeEthnicity);
        if (typeEthnicity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeEthnicity", "idexists", "A new typeEthnicity cannot already have an ID")).body(null);
        }
        TypeEthnicity result = typeEthnicityService.save(typeEthnicity);
        return ResponseEntity.created(new URI("/api/type-ethnicities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeEthnicity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-ethnicities : Updates an existing typeEthnicity.
     *
     * @param typeEthnicity the typeEthnicity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeEthnicity,
     * or with status 400 (Bad Request) if the typeEthnicity is not valid,
     * or with status 500 (Internal Server Error) if the typeEthnicity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-ethnicities")
    @Timed
    public ResponseEntity<TypeEthnicity> updateTypeEthnicity(@Valid @RequestBody TypeEthnicity typeEthnicity) throws URISyntaxException {
        log.debug("REST request to update TypeEthnicity : {}", typeEthnicity);
        if (typeEthnicity.getId() == null) {
            return createTypeEthnicity(typeEthnicity);
        }
        TypeEthnicity result = typeEthnicityService.save(typeEthnicity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeEthnicity", typeEthnicity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-ethnicities : get all the typeEthnicities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeEthnicities in body
     */
    @GetMapping("/type-ethnicities")
    @Timed
    public List<TypeEthnicity> getAllTypeEthnicities() {
        log.debug("REST request to get all TypeEthnicities");
        return typeEthnicityService.findAll();
    }

    /**
     * GET  /type-ethnicities/:id : get the "id" typeEthnicity.
     *
     * @param id the id of the typeEthnicity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEthnicity, or with status 404 (Not Found)
     */
    @GetMapping("/type-ethnicities/{id}")
    @Timed
    public ResponseEntity<TypeEthnicity> getTypeEthnicity(@PathVariable Long id) {
        log.debug("REST request to get TypeEthnicity : {}", id);
        TypeEthnicity typeEthnicity = typeEthnicityService.findOne(id);
        return Optional.ofNullable(typeEthnicity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-ethnicities/:id : delete the "id" typeEthnicity.
     *
     * @param id the id of the typeEthnicity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-ethnicities/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeEthnicity(@PathVariable Long id) {
        log.debug("REST request to delete TypeEthnicity : {}", id);
        typeEthnicityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeEthnicity", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-ethnicities?query=:query : search for the typeEthnicity corresponding
     * to the query.
     *
     * @param query the query of the typeEthnicity search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-ethnicities")
    @Timed
    public List<TypeEthnicity> searchTypeEthnicities(@RequestParam String query) {
        log.debug("REST request to search TypeEthnicities for query {}", query);
        return typeEthnicityService.search(query);
    }


}
