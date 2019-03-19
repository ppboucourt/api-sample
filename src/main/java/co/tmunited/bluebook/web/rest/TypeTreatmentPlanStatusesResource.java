package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeTreatmentPlanStatuses;
import co.tmunited.bluebook.service.TypeTreatmentPlanStatusesService;
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
 * REST controller for managing TypeTreatmentPlanStatuses.
 */
@RestController
@RequestMapping("/api")
public class TypeTreatmentPlanStatusesResource {

    private final Logger log = LoggerFactory.getLogger(TypeTreatmentPlanStatusesResource.class);
        
    @Inject
    private TypeTreatmentPlanStatusesService typeTreatmentPlanStatusesService;

    /**
     * POST  /type-treatment-plan-statuses : Create a new typeTreatmentPlanStatuses.
     *
     * @param typeTreatmentPlanStatuses the typeTreatmentPlanStatuses to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeTreatmentPlanStatuses, or with status 400 (Bad Request) if the typeTreatmentPlanStatuses has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-treatment-plan-statuses")
    @Timed
    public ResponseEntity<TypeTreatmentPlanStatuses> createTypeTreatmentPlanStatuses(@Valid @RequestBody TypeTreatmentPlanStatuses typeTreatmentPlanStatuses) throws URISyntaxException {
        log.debug("REST request to save TypeTreatmentPlanStatuses : {}", typeTreatmentPlanStatuses);
        if (typeTreatmentPlanStatuses.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeTreatmentPlanStatuses", "idexists", "A new typeTreatmentPlanStatuses cannot already have an ID")).body(null);
        }
        TypeTreatmentPlanStatuses result = typeTreatmentPlanStatusesService.save(typeTreatmentPlanStatuses);
        return ResponseEntity.created(new URI("/api/type-treatment-plan-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeTreatmentPlanStatuses", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-treatment-plan-statuses : Updates an existing typeTreatmentPlanStatuses.
     *
     * @param typeTreatmentPlanStatuses the typeTreatmentPlanStatuses to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeTreatmentPlanStatuses,
     * or with status 400 (Bad Request) if the typeTreatmentPlanStatuses is not valid,
     * or with status 500 (Internal Server Error) if the typeTreatmentPlanStatuses couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-treatment-plan-statuses")
    @Timed
    public ResponseEntity<TypeTreatmentPlanStatuses> updateTypeTreatmentPlanStatuses(@Valid @RequestBody TypeTreatmentPlanStatuses typeTreatmentPlanStatuses) throws URISyntaxException {
        log.debug("REST request to update TypeTreatmentPlanStatuses : {}", typeTreatmentPlanStatuses);
        if (typeTreatmentPlanStatuses.getId() == null) {
            return createTypeTreatmentPlanStatuses(typeTreatmentPlanStatuses);
        }
        TypeTreatmentPlanStatuses result = typeTreatmentPlanStatusesService.save(typeTreatmentPlanStatuses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeTreatmentPlanStatuses", typeTreatmentPlanStatuses.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-treatment-plan-statuses : get all the typeTreatmentPlanStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeTreatmentPlanStatuses in body
     */
    @GetMapping("/type-treatment-plan-statuses")
    @Timed
    public List<TypeTreatmentPlanStatuses> getAllTypeTreatmentPlanStatuses() {
        log.debug("REST request to get all TypeTreatmentPlanStatuses");
        return typeTreatmentPlanStatusesService.findAll();
    }

    /**
     * GET  /type-treatment-plan-statuses/:id : get the "id" typeTreatmentPlanStatuses.
     *
     * @param id the id of the typeTreatmentPlanStatuses to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeTreatmentPlanStatuses, or with status 404 (Not Found)
     */
    @GetMapping("/type-treatment-plan-statuses/{id}")
    @Timed
    public ResponseEntity<TypeTreatmentPlanStatuses> getTypeTreatmentPlanStatuses(@PathVariable Long id) {
        log.debug("REST request to get TypeTreatmentPlanStatuses : {}", id);
        TypeTreatmentPlanStatuses typeTreatmentPlanStatuses = typeTreatmentPlanStatusesService.findOne(id);
        return Optional.ofNullable(typeTreatmentPlanStatuses)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-treatment-plan-statuses/:id : delete the "id" typeTreatmentPlanStatuses.
     *
     * @param id the id of the typeTreatmentPlanStatuses to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-treatment-plan-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeTreatmentPlanStatuses(@PathVariable Long id) {
        log.debug("REST request to delete TypeTreatmentPlanStatuses : {}", id);
        typeTreatmentPlanStatusesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeTreatmentPlanStatuses", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-treatment-plan-statuses?query=:query : search for the typeTreatmentPlanStatuses corresponding
     * to the query.
     *
     * @param query the query of the typeTreatmentPlanStatuses search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-treatment-plan-statuses")
    @Timed
    public List<TypeTreatmentPlanStatuses> searchTypeTreatmentPlanStatuses(@RequestParam String query) {
        log.debug("REST request to search TypeTreatmentPlanStatuses for query {}", query);
        return typeTreatmentPlanStatusesService.search(query);
    }


}
