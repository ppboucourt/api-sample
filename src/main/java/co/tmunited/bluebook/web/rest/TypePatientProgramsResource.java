package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePatientPrograms;
import co.tmunited.bluebook.service.TypePatientProgramsService;
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
 * REST controller for managing TypePatientPrograms.
 */
@RestController
@RequestMapping("/api")
public class TypePatientProgramsResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientProgramsResource.class);
        
    @Inject
    private TypePatientProgramsService typePatientProgramsService;

    /**
     * POST  /type-patient-programs : Create a new typePatientPrograms.
     *
     * @param typePatientPrograms the typePatientPrograms to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePatientPrograms, or with status 400 (Bad Request) if the typePatientPrograms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-patient-programs")
    @Timed
    public ResponseEntity<TypePatientPrograms> createTypePatientPrograms(@RequestBody TypePatientPrograms typePatientPrograms) throws URISyntaxException {
        log.debug("REST request to save TypePatientPrograms : {}", typePatientPrograms);
        if (typePatientPrograms.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePatientPrograms", "idexists", "A new typePatientPrograms cannot already have an ID")).body(null);
        }
        TypePatientPrograms result = typePatientProgramsService.save(typePatientPrograms);
        return ResponseEntity.created(new URI("/api/type-patient-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePatientPrograms", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-patient-programs : Updates an existing typePatientPrograms.
     *
     * @param typePatientPrograms the typePatientPrograms to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePatientPrograms,
     * or with status 400 (Bad Request) if the typePatientPrograms is not valid,
     * or with status 500 (Internal Server Error) if the typePatientPrograms couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-patient-programs")
    @Timed
    public ResponseEntity<TypePatientPrograms> updateTypePatientPrograms(@RequestBody TypePatientPrograms typePatientPrograms) throws URISyntaxException {
        log.debug("REST request to update TypePatientPrograms : {}", typePatientPrograms);
        if (typePatientPrograms.getId() == null) {
            return createTypePatientPrograms(typePatientPrograms);
        }
        TypePatientPrograms result = typePatientProgramsService.save(typePatientPrograms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePatientPrograms", typePatientPrograms.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-patient-programs : get all the typePatientPrograms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientPrograms in body
     */
    @GetMapping("/type-patient-programs")
    @Timed
    public List<TypePatientPrograms> getAllTypePatientPrograms() {
        log.debug("REST request to get all TypePatientPrograms");
        return typePatientProgramsService.findAll();
    }

    /**
     * GET  /type-patient-programs/:id : get the "id" typePatientPrograms.
     *
     * @param id the id of the typePatientPrograms to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePatientPrograms, or with status 404 (Not Found)
     */
    @GetMapping("/type-patient-programs/{id}")
    @Timed
    public ResponseEntity<TypePatientPrograms> getTypePatientPrograms(@PathVariable Long id) {
        log.debug("REST request to get TypePatientPrograms : {}", id);
        TypePatientPrograms typePatientPrograms = typePatientProgramsService.findOne(id);
        return Optional.ofNullable(typePatientPrograms)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-patient-programs/:id : delete the "id" typePatientPrograms.
     *
     * @param id the id of the typePatientPrograms to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-patient-programs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePatientPrograms(@PathVariable Long id) {
        log.debug("REST request to delete TypePatientPrograms : {}", id);
        typePatientProgramsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePatientPrograms", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-patient-programs?query=:query : search for the typePatientPrograms corresponding
     * to the query.
     *
     * @param query the query of the typePatientPrograms search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-patient-programs")
    @Timed
    public List<TypePatientPrograms> searchTypePatientPrograms(@RequestParam String query) {
        log.debug("REST request to search TypePatientPrograms for query {}", query);
        return typePatientProgramsService.search(query);
    }


}
