package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.InsuranceLevel;
import co.tmunited.bluebook.service.InsuranceLevelService;
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
 * REST controller for managing InsuranceLevel.
 */
@RestController
@RequestMapping("/api")
public class InsuranceLevelResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceLevelResource.class);
        
    @Inject
    private InsuranceLevelService insuranceLevelService;

    /**
     * POST  /insurance-levels : Create a new insuranceLevel.
     *
     * @param insuranceLevel the insuranceLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceLevel, or with status 400 (Bad Request) if the insuranceLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-levels")
    @Timed
    public ResponseEntity<InsuranceLevel> createInsuranceLevel(@Valid @RequestBody InsuranceLevel insuranceLevel) throws URISyntaxException {
        log.debug("REST request to save InsuranceLevel : {}", insuranceLevel);
        if (insuranceLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("insuranceLevel", "idexists", "A new insuranceLevel cannot already have an ID")).body(null);
        }
        InsuranceLevel result = insuranceLevelService.save(insuranceLevel);
        return ResponseEntity.created(new URI("/api/insurance-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insuranceLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-levels : Updates an existing insuranceLevel.
     *
     * @param insuranceLevel the insuranceLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceLevel,
     * or with status 400 (Bad Request) if the insuranceLevel is not valid,
     * or with status 500 (Internal Server Error) if the insuranceLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-levels")
    @Timed
    public ResponseEntity<InsuranceLevel> updateInsuranceLevel(@Valid @RequestBody InsuranceLevel insuranceLevel) throws URISyntaxException {
        log.debug("REST request to update InsuranceLevel : {}", insuranceLevel);
        if (insuranceLevel.getId() == null) {
            return createInsuranceLevel(insuranceLevel);
        }
        InsuranceLevel result = insuranceLevelService.save(insuranceLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insuranceLevel", insuranceLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-levels : get all the insuranceLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceLevels in body
     */
    @GetMapping("/insurance-levels")
    @Timed
    public List<InsuranceLevel> getAllInsuranceLevels() {
        log.debug("REST request to get all InsuranceLevels");
        return insuranceLevelService.findAll();
    }

    /**
     * GET  /insurance-levels/:id : get the "id" insuranceLevel.
     *
     * @param id the id of the insuranceLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceLevel, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-levels/{id}")
    @Timed
    public ResponseEntity<InsuranceLevel> getInsuranceLevel(@PathVariable Long id) {
        log.debug("REST request to get InsuranceLevel : {}", id);
        InsuranceLevel insuranceLevel = insuranceLevelService.findOne(id);
        return Optional.ofNullable(insuranceLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insurance-levels/:id : delete the "id" insuranceLevel.
     *
     * @param id the id of the insuranceLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceLevel(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceLevel : {}", id);
        insuranceLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insuranceLevel", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insurance-levels?query=:query : search for the insuranceLevel corresponding
     * to the query.
     *
     * @param query the query of the insuranceLevel search 
     * @return the result of the search
     */
    @GetMapping("/_search/insurance-levels")
    @Timed
    public List<InsuranceLevel> searchInsuranceLevels(@RequestParam String query) {
        log.debug("REST request to search InsuranceLevels for query {}", query);
        return insuranceLevelService.search(query);
    }


}
