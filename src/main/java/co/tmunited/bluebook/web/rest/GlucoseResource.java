package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Glucose;
import co.tmunited.bluebook.service.GlucoseService;
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
 * REST controller for managing Glucose.
 */
@RestController
@RequestMapping("/api")
public class GlucoseResource {

    private final Logger log = LoggerFactory.getLogger(GlucoseResource.class);

    @Inject
    private GlucoseService glucoseService;

    /**
     * POST  /glucoses : Create a new glucose.
     *
     * @param glucose the glucose to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glucose, or with status 400 (Bad Request) if the glucose has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glucoses")
    @Timed
    public ResponseEntity<Glucose> createGlucose(@RequestBody Glucose glucose) throws URISyntaxException {
        log.debug("REST request to save Glucose : {}", glucose);
        if (glucose.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("glucose", "idexists", "A new glucose cannot already have an ID")).body(null);
        }
        Glucose result = glucoseService.save(glucose);
        return ResponseEntity.created(new URI("/api/glucoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("glucose", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glucoses : Updates an existing glucose.
     *
     * @param glucose the glucose to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glucose,
     * or with status 400 (Bad Request) if the glucose is not valid,
     * or with status 500 (Internal Server Error) if the glucose couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glucoses")
    @Timed
    public ResponseEntity<Glucose> updateGlucose(@RequestBody Glucose glucose) throws URISyntaxException {
        log.debug("REST request to update Glucose : {}", glucose);
        if (glucose.getId() == null) {
            return createGlucose(glucose);
        }
        Glucose result = glucoseService.save(glucose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("glucose", glucose.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glucoses : get all the glucoses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of glucoses in body
     */
    @GetMapping("/glucoses")
    @Timed
    public List<Glucose> getAllGlucoses() {
        log.debug("REST request to get all Glucoses");
        return glucoseService.findAll();
    }

    /**
     * GET  /glucoses/:id : get the "id" glucose.
     *
     * @param id the id of the glucose to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glucose, or with status 404 (Not Found)
     */
    @GetMapping("/glucoses/{id}")
    @Timed
    public ResponseEntity<Glucose> getGlucose(@PathVariable Long id) {
        log.debug("REST request to get Glucose : {}", id);
        Glucose glucose = glucoseService.findOne(id);
        return Optional.ofNullable(glucose)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /glucoses/:id : delete the "id" glucose.
     *
     * @param id the id of the glucose to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glucoses/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlucose(@PathVariable Long id) {
        log.debug("REST request to delete Glucose : {}", id);
        glucoseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("glucose", id.toString())).build();
    }

    /**
     * SEARCH  /_search/glucoses?query=:query : search for the glucose corresponding
     * to the query.
     *
     * @param query the query of the glucose search
     * @return the result of the search
     */
    @GetMapping("/_search/glucoses")
    @Timed
    public List<Glucose> searchGlucoses(@RequestParam String query) {
        log.debug("REST request to search Glucoses for query {}", query);
        return glucoseService.search(query);
    }

    /**
     * SEARCH  /_search/vitals?query=:query : search for the vitals corresponding
     * to the query.
     *
     * @param id the query of the vitals search
     * @return the result of the search
     */
    @GetMapping("/glucose/chart/{id}")
    @Timed
    public List<Glucose> searchGlucoseByChart(@PathVariable Long id) {
        log.debug("REST request to search Glucose by chart {}", id);
        return glucoseService.findByChart(id);
    }


}
