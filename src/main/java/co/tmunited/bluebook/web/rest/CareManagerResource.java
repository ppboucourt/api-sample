package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.CareManager;
import co.tmunited.bluebook.service.CareManagerService;
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
 * REST controller for managing CareManager.
 */
@RestController
@RequestMapping("/api")
public class CareManagerResource {

    private final Logger log = LoggerFactory.getLogger(CareManagerResource.class);
        
    @Inject
    private CareManagerService careManagerService;

    /**
     * POST  /care-managers : Create a new careManager.
     *
     * @param careManager the careManager to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careManager, or with status 400 (Bad Request) if the careManager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/care-managers")
    @Timed
    public ResponseEntity<CareManager> createCareManager(@Valid @RequestBody CareManager careManager) throws URISyntaxException {
        log.debug("REST request to save CareManager : {}", careManager);
        if (careManager.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careManager", "idexists", "A new careManager cannot already have an ID")).body(null);
        }
        CareManager result = careManagerService.save(careManager);
        return ResponseEntity.created(new URI("/api/care-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careManager", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /care-managers : Updates an existing careManager.
     *
     * @param careManager the careManager to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careManager,
     * or with status 400 (Bad Request) if the careManager is not valid,
     * or with status 500 (Internal Server Error) if the careManager couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/care-managers")
    @Timed
    public ResponseEntity<CareManager> updateCareManager(@Valid @RequestBody CareManager careManager) throws URISyntaxException {
        log.debug("REST request to update CareManager : {}", careManager);
        if (careManager.getId() == null) {
            return createCareManager(careManager);
        }
        CareManager result = careManagerService.save(careManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careManager", careManager.getId().toString()))
            .body(result);
    }

    /**
     * GET  /care-managers : get all the careManagers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careManagers in body
     */
    @GetMapping("/care-managers")
    @Timed
    public List<CareManager> getAllCareManagers() {
        log.debug("REST request to get all CareManagers");
        return careManagerService.findAll();
    }

    /**
     * GET  /care-managers/:id : get the "id" careManager.
     *
     * @param id the id of the careManager to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careManager, or with status 404 (Not Found)
     */
    @GetMapping("/care-managers/{id}")
    @Timed
    public ResponseEntity<CareManager> getCareManager(@PathVariable Long id) {
        log.debug("REST request to get CareManager : {}", id);
        CareManager careManager = careManagerService.findOne(id);
        return Optional.ofNullable(careManager)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /care-managers/:id : delete the "id" careManager.
     *
     * @param id the id of the careManager to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/care-managers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareManager(@PathVariable Long id) {
        log.debug("REST request to delete CareManager : {}", id);
        careManagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careManager", id.toString())).build();
    }

    /**
     * SEARCH  /_search/care-managers?query=:query : search for the careManager corresponding
     * to the query.
     *
     * @param query the query of the careManager search 
     * @return the result of the search
     */
    @GetMapping("/_search/care-managers")
    @Timed
    public List<CareManager> searchCareManagers(@RequestParam String query) {
        log.debug("REST request to search CareManagers for query {}", query);
        return careManagerService.search(query);
    }


}
