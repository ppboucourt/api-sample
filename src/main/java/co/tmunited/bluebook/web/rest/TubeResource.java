package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.Tube;
import co.tmunited.bluebook.service.TubeService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tube.
 */
@RestController
@RequestMapping("/api")
public class TubeResource {

    private final Logger log = LoggerFactory.getLogger(TubeResource.class);

    @Inject
    private TubeService tubeService;

    /**
     * POST  /tubes : Create a new tube.
     *
     * @param tube the tube to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tube, or with status 400 (Bad Request) if the tube has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tubes")
    @Timed
    public ResponseEntity<Tube> createTube(@Valid @RequestBody Tube tube) throws URISyntaxException {
        log.debug("REST request to save Tube : {}", tube);
        if (tube.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tube", "idexists", "A new tube cannot already have an ID")).body(null);
        }
        Tube result = tubeService.save(tube);
        return ResponseEntity.created(new URI("/api/tubes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tube", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tubes : Updates an existing tube.
     *
     * @param tube the tube to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tube,
     * or with status 400 (Bad Request) if the tube is not valid,
     * or with status 500 (Internal Server Error) if the tube couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tubes")
    @Timed
    public ResponseEntity<Tube> updateTube(@Valid @RequestBody Tube tube) throws URISyntaxException {
        log.debug("REST request to update Tube : {}", tube);
        if (tube.getId() == null) {
            return createTube(tube);
        }
        Tube result = tubeService.save(tube);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tube", tube.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tubes : get all the tubes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tubes in body
     */
    @GetMapping("/tubes")
    @Timed
    public List<Tube> getAllTubes() {
        log.debug("REST request to get all Tubes");
        return tubeService.findAll();
    }

    /**
     * GET  /tubes/:id : get the "id" tube.
     *
     * @param id the id of the tube to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tube, or with status 404 (Not Found)
     */
    @GetMapping("/tubes/{id}")
    @Timed
    public ResponseEntity<Tube> getTube(@PathVariable Long id) {
        log.debug("REST request to get Tube : {}", id);
        Tube tube = tubeService.findOne(id);
        return Optional.ofNullable(tube)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tubes/:id : delete the "id" tube.
     *
     * @param id the id of the tube to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tubes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTube(@PathVariable Long id) {
        log.debug("REST request to delete Tube : {}", id);
        tubeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tube", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tubes?query=:query : search for the tube corresponding
     * to the query.
     *
     * @param query the query of the tube search
     * @return the result of the search
     */
    @GetMapping("/_search/tubes")
    @Timed
    public List<Tube> searchTubes(@RequestParam String query) {
        log.debug("REST request to search Tubes for query {}", query);
        return tubeService.search(query);
    }


}
