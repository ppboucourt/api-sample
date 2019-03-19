package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.LabRequisitionsComponents;
import co.tmunited.bluebook.service.LabRequisitionsComponentsService;
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
 * REST controller for managing LabRequisitionsComponents.
 */
@RestController
@RequestMapping("/api")
public class LabRequisitionsComponentsResource {

    private final Logger log = LoggerFactory.getLogger(LabRequisitionsComponentsResource.class);
        
    @Inject
    private LabRequisitionsComponentsService labRequisitionsComponentsService;

    /**
     * POST  /lab-requisitions-components : Create a new labRequisitionsComponents.
     *
     * @param labRequisitionsComponents the labRequisitionsComponents to create
     * @return the ResponseEntity with status 201 (Created) and with body the new labRequisitionsComponents, or with status 400 (Bad Request) if the labRequisitionsComponents has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lab-requisitions-components")
    @Timed
    public ResponseEntity<LabRequisitionsComponents> createLabRequisitionsComponents(@RequestBody LabRequisitionsComponents labRequisitionsComponents) throws URISyntaxException {
        log.debug("REST request to save LabRequisitionsComponents : {}", labRequisitionsComponents);
        if (labRequisitionsComponents.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("labRequisitionsComponents", "idexists", "A new labRequisitionsComponents cannot already have an ID")).body(null);
        }
        LabRequisitionsComponents result = labRequisitionsComponentsService.save(labRequisitionsComponents);
        return ResponseEntity.created(new URI("/api/lab-requisitions-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("labRequisitionsComponents", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lab-requisitions-components : Updates an existing labRequisitionsComponents.
     *
     * @param labRequisitionsComponents the labRequisitionsComponents to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated labRequisitionsComponents,
     * or with status 400 (Bad Request) if the labRequisitionsComponents is not valid,
     * or with status 500 (Internal Server Error) if the labRequisitionsComponents couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lab-requisitions-components")
    @Timed
    public ResponseEntity<LabRequisitionsComponents> updateLabRequisitionsComponents(@RequestBody LabRequisitionsComponents labRequisitionsComponents) throws URISyntaxException {
        log.debug("REST request to update LabRequisitionsComponents : {}", labRequisitionsComponents);
        if (labRequisitionsComponents.getId() == null) {
            return createLabRequisitionsComponents(labRequisitionsComponents);
        }
        LabRequisitionsComponents result = labRequisitionsComponentsService.save(labRequisitionsComponents);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("labRequisitionsComponents", labRequisitionsComponents.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lab-requisitions-components : get all the labRequisitionsComponents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labRequisitionsComponents in body
     */
    @GetMapping("/lab-requisitions-components")
    @Timed
    public List<LabRequisitionsComponents> getAllLabRequisitionsComponents() {
        log.debug("REST request to get all LabRequisitionsComponents");
        return labRequisitionsComponentsService.findAll();
    }

    /**
     * GET  /lab-requisitions-components/:id : get the "id" labRequisitionsComponents.
     *
     * @param id the id of the labRequisitionsComponents to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the labRequisitionsComponents, or with status 404 (Not Found)
     */
    @GetMapping("/lab-requisitions-components/{id}")
    @Timed
    public ResponseEntity<LabRequisitionsComponents> getLabRequisitionsComponents(@PathVariable Long id) {
        log.debug("REST request to get LabRequisitionsComponents : {}", id);
        LabRequisitionsComponents labRequisitionsComponents = labRequisitionsComponentsService.findOne(id);
        return Optional.ofNullable(labRequisitionsComponents)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lab-requisitions-components/:id : delete the "id" labRequisitionsComponents.
     *
     * @param id the id of the labRequisitionsComponents to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lab-requisitions-components/{id}")
    @Timed
    public ResponseEntity<Void> deleteLabRequisitionsComponents(@PathVariable Long id) {
        log.debug("REST request to delete LabRequisitionsComponents : {}", id);
        labRequisitionsComponentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("labRequisitionsComponents", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lab-requisitions-components?query=:query : search for the labRequisitionsComponents corresponding
     * to the query.
     *
     * @param query the query of the labRequisitionsComponents search 
     * @return the result of the search
     */
    @GetMapping("/_search/lab-requisitions-components")
    @Timed
    public List<LabRequisitionsComponents> searchLabRequisitionsComponents(@RequestParam String query) {
        log.debug("REST request to search LabRequisitionsComponents for query {}", query);
        return labRequisitionsComponentsService.search(query);
    }


}
