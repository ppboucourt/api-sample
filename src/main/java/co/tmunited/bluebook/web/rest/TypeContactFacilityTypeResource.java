package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeContactFacilityType;
import co.tmunited.bluebook.service.TypeContactFacilityTypeService;
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
 * REST controller for managing TypeContactFacilityType.
 */
@RestController
@RequestMapping("/api")
public class TypeContactFacilityTypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeContactFacilityTypeResource.class);
        
    @Inject
    private TypeContactFacilityTypeService typeContactFacilityTypeService;

    /**
     * POST  /type-contact-facility-types : Create a new typeContactFacilityType.
     *
     * @param typeContactFacilityType the typeContactFacilityType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeContactFacilityType, or with status 400 (Bad Request) if the typeContactFacilityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-contact-facility-types")
    @Timed
    public ResponseEntity<TypeContactFacilityType> createTypeContactFacilityType(@RequestBody TypeContactFacilityType typeContactFacilityType) throws URISyntaxException {
        log.debug("REST request to save TypeContactFacilityType : {}", typeContactFacilityType);
        if (typeContactFacilityType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeContactFacilityType", "idexists", "A new typeContactFacilityType cannot already have an ID")).body(null);
        }
        TypeContactFacilityType result = typeContactFacilityTypeService.save(typeContactFacilityType);
        return ResponseEntity.created(new URI("/api/type-contact-facility-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeContactFacilityType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-contact-facility-types : Updates an existing typeContactFacilityType.
     *
     * @param typeContactFacilityType the typeContactFacilityType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeContactFacilityType,
     * or with status 400 (Bad Request) if the typeContactFacilityType is not valid,
     * or with status 500 (Internal Server Error) if the typeContactFacilityType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-contact-facility-types")
    @Timed
    public ResponseEntity<TypeContactFacilityType> updateTypeContactFacilityType(@RequestBody TypeContactFacilityType typeContactFacilityType) throws URISyntaxException {
        log.debug("REST request to update TypeContactFacilityType : {}", typeContactFacilityType);
        if (typeContactFacilityType.getId() == null) {
            return createTypeContactFacilityType(typeContactFacilityType);
        }
        TypeContactFacilityType result = typeContactFacilityTypeService.save(typeContactFacilityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeContactFacilityType", typeContactFacilityType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-contact-facility-types : get all the typeContactFacilityTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeContactFacilityTypes in body
     */
    @GetMapping("/type-contact-facility-types")
    @Timed
    public List<TypeContactFacilityType> getAllTypeContactFacilityTypes() {
        log.debug("REST request to get all TypeContactFacilityTypes");
        return typeContactFacilityTypeService.findAll();
    }

    /**
     * GET  /type-contact-facility-types/:id : get the "id" typeContactFacilityType.
     *
     * @param id the id of the typeContactFacilityType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeContactFacilityType, or with status 404 (Not Found)
     */
    @GetMapping("/type-contact-facility-types/{id}")
    @Timed
    public ResponseEntity<TypeContactFacilityType> getTypeContactFacilityType(@PathVariable Long id) {
        log.debug("REST request to get TypeContactFacilityType : {}", id);
        TypeContactFacilityType typeContactFacilityType = typeContactFacilityTypeService.findOne(id);
        return Optional.ofNullable(typeContactFacilityType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-contact-facility-types/:id : delete the "id" typeContactFacilityType.
     *
     * @param id the id of the typeContactFacilityType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-contact-facility-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeContactFacilityType(@PathVariable Long id) {
        log.debug("REST request to delete TypeContactFacilityType : {}", id);
        typeContactFacilityTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeContactFacilityType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-contact-facility-types?query=:query : search for the typeContactFacilityType corresponding
     * to the query.
     *
     * @param query the query of the typeContactFacilityType search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-contact-facility-types")
    @Timed
    public List<TypeContactFacilityType> searchTypeContactFacilityTypes(@RequestParam String query) {
        log.debug("REST request to search TypeContactFacilityTypes for query {}", query);
        return typeContactFacilityTypeService.search(query);
    }


}
