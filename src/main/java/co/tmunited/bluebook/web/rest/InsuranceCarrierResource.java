package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.InsuranceCarrier;
import co.tmunited.bluebook.service.InsuranceCarrierService;
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
 * REST controller for managing InsuranceCarrier.
 */
@RestController
@RequestMapping("/api")
public class InsuranceCarrierResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceCarrierResource.class);
        
    @Inject
    private InsuranceCarrierService insuranceCarrierService;

    /**
     * POST  /insurance-carriers : Create a new insuranceCarrier.
     *
     * @param insuranceCarrier the insuranceCarrier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceCarrier, or with status 400 (Bad Request) if the insuranceCarrier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-carriers")
    @Timed
    public ResponseEntity<InsuranceCarrier> createInsuranceCarrier(@Valid @RequestBody InsuranceCarrier insuranceCarrier) throws URISyntaxException {
        log.debug("REST request to save InsuranceCarrier : {}", insuranceCarrier);
        if (insuranceCarrier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("insuranceCarrier", "idexists", "A new insuranceCarrier cannot already have an ID")).body(null);
        }
        InsuranceCarrier result = insuranceCarrierService.save(insuranceCarrier);
        return ResponseEntity.created(new URI("/api/insurance-carriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insuranceCarrier", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-carriers : Updates an existing insuranceCarrier.
     *
     * @param insuranceCarrier the insuranceCarrier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceCarrier,
     * or with status 400 (Bad Request) if the insuranceCarrier is not valid,
     * or with status 500 (Internal Server Error) if the insuranceCarrier couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-carriers")
    @Timed
    public ResponseEntity<InsuranceCarrier> updateInsuranceCarrier(@Valid @RequestBody InsuranceCarrier insuranceCarrier) throws URISyntaxException {
        log.debug("REST request to update InsuranceCarrier : {}", insuranceCarrier);
        if (insuranceCarrier.getId() == null) {
            return createInsuranceCarrier(insuranceCarrier);
        }
        InsuranceCarrier result = insuranceCarrierService.save(insuranceCarrier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insuranceCarrier", insuranceCarrier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-carriers : get all the insuranceCarriers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceCarriers in body
     */
    @GetMapping("/insurance-carriers")
    @Timed
    public List<InsuranceCarrier> getAllInsuranceCarriers() {
        log.debug("REST request to get all InsuranceCarriers");
        return insuranceCarrierService.findAll();
    }

    /**
     * GET  /insurance-carriers/:id : get the "id" insuranceCarrier.
     *
     * @param id the id of the insuranceCarrier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceCarrier, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-carriers/{id}")
    @Timed
    public ResponseEntity<InsuranceCarrier> getInsuranceCarrier(@PathVariable Long id) {
        log.debug("REST request to get InsuranceCarrier : {}", id);
        InsuranceCarrier insuranceCarrier = insuranceCarrierService.findOne(id);
        return Optional.ofNullable(insuranceCarrier)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insurance-carriers/:id : delete the "id" insuranceCarrier.
     *
     * @param id the id of the insuranceCarrier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-carriers/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceCarrier(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceCarrier : {}", id);
        insuranceCarrierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insuranceCarrier", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insurance-carriers?query=:query : search for the insuranceCarrier corresponding
     * to the query.
     *
     * @param query the query of the insuranceCarrier search 
     * @return the result of the search
     */
    @GetMapping("/_search/insurance-carriers")
    @Timed
    public List<InsuranceCarrier> searchInsuranceCarriers(@RequestParam String query) {
        log.debug("REST request to search InsuranceCarriers for query {}", query);
        return insuranceCarrierService.search(query);
    }


}
