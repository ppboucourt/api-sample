package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePatientContactsRelationship;
import co.tmunited.bluebook.service.TypePatientContactsRelationshipService;
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
 * REST controller for managing TypePatientContactsRelationship.
 */
@RestController
@RequestMapping("/api")
public class TypePatientContactsRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientContactsRelationshipResource.class);
        
    @Inject
    private TypePatientContactsRelationshipService typePatientContactsRelationshipService;

    /**
     * POST  /type-patient-contacts-relationships : Create a new typePatientContactsRelationship.
     *
     * @param typePatientContactsRelationship the typePatientContactsRelationship to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePatientContactsRelationship, or with status 400 (Bad Request) if the typePatientContactsRelationship has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-patient-contacts-relationships")
    @Timed
    public ResponseEntity<TypePatientContactsRelationship> createTypePatientContactsRelationship(@RequestBody TypePatientContactsRelationship typePatientContactsRelationship) throws URISyntaxException {
        log.debug("REST request to save TypePatientContactsRelationship : {}", typePatientContactsRelationship);
        if (typePatientContactsRelationship.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePatientContactsRelationship", "idexists", "A new typePatientContactsRelationship cannot already have an ID")).body(null);
        }
        TypePatientContactsRelationship result = typePatientContactsRelationshipService.save(typePatientContactsRelationship);
        return ResponseEntity.created(new URI("/api/type-patient-contacts-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePatientContactsRelationship", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-patient-contacts-relationships : Updates an existing typePatientContactsRelationship.
     *
     * @param typePatientContactsRelationship the typePatientContactsRelationship to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePatientContactsRelationship,
     * or with status 400 (Bad Request) if the typePatientContactsRelationship is not valid,
     * or with status 500 (Internal Server Error) if the typePatientContactsRelationship couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-patient-contacts-relationships")
    @Timed
    public ResponseEntity<TypePatientContactsRelationship> updateTypePatientContactsRelationship(@RequestBody TypePatientContactsRelationship typePatientContactsRelationship) throws URISyntaxException {
        log.debug("REST request to update TypePatientContactsRelationship : {}", typePatientContactsRelationship);
        if (typePatientContactsRelationship.getId() == null) {
            return createTypePatientContactsRelationship(typePatientContactsRelationship);
        }
        TypePatientContactsRelationship result = typePatientContactsRelationshipService.save(typePatientContactsRelationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePatientContactsRelationship", typePatientContactsRelationship.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-patient-contacts-relationships : get all the typePatientContactsRelationships.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientContactsRelationships in body
     */
    @GetMapping("/type-patient-contacts-relationships")
    @Timed
    public List<TypePatientContactsRelationship> getAllTypePatientContactsRelationships() {
        log.debug("REST request to get all TypePatientContactsRelationships");
        return typePatientContactsRelationshipService.findAll();
    }

    /**
     * GET  /type-patient-contacts-relationships/:id : get the "id" typePatientContactsRelationship.
     *
     * @param id the id of the typePatientContactsRelationship to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePatientContactsRelationship, or with status 404 (Not Found)
     */
    @GetMapping("/type-patient-contacts-relationships/{id}")
    @Timed
    public ResponseEntity<TypePatientContactsRelationship> getTypePatientContactsRelationship(@PathVariable Long id) {
        log.debug("REST request to get TypePatientContactsRelationship : {}", id);
        TypePatientContactsRelationship typePatientContactsRelationship = typePatientContactsRelationshipService.findOne(id);
        return Optional.ofNullable(typePatientContactsRelationship)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-patient-contacts-relationships/:id : delete the "id" typePatientContactsRelationship.
     *
     * @param id the id of the typePatientContactsRelationship to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-patient-contacts-relationships/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePatientContactsRelationship(@PathVariable Long id) {
        log.debug("REST request to delete TypePatientContactsRelationship : {}", id);
        typePatientContactsRelationshipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePatientContactsRelationship", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-patient-contacts-relationships?query=:query : search for the typePatientContactsRelationship corresponding
     * to the query.
     *
     * @param query the query of the typePatientContactsRelationship search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-patient-contacts-relationships")
    @Timed
    public List<TypePatientContactsRelationship> searchTypePatientContactsRelationships(@RequestParam String query) {
        log.debug("REST request to search TypePatientContactsRelationships for query {}", query);
        return typePatientContactsRelationshipService.search(query);
    }


}
