package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Allergies;
import co.tmunited.bluebook.service.AllergiesService;
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
 * REST controller for managing Allergies.
 */
@RestController
@RequestMapping("/api")
public class AllergiesResource {

    private final Logger log = LoggerFactory.getLogger(AllergiesResource.class);
        
    @Inject
    private AllergiesService allergiesService;

    /**
     * POST  /allergies : Create a new allergies.
     *
     * @param allergies the allergies to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allergies, or with status 400 (Bad Request) if the allergies has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allergies")
    @Timed
    public ResponseEntity<Allergies> createAllergies(@RequestBody Allergies allergies) throws URISyntaxException {
        log.debug("REST request to save Allergies : {}", allergies);
        if (allergies.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("allergies", "idexists", "A new allergies cannot already have an ID")).body(null);
        }
        Allergies result = allergiesService.save(allergies);
        return ResponseEntity.created(new URI("/api/allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("allergies", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allergies : Updates an existing allergies.
     *
     * @param allergies the allergies to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allergies,
     * or with status 400 (Bad Request) if the allergies is not valid,
     * or with status 500 (Internal Server Error) if the allergies couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allergies")
    @Timed
    public ResponseEntity<Allergies> updateAllergies(@RequestBody Allergies allergies) throws URISyntaxException {
        log.debug("REST request to update Allergies : {}", allergies);
        if (allergies.getId() == null) {
            return createAllergies(allergies);
        }
        Allergies result = allergiesService.save(allergies);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("allergies", allergies.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allergies : get all the allergies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of allergies in body
     */
    @GetMapping("/allergies")
    @Timed
    public List<Allergies> getAllAllergies() {
        log.debug("REST request to get all Allergies");
        return allergiesService.findAll();
    }

    /**
     * GET  /allergies/:id : get the "id" allergies.
     *
     * @param id the id of the allergies to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allergies, or with status 404 (Not Found)
     */
    @GetMapping("/allergies/{id}")
    @Timed
    public ResponseEntity<Allergies> getAllergies(@PathVariable Long id) {
        log.debug("REST request to get Allergies : {}", id);
        Allergies allergies = allergiesService.findOne(id);
        return Optional.ofNullable(allergies)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /allergies/:id : delete the "id" allergies.
     *
     * @param id the id of the allergies to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allergies/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllergies(@PathVariable Long id) {
        log.debug("REST request to delete Allergies : {}", id);
        allergiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("allergies", id.toString())).build();
    }

    /**
     * SEARCH  /_search/allergies?query=:query : search for the allergies corresponding
     * to the query.
     *
     * @param query the query of the allergies search 
     * @return the result of the search
     */
    @GetMapping("/_search/allergies")
    @Timed
    public List<Allergies> searchAllergies(@RequestParam String query) {
        log.debug("REST request to search Allergies for query {}", query);
        return allergiesService.search(query);
    }


}
