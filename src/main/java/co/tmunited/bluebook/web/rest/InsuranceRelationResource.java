package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.InsuranceRelation;
import co.tmunited.bluebook.service.InsuranceRelationService;
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
 * REST controller for managing InsuranceRelation.
 */
@RestController
@RequestMapping("/api")
public class InsuranceRelationResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceRelationResource.class);
        
    @Inject
    private InsuranceRelationService insuranceRelationService;

    /**
     * POST  /insurance-relations : Create a new insuranceRelation.
     *
     * @param insuranceRelation the insuranceRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceRelation, or with status 400 (Bad Request) if the insuranceRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-relations")
    @Timed
    public ResponseEntity<InsuranceRelation> createInsuranceRelation(@Valid @RequestBody InsuranceRelation insuranceRelation) throws URISyntaxException {
        log.debug("REST request to save InsuranceRelation : {}", insuranceRelation);
        if (insuranceRelation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("insuranceRelation", "idexists", "A new insuranceRelation cannot already have an ID")).body(null);
        }
        InsuranceRelation result = insuranceRelationService.save(insuranceRelation);
        return ResponseEntity.created(new URI("/api/insurance-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insuranceRelation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-relations : Updates an existing insuranceRelation.
     *
     * @param insuranceRelation the insuranceRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceRelation,
     * or with status 400 (Bad Request) if the insuranceRelation is not valid,
     * or with status 500 (Internal Server Error) if the insuranceRelation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-relations")
    @Timed
    public ResponseEntity<InsuranceRelation> updateInsuranceRelation(@Valid @RequestBody InsuranceRelation insuranceRelation) throws URISyntaxException {
        log.debug("REST request to update InsuranceRelation : {}", insuranceRelation);
        if (insuranceRelation.getId() == null) {
            return createInsuranceRelation(insuranceRelation);
        }
        InsuranceRelation result = insuranceRelationService.save(insuranceRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insuranceRelation", insuranceRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-relations : get all the insuranceRelations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceRelations in body
     */
    @GetMapping("/insurance-relations")
    @Timed
    public List<InsuranceRelation> getAllInsuranceRelations() {
        log.debug("REST request to get all InsuranceRelations");
        return insuranceRelationService.findAll();
    }

    /**
     * GET  /insurance-relations/:id : get the "id" insuranceRelation.
     *
     * @param id the id of the insuranceRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceRelation, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-relations/{id}")
    @Timed
    public ResponseEntity<InsuranceRelation> getInsuranceRelation(@PathVariable Long id) {
        log.debug("REST request to get InsuranceRelation : {}", id);
        InsuranceRelation insuranceRelation = insuranceRelationService.findOne(id);
        return Optional.ofNullable(insuranceRelation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insurance-relations/:id : delete the "id" insuranceRelation.
     *
     * @param id the id of the insuranceRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceRelation(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceRelation : {}", id);
        insuranceRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insuranceRelation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insurance-relations?query=:query : search for the insuranceRelation corresponding
     * to the query.
     *
     * @param query the query of the insuranceRelation search 
     * @return the result of the search
     */
    @GetMapping("/_search/insurance-relations")
    @Timed
    public List<InsuranceRelation> searchInsuranceRelations(@RequestParam String query) {
        log.debug("REST request to search InsuranceRelations for query {}", query);
        return insuranceRelationService.search(query);
    }


}
