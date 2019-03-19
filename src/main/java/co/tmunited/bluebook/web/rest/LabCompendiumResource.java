package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.LabCompendium;
import co.tmunited.bluebook.service.LabCompendiumService;
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
 * REST controller for managing LabCompendium.
 */
@RestController
@RequestMapping("/api")
public class LabCompendiumResource {

    private final Logger log = LoggerFactory.getLogger(LabCompendiumResource.class);
        
    @Inject
    private LabCompendiumService labCompendiumService;

    /**
     * POST  /lab-compendiums : Create a new labCompendium.
     *
     * @param labCompendium the labCompendium to create
     * @return the ResponseEntity with status 201 (Created) and with body the new labCompendium, or with status 400 (Bad Request) if the labCompendium has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lab-compendiums")
    @Timed
    public ResponseEntity<LabCompendium> createLabCompendium(@RequestBody LabCompendium labCompendium) throws URISyntaxException {
        log.debug("REST request to save LabCompendium : {}", labCompendium);
        if (labCompendium.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("labCompendium", "idexists", "A new labCompendium cannot already have an ID")).body(null);
        }
        LabCompendium result = labCompendiumService.save(labCompendium);
        return ResponseEntity.created(new URI("/api/lab-compendiums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("labCompendium", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lab-compendiums : Updates an existing labCompendium.
     *
     * @param labCompendium the labCompendium to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated labCompendium,
     * or with status 400 (Bad Request) if the labCompendium is not valid,
     * or with status 500 (Internal Server Error) if the labCompendium couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lab-compendiums")
    @Timed
    public ResponseEntity<LabCompendium> updateLabCompendium(@RequestBody LabCompendium labCompendium) throws URISyntaxException {
        log.debug("REST request to update LabCompendium : {}", labCompendium);
        if (labCompendium.getId() == null) {
            return createLabCompendium(labCompendium);
        }
        LabCompendium result = labCompendiumService.save(labCompendium);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("labCompendium", labCompendium.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lab-compendiums : get all the labCompendiums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labCompendiums in body
     */
    @GetMapping("/lab-compendiums")
    @Timed
    public List<LabCompendium> getAllLabCompendiums() {
        log.debug("REST request to get all LabCompendiums");
        return labCompendiumService.findAll();
    }

    /**
     * GET  /lab-compendiums/:id : get the "id" labCompendium.
     *
     * @param id the id of the labCompendium to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the labCompendium, or with status 404 (Not Found)
     */
    @GetMapping("/lab-compendiums/{id}")
    @Timed
    public ResponseEntity<LabCompendium> getLabCompendium(@PathVariable Long id) {
        log.debug("REST request to get LabCompendium : {}", id);
        LabCompendium labCompendium = labCompendiumService.findOne(id);
        return Optional.ofNullable(labCompendium)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lab-compendiums/:id : delete the "id" labCompendium.
     *
     * @param id the id of the labCompendium to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lab-compendiums/{id}")
    @Timed
    public ResponseEntity<Void> deleteLabCompendium(@PathVariable Long id) {
        log.debug("REST request to delete LabCompendium : {}", id);
        labCompendiumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("labCompendium", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lab-compendiums?query=:query : search for the labCompendium corresponding
     * to the query.
     *
     * @param query the query of the labCompendium search 
     * @return the result of the search
     */
    @GetMapping("/_search/lab-compendiums")
    @Timed
    public List<LabCompendium> searchLabCompendiums(@RequestParam String query) {
        log.debug("REST request to search LabCompendiums for query {}", query);
        return labCompendiumService.search(query);
    }


}
