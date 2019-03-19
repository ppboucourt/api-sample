package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Laboratories;
import co.tmunited.bluebook.service.LaboratoriesService;
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
 * REST controller for managing Laboratories.
 */
@RestController
@RequestMapping("/api")
public class LaboratoriesResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoriesResource.class);
        
    @Inject
    private LaboratoriesService laboratoriesService;

    /**
     * POST  /laboratories : Create a new laboratories.
     *
     * @param laboratories the laboratories to create
     * @return the ResponseEntity with status 201 (Created) and with body the new laboratories, or with status 400 (Bad Request) if the laboratories has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/laboratories")
    @Timed
    public ResponseEntity<Laboratories> createLaboratories(@RequestBody Laboratories laboratories) throws URISyntaxException {
        log.debug("REST request to save Laboratories : {}", laboratories);
        if (laboratories.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("laboratories", "idexists", "A new laboratories cannot already have an ID")).body(null);
        }
        Laboratories result = laboratoriesService.save(laboratories);
        return ResponseEntity.created(new URI("/api/laboratories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("laboratories", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /laboratories : Updates an existing laboratories.
     *
     * @param laboratories the laboratories to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated laboratories,
     * or with status 400 (Bad Request) if the laboratories is not valid,
     * or with status 500 (Internal Server Error) if the laboratories couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/laboratories")
    @Timed
    public ResponseEntity<Laboratories> updateLaboratories(@RequestBody Laboratories laboratories) throws URISyntaxException {
        log.debug("REST request to update Laboratories : {}", laboratories);
        if (laboratories.getId() == null) {
            return createLaboratories(laboratories);
        }
        Laboratories result = laboratoriesService.save(laboratories);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("laboratories", laboratories.getId().toString()))
            .body(result);
    }

    /**
     * GET  /laboratories : get all the laboratories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of laboratories in body
     */
    @GetMapping("/laboratories")
    @Timed
    public List<Laboratories> getAllLaboratories() {
        log.debug("REST request to get all Laboratories");
        return laboratoriesService.findAll();
    }

    /**
     * GET  /laboratories/:id : get the "id" laboratories.
     *
     * @param id the id of the laboratories to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the laboratories, or with status 404 (Not Found)
     */
    @GetMapping("/laboratories/{id}")
    @Timed
    public ResponseEntity<Laboratories> getLaboratories(@PathVariable Long id) {
        log.debug("REST request to get Laboratories : {}", id);
        Laboratories laboratories = laboratoriesService.findOne(id);
        return Optional.ofNullable(laboratories)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /laboratories/:id : delete the "id" laboratories.
     *
     * @param id the id of the laboratories to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/laboratories/{id}")
    @Timed
    public ResponseEntity<Void> deleteLaboratories(@PathVariable Long id) {
        log.debug("REST request to delete Laboratories : {}", id);
        laboratoriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("laboratories", id.toString())).build();
    }

    /**
     * SEARCH  /_search/laboratories?query=:query : search for the laboratories corresponding
     * to the query.
     *
     * @param query the query of the laboratories search 
     * @return the result of the search
     */
    @GetMapping("/_search/laboratories")
    @Timed
    public List<Laboratories> searchLaboratories(@RequestParam String query) {
        log.debug("REST request to search Laboratories for query {}", query);
        return laboratoriesService.search(query);
    }


}
