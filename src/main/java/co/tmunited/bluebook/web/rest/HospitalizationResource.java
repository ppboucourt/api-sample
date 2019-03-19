package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Hospitalization;
import co.tmunited.bluebook.service.HospitalizationService;
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
 * REST controller for managing Hospitalization.
 */
@RestController
@RequestMapping("/api")
public class HospitalizationResource {

    private final Logger log = LoggerFactory.getLogger(HospitalizationResource.class);
        
    @Inject
    private HospitalizationService hospitalizationService;

    /**
     * POST  /hospitalizations : Create a new hospitalization.
     *
     * @param hospitalization the hospitalization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hospitalization, or with status 400 (Bad Request) if the hospitalization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hospitalizations")
    @Timed
    public ResponseEntity<Hospitalization> createHospitalization(@RequestBody Hospitalization hospitalization) throws URISyntaxException {
        log.debug("REST request to save Hospitalization : {}", hospitalization);
        if (hospitalization.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hospitalization", "idexists", "A new hospitalization cannot already have an ID")).body(null);
        }
        Hospitalization result = hospitalizationService.save(hospitalization);
        return ResponseEntity.created(new URI("/api/hospitalizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hospitalization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hospitalizations : Updates an existing hospitalization.
     *
     * @param hospitalization the hospitalization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hospitalization,
     * or with status 400 (Bad Request) if the hospitalization is not valid,
     * or with status 500 (Internal Server Error) if the hospitalization couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hospitalizations")
    @Timed
    public ResponseEntity<Hospitalization> updateHospitalization(@RequestBody Hospitalization hospitalization) throws URISyntaxException {
        log.debug("REST request to update Hospitalization : {}", hospitalization);
        if (hospitalization.getId() == null) {
            return createHospitalization(hospitalization);
        }
        Hospitalization result = hospitalizationService.save(hospitalization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hospitalization", hospitalization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hospitalizations : get all the hospitalizations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hospitalizations in body
     */
    @GetMapping("/hospitalizations")
    @Timed
    public List<Hospitalization> getAllHospitalizations() {
        log.debug("REST request to get all Hospitalizations");
        return hospitalizationService.findAll();
    }

    /**
     * GET  /hospitalizations/:id : get the "id" hospitalization.
     *
     * @param id the id of the hospitalization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hospitalization, or with status 404 (Not Found)
     */
    @GetMapping("/hospitalizations/{id}")
    @Timed
    public ResponseEntity<Hospitalization> getHospitalization(@PathVariable Long id) {
        log.debug("REST request to get Hospitalization : {}", id);
        Hospitalization hospitalization = hospitalizationService.findOne(id);
        return Optional.ofNullable(hospitalization)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hospitalizations/:id : delete the "id" hospitalization.
     *
     * @param id the id of the hospitalization to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hospitalizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteHospitalization(@PathVariable Long id) {
        log.debug("REST request to delete Hospitalization : {}", id);
        hospitalizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hospitalization", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hospitalizations?query=:query : search for the hospitalization corresponding
     * to the query.
     *
     * @param query the query of the hospitalization search 
     * @return the result of the search
     */
    @GetMapping("/_search/hospitalizations")
    @Timed
    public List<Hospitalization> searchHospitalizations(@RequestParam String query) {
        log.debug("REST request to search Hospitalizations for query {}", query);
        return hospitalizationService.search(query);
    }


}
