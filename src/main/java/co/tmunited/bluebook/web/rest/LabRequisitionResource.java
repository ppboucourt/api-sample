package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.LabRequisition;
import co.tmunited.bluebook.service.LabRequisitionService;
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
 * REST controller for managing LabRequisition.
 */
@RestController
@RequestMapping("/api")
public class LabRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(LabRequisitionResource.class);
        
    @Inject
    private LabRequisitionService labRequisitionService;

    /**
     * POST  /lab-requisitions : Create a new labRequisition.
     *
     * @param labRequisition the labRequisition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new labRequisition, or with status 400 (Bad Request) if the labRequisition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lab-requisitions")
    @Timed
    public ResponseEntity<LabRequisition> createLabRequisition(@RequestBody LabRequisition labRequisition) throws URISyntaxException {
        log.debug("REST request to save LabRequisition : {}", labRequisition);
        if (labRequisition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("labRequisition", "idexists", "A new labRequisition cannot already have an ID")).body(null);
        }
        LabRequisition result = labRequisitionService.save(labRequisition);
        return ResponseEntity.created(new URI("/api/lab-requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("labRequisition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lab-requisitions : Updates an existing labRequisition.
     *
     * @param labRequisition the labRequisition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated labRequisition,
     * or with status 400 (Bad Request) if the labRequisition is not valid,
     * or with status 500 (Internal Server Error) if the labRequisition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lab-requisitions")
    @Timed
    public ResponseEntity<LabRequisition> updateLabRequisition(@RequestBody LabRequisition labRequisition) throws URISyntaxException {
        log.debug("REST request to update LabRequisition : {}", labRequisition);
        if (labRequisition.getId() == null) {
            return createLabRequisition(labRequisition);
        }
        LabRequisition result = labRequisitionService.save(labRequisition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("labRequisition", labRequisition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lab-requisitions : get all the labRequisitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labRequisitions in body
     */
    @GetMapping("/lab-requisitions")
    @Timed
    public List<LabRequisition> getAllLabRequisitions() {
        log.debug("REST request to get all LabRequisitions");
        return labRequisitionService.findAll();
    }

    /**
     * GET  /lab-requisitions/:id : get the "id" labRequisition.
     *
     * @param id the id of the labRequisition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the labRequisition, or with status 404 (Not Found)
     */
    @GetMapping("/lab-requisitions/{id}")
    @Timed
    public ResponseEntity<LabRequisition> getLabRequisition(@PathVariable Long id) {
        log.debug("REST request to get LabRequisition : {}", id);
        LabRequisition labRequisition = labRequisitionService.findOne(id);
        return Optional.ofNullable(labRequisition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lab-requisitions/:id : delete the "id" labRequisition.
     *
     * @param id the id of the labRequisition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lab-requisitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteLabRequisition(@PathVariable Long id) {
        log.debug("REST request to delete LabRequisition : {}", id);
        labRequisitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("labRequisition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lab-requisitions?query=:query : search for the labRequisition corresponding
     * to the query.
     *
     * @param query the query of the labRequisition search 
     * @return the result of the search
     */
    @GetMapping("/_search/lab-requisitions")
    @Timed
    public List<LabRequisition> searchLabRequisitions(@RequestParam String query) {
        log.debug("REST request to search LabRequisitions for query {}", query);
        return labRequisitionService.search(query);
    }


}
