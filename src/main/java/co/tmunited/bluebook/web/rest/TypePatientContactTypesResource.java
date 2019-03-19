package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePatientContactTypes;
import co.tmunited.bluebook.service.TypePatientContactTypesService;
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
 * REST controller for managing TypePatientContactTypes.
 */
@RestController
@RequestMapping("/api")
public class TypePatientContactTypesResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientContactTypesResource.class);
        
    @Inject
    private TypePatientContactTypesService typePatientContactTypesService;

    /**
     * POST  /type-patient-contact-types : Create a new typePatientContactTypes.
     *
     * @param typePatientContactTypes the typePatientContactTypes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePatientContactTypes, or with status 400 (Bad Request) if the typePatientContactTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-patient-contact-types")
    @Timed
    public ResponseEntity<TypePatientContactTypes> createTypePatientContactTypes(@RequestBody TypePatientContactTypes typePatientContactTypes) throws URISyntaxException {
        log.debug("REST request to save TypePatientContactTypes : {}", typePatientContactTypes);
        if (typePatientContactTypes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePatientContactTypes", "idexists", "A new typePatientContactTypes cannot already have an ID")).body(null);
        }
        TypePatientContactTypes result = typePatientContactTypesService.save(typePatientContactTypes);
        return ResponseEntity.created(new URI("/api/type-patient-contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePatientContactTypes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-patient-contact-types : Updates an existing typePatientContactTypes.
     *
     * @param typePatientContactTypes the typePatientContactTypes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePatientContactTypes,
     * or with status 400 (Bad Request) if the typePatientContactTypes is not valid,
     * or with status 500 (Internal Server Error) if the typePatientContactTypes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-patient-contact-types")
    @Timed
    public ResponseEntity<TypePatientContactTypes> updateTypePatientContactTypes(@RequestBody TypePatientContactTypes typePatientContactTypes) throws URISyntaxException {
        log.debug("REST request to update TypePatientContactTypes : {}", typePatientContactTypes);
        if (typePatientContactTypes.getId() == null) {
            return createTypePatientContactTypes(typePatientContactTypes);
        }
        TypePatientContactTypes result = typePatientContactTypesService.save(typePatientContactTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePatientContactTypes", typePatientContactTypes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-patient-contact-types : get all the typePatientContactTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientContactTypes in body
     */
    @GetMapping("/type-patient-contact-types")
    @Timed
    public List<TypePatientContactTypes> getAllTypePatientContactTypes() {
        log.debug("REST request to get all TypePatientContactTypes");
        return typePatientContactTypesService.findAll();
    }

    /**
     * GET  /type-patient-contact-types/:id : get the "id" typePatientContactTypes.
     *
     * @param id the id of the typePatientContactTypes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePatientContactTypes, or with status 404 (Not Found)
     */
    @GetMapping("/type-patient-contact-types/{id}")
    @Timed
    public ResponseEntity<TypePatientContactTypes> getTypePatientContactTypes(@PathVariable Long id) {
        log.debug("REST request to get TypePatientContactTypes : {}", id);
        TypePatientContactTypes typePatientContactTypes = typePatientContactTypesService.findOne(id);
        return Optional.ofNullable(typePatientContactTypes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-patient-contact-types/:id : delete the "id" typePatientContactTypes.
     *
     * @param id the id of the typePatientContactTypes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-patient-contact-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePatientContactTypes(@PathVariable Long id) {
        log.debug("REST request to delete TypePatientContactTypes : {}", id);
        typePatientContactTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePatientContactTypes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-patient-contact-types?query=:query : search for the typePatientContactTypes corresponding
     * to the query.
     *
     * @param query the query of the typePatientContactTypes search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-patient-contact-types")
    @Timed
    public List<TypePatientContactTypes> searchTypePatientContactTypes(@RequestParam String query) {
        log.debug("REST request to search TypePatientContactTypes for query {}", query);
        return typePatientContactTypesService.search(query);
    }


}
