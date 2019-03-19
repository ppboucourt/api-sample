package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeMedicationRoutes;
import co.tmunited.bluebook.service.TypeMedicationRoutesService;
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
 * REST controller for managing TypeMedicationRoutes.
 */
@RestController
@RequestMapping("/api")
public class TypeMedicationRoutesResource {

    private final Logger log = LoggerFactory.getLogger(TypeMedicationRoutesResource.class);
        
    @Inject
    private TypeMedicationRoutesService typeMedicationRoutesService;

    /**
     * POST  /type-medication-routes : Create a new typeMedicationRoutes.
     *
     * @param typeMedicationRoutes the typeMedicationRoutes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeMedicationRoutes, or with status 400 (Bad Request) if the typeMedicationRoutes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-medication-routes")
    @Timed
    public ResponseEntity<TypeMedicationRoutes> createTypeMedicationRoutes(@RequestBody TypeMedicationRoutes typeMedicationRoutes) throws URISyntaxException {
        log.debug("REST request to save TypeMedicationRoutes : {}", typeMedicationRoutes);
        if (typeMedicationRoutes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeMedicationRoutes", "idexists", "A new typeMedicationRoutes cannot already have an ID")).body(null);
        }
        TypeMedicationRoutes result = typeMedicationRoutesService.save(typeMedicationRoutes);
        return ResponseEntity.created(new URI("/api/type-medication-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeMedicationRoutes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-medication-routes : Updates an existing typeMedicationRoutes.
     *
     * @param typeMedicationRoutes the typeMedicationRoutes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeMedicationRoutes,
     * or with status 400 (Bad Request) if the typeMedicationRoutes is not valid,
     * or with status 500 (Internal Server Error) if the typeMedicationRoutes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-medication-routes")
    @Timed
    public ResponseEntity<TypeMedicationRoutes> updateTypeMedicationRoutes(@RequestBody TypeMedicationRoutes typeMedicationRoutes) throws URISyntaxException {
        log.debug("REST request to update TypeMedicationRoutes : {}", typeMedicationRoutes);
        if (typeMedicationRoutes.getId() == null) {
            return createTypeMedicationRoutes(typeMedicationRoutes);
        }
        TypeMedicationRoutes result = typeMedicationRoutesService.save(typeMedicationRoutes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeMedicationRoutes", typeMedicationRoutes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-medication-routes : get all the typeMedicationRoutes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeMedicationRoutes in body
     */
    @GetMapping("/type-medication-routes")
    @Timed
    public List<TypeMedicationRoutes> getAllTypeMedicationRoutes() {
        log.debug("REST request to get all TypeMedicationRoutes");
        return typeMedicationRoutesService.findAll();
    }

    /**
     * GET  /type-medication-routes/:id : get the "id" typeMedicationRoutes.
     *
     * @param id the id of the typeMedicationRoutes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeMedicationRoutes, or with status 404 (Not Found)
     */
    @GetMapping("/type-medication-routes/{id}")
    @Timed
    public ResponseEntity<TypeMedicationRoutes> getTypeMedicationRoutes(@PathVariable Long id) {
        log.debug("REST request to get TypeMedicationRoutes : {}", id);
        TypeMedicationRoutes typeMedicationRoutes = typeMedicationRoutesService.findOne(id);
        return Optional.ofNullable(typeMedicationRoutes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-medication-routes/:id : delete the "id" typeMedicationRoutes.
     *
     * @param id the id of the typeMedicationRoutes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-medication-routes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeMedicationRoutes(@PathVariable Long id) {
        log.debug("REST request to delete TypeMedicationRoutes : {}", id);
        typeMedicationRoutesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeMedicationRoutes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-medication-routes?query=:query : search for the typeMedicationRoutes corresponding
     * to the query.
     *
     * @param query the query of the typeMedicationRoutes search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-medication-routes")
    @Timed
    public List<TypeMedicationRoutes> searchTypeMedicationRoutes(@RequestParam String query) {
        log.debug("REST request to search TypeMedicationRoutes for query {}", query);
        return typeMedicationRoutesService.search(query);
    }


}
