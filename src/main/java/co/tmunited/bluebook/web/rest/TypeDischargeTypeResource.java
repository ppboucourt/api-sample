package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeDischargeType;
import co.tmunited.bluebook.service.TypeDischargeTypeService;
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
 * REST controller for managing TypeDischargeType.
 */
@RestController
@RequestMapping("/api")
public class TypeDischargeTypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeDischargeTypeResource.class);
        
    @Inject
    private TypeDischargeTypeService typeDischargeTypeService;

    /**
     * POST  /type-discharge-types : Create a new typeDischargeType.
     *
     * @param typeDischargeType the typeDischargeType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDischargeType, or with status 400 (Bad Request) if the typeDischargeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-discharge-types")
    @Timed
    public ResponseEntity<TypeDischargeType> createTypeDischargeType(@Valid @RequestBody TypeDischargeType typeDischargeType) throws URISyntaxException {
        log.debug("REST request to save TypeDischargeType : {}", typeDischargeType);
        if (typeDischargeType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeDischargeType", "idexists", "A new typeDischargeType cannot already have an ID")).body(null);
        }
        TypeDischargeType result = typeDischargeTypeService.save(typeDischargeType);
        return ResponseEntity.created(new URI("/api/type-discharge-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeDischargeType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-discharge-types : Updates an existing typeDischargeType.
     *
     * @param typeDischargeType the typeDischargeType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDischargeType,
     * or with status 400 (Bad Request) if the typeDischargeType is not valid,
     * or with status 500 (Internal Server Error) if the typeDischargeType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-discharge-types")
    @Timed
    public ResponseEntity<TypeDischargeType> updateTypeDischargeType(@Valid @RequestBody TypeDischargeType typeDischargeType) throws URISyntaxException {
        log.debug("REST request to update TypeDischargeType : {}", typeDischargeType);
        if (typeDischargeType.getId() == null) {
            return createTypeDischargeType(typeDischargeType);
        }
        TypeDischargeType result = typeDischargeTypeService.save(typeDischargeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeDischargeType", typeDischargeType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-discharge-types : get all the typeDischargeTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeDischargeTypes in body
     */
    @GetMapping("/type-discharge-types")
    @Timed
    public List<TypeDischargeType> getAllTypeDischargeTypes() {
        log.debug("REST request to get all TypeDischargeTypes");
        return typeDischargeTypeService.findAll();
    }

    /**
     * GET  /type-discharge-types/:id : get the "id" typeDischargeType.
     *
     * @param id the id of the typeDischargeType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDischargeType, or with status 404 (Not Found)
     */
    @GetMapping("/type-discharge-types/{id}")
    @Timed
    public ResponseEntity<TypeDischargeType> getTypeDischargeType(@PathVariable Long id) {
        log.debug("REST request to get TypeDischargeType : {}", id);
        TypeDischargeType typeDischargeType = typeDischargeTypeService.findOne(id);
        return Optional.ofNullable(typeDischargeType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-discharge-types/:id : delete the "id" typeDischargeType.
     *
     * @param id the id of the typeDischargeType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-discharge-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeDischargeType(@PathVariable Long id) {
        log.debug("REST request to delete TypeDischargeType : {}", id);
        typeDischargeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeDischargeType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-discharge-types?query=:query : search for the typeDischargeType corresponding
     * to the query.
     *
     * @param query the query of the typeDischargeType search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-discharge-types")
    @Timed
    public List<TypeDischargeType> searchTypeDischargeTypes(@RequestParam String query) {
        log.debug("REST request to search TypeDischargeTypes for query {}", query);
        return typeDischargeTypeService.search(query);
    }


}
