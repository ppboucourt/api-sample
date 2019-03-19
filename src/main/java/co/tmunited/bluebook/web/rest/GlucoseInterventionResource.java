package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.GlucoseIntervention;
import co.tmunited.bluebook.service.GlucoseInterventionService;
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
 * REST controller for managing GlucoseIntervention.
 */
@RestController
@RequestMapping("/api")
public class GlucoseInterventionResource {

    private final Logger log = LoggerFactory.getLogger(GlucoseInterventionResource.class);
        
    @Inject
    private GlucoseInterventionService glucoseInterventionService;

    /**
     * POST  /glucose-interventions : Create a new glucoseIntervention.
     *
     * @param glucoseIntervention the glucoseIntervention to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glucoseIntervention, or with status 400 (Bad Request) if the glucoseIntervention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glucose-interventions")
    @Timed
    public ResponseEntity<GlucoseIntervention> createGlucoseIntervention(@Valid @RequestBody GlucoseIntervention glucoseIntervention) throws URISyntaxException {
        log.debug("REST request to save GlucoseIntervention : {}", glucoseIntervention);
        if (glucoseIntervention.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("glucoseIntervention", "idexists", "A new glucoseIntervention cannot already have an ID")).body(null);
        }
        GlucoseIntervention result = glucoseInterventionService.save(glucoseIntervention);
        return ResponseEntity.created(new URI("/api/glucose-interventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("glucoseIntervention", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glucose-interventions : Updates an existing glucoseIntervention.
     *
     * @param glucoseIntervention the glucoseIntervention to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glucoseIntervention,
     * or with status 400 (Bad Request) if the glucoseIntervention is not valid,
     * or with status 500 (Internal Server Error) if the glucoseIntervention couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glucose-interventions")
    @Timed
    public ResponseEntity<GlucoseIntervention> updateGlucoseIntervention(@Valid @RequestBody GlucoseIntervention glucoseIntervention) throws URISyntaxException {
        log.debug("REST request to update GlucoseIntervention : {}", glucoseIntervention);
        if (glucoseIntervention.getId() == null) {
            return createGlucoseIntervention(glucoseIntervention);
        }
        GlucoseIntervention result = glucoseInterventionService.save(glucoseIntervention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("glucoseIntervention", glucoseIntervention.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glucose-interventions : get all the glucoseInterventions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of glucoseInterventions in body
     */
    @GetMapping("/glucose-interventions")
    @Timed
    public List<GlucoseIntervention> getAllGlucoseInterventions() {
        log.debug("REST request to get all GlucoseInterventions");
        return glucoseInterventionService.findAll();
    }

    /**
     * GET  /glucose-interventions/:id : get the "id" glucoseIntervention.
     *
     * @param id the id of the glucoseIntervention to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glucoseIntervention, or with status 404 (Not Found)
     */
    @GetMapping("/glucose-interventions/{id}")
    @Timed
    public ResponseEntity<GlucoseIntervention> getGlucoseIntervention(@PathVariable Long id) {
        log.debug("REST request to get GlucoseIntervention : {}", id);
        GlucoseIntervention glucoseIntervention = glucoseInterventionService.findOne(id);
        return Optional.ofNullable(glucoseIntervention)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /glucose-interventions/:id : delete the "id" glucoseIntervention.
     *
     * @param id the id of the glucoseIntervention to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glucose-interventions/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlucoseIntervention(@PathVariable Long id) {
        log.debug("REST request to delete GlucoseIntervention : {}", id);
        glucoseInterventionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("glucoseIntervention", id.toString())).build();
    }

    /**
     * SEARCH  /_search/glucose-interventions?query=:query : search for the glucoseIntervention corresponding
     * to the query.
     *
     * @param query the query of the glucoseIntervention search 
     * @return the result of the search
     */
    @GetMapping("/_search/glucose-interventions")
    @Timed
    public List<GlucoseIntervention> searchGlucoseInterventions(@RequestParam String query) {
        log.debug("REST request to search GlucoseInterventions for query {}", query);
        return glucoseInterventionService.search(query);
    }


}
