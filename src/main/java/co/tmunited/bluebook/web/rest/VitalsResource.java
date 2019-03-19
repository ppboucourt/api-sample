package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Vitals;
import co.tmunited.bluebook.service.VitalsService;
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
 * REST controller for managing Vitals.
 */
@RestController
@RequestMapping("/api")
public class VitalsResource {

    private final Logger log = LoggerFactory.getLogger(VitalsResource.class);

    @Inject
    private VitalsService vitalsService;

    /**
     * POST  /vitals : Create a new vitals.
     *
     * @param vitals the vitals to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vitals, or with status 400 (Bad Request) if the vitals has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vitals")
    @Timed
    public ResponseEntity<Vitals> createVitals(@RequestBody Vitals vitals) throws URISyntaxException {
        log.debug("REST request to save Vitals : {}", vitals);
        if (vitals.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vitals", "idexists", "A new vitals cannot already have an ID")).body(null);
        }
        Vitals result = vitalsService.save(vitals);
        return ResponseEntity.created(new URI("/api/vitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vitals", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vitals : Updates an existing vitals.
     *
     * @param vitals the vitals to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vitals,
     * or with status 400 (Bad Request) if the vitals is not valid,
     * or with status 500 (Internal Server Error) if the vitals couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vitals")
    @Timed
    public ResponseEntity<Vitals> updateVitals(@RequestBody Vitals vitals) throws URISyntaxException {
        log.debug("REST request to update Vitals : {}", vitals);
        if (vitals.getId() == null) {
            return createVitals(vitals);
        }
        Vitals result = vitalsService.save(vitals);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vitals", vitals.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vitals : get all the vitals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vitals in body
     */
    @GetMapping("/vitals")
    @Timed
    public List<Vitals> getAllVitals() {
        log.debug("REST request to get all Vitals");
        return vitalsService.findAll();
    }

    /**
     * GET  /vitals/:id : get the "id" vitals.
     *
     * @param id the id of the vitals to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vitals, or with status 404 (Not Found)
     */
    @GetMapping("/vitals/{id}")
    @Timed
    public ResponseEntity<Vitals> getVitals(@PathVariable Long id) {
        log.debug("REST request to get Vitals : {}", id);
        Vitals vitals = vitalsService.findOne(id);
        return Optional.ofNullable(vitals)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vitals/:id : delete the "id" vitals.
     *
     * @param id the id of the vitals to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vitals/{id}")
    @Timed
    public ResponseEntity<Void> deleteVitals(@PathVariable Long id) {
        log.debug("REST request to delete Vitals : {}", id);
        vitalsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vitals", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vitals?query=:query : search for the vitals corresponding
     * to the query.
     *
     * @param query the query of the vitals search
     * @return the result of the search
     */
    @GetMapping("/_search/vitals")
    @Timed
    public List<Vitals> searchVitals(@RequestParam String query) {
        log.debug("REST request to search Vitals for query {}", query);
        return vitalsService.search(query);
    }

    /**
     * SEARCH  /_search/vitals?query=:query : search for the vitals corresponding
     * to the query.
     *
     * @param id the query of the vitals search
     * @return the result of the search
     */
    @GetMapping("/vitals/chart/{id}")
    @Timed
    public List<Vitals> searchVitalsByChart(@PathVariable Long id) {
        log.debug("REST request to search Vitals by chart {}", id);
        return vitalsService.findByChart(id);
    }


}
