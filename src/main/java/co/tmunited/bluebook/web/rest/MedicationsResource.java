package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Medications;
import co.tmunited.bluebook.service.MedicationsService;
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
 * REST controller for managing Medications.
 */
@RestController
@RequestMapping("/api")
public class MedicationsResource {

    private final Logger log = LoggerFactory.getLogger(MedicationsResource.class);
        
    @Inject
    private MedicationsService medicationsService;

    /**
     * POST  /medications : Create a new medications.
     *
     * @param medications the medications to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medications, or with status 400 (Bad Request) if the medications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medications")
    @Timed
    public ResponseEntity<Medications> createMedications(@RequestBody Medications medications) throws URISyntaxException {
        log.debug("REST request to save Medications : {}", medications);
        if (medications.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("medications", "idexists", "A new medications cannot already have an ID")).body(null);
        }
        Medications result = medicationsService.save(medications);
        return ResponseEntity.created(new URI("/api/medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("medications", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medications : Updates an existing medications.
     *
     * @param medications the medications to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medications,
     * or with status 400 (Bad Request) if the medications is not valid,
     * or with status 500 (Internal Server Error) if the medications couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medications")
    @Timed
    public ResponseEntity<Medications> updateMedications(@RequestBody Medications medications) throws URISyntaxException {
        log.debug("REST request to update Medications : {}", medications);
        if (medications.getId() == null) {
            return createMedications(medications);
        }
        Medications result = medicationsService.save(medications);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("medications", medications.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medications : get all the medications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medications in body
     */
    @GetMapping("/medications")
    @Timed
    public List<Medications> getAllMedications() {
        log.debug("REST request to get all Medications");
        return medicationsService.findAll();
    }

    /**
     * GET  /medications/:id : get the "id" medications.
     *
     * @param id the id of the medications to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medications, or with status 404 (Not Found)
     */
    @GetMapping("/medications/{id}")
    @Timed
    public ResponseEntity<Medications> getMedications(@PathVariable Long id) {
        log.debug("REST request to get Medications : {}", id);
        Medications medications = medicationsService.findOne(id);
        return Optional.ofNullable(medications)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /medications/:id : delete the "id" medications.
     *
     * @param id the id of the medications to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medications/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedications(@PathVariable Long id) {
        log.debug("REST request to delete Medications : {}", id);
        medicationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("medications", id.toString())).build();
    }

    /**
     * SEARCH  /_search/medications?query=:query : search for the medications corresponding
     * to the query.
     *
     * @param query the query of the medications search 
     * @return the result of the search
     */
    @GetMapping("/_search/medications")
    @Timed
    public List<Medications> searchMedications(@RequestParam String query) {
        log.debug("REST request to search Medications for query {}", query);
        return medicationsService.search(query);
    }


}
