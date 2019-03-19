package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Insurance;
import co.tmunited.bluebook.service.InsuranceService;
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
 * REST controller for managing Insurance.
 */
@RestController
@RequestMapping("/api")
public class InsuranceResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceResource.class);
        
    @Inject
    private InsuranceService insuranceService;

    /**
     * POST  /insurances : Create a new insurance.
     *
     * @param insurance the insurance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurance, or with status 400 (Bad Request) if the insurance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurances")
    @Timed
    public ResponseEntity<Insurance> createInsurance(@Valid @RequestBody Insurance insurance) throws URISyntaxException {
        log.debug("REST request to save Insurance : {}", insurance);
        if (insurance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("insurance", "idexists", "A new insurance cannot already have an ID")).body(null);
        }
        Insurance result = insuranceService.save(insurance);
        return ResponseEntity.created(new URI("/api/insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insurance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurances : Updates an existing insurance.
     *
     * @param insurance the insurance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurance,
     * or with status 400 (Bad Request) if the insurance is not valid,
     * or with status 500 (Internal Server Error) if the insurance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurances")
    @Timed
    public ResponseEntity<Insurance> updateInsurance(@Valid @RequestBody Insurance insurance) throws URISyntaxException {
        log.debug("REST request to update Insurance : {}", insurance);
        if (insurance.getId() == null) {
            return createInsurance(insurance);
        }
        Insurance result = insuranceService.save(insurance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insurance", insurance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurances : get all the insurances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insurances in body
     */
    @GetMapping("/insurances")
    @Timed
    public List<Insurance> getAllInsurances() {
        log.debug("REST request to get all Insurances");
        return insuranceService.findAll();
    }

    /**
     * GET  /insurances/:id : get the "id" insurance.
     *
     * @param id the id of the insurance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurance, or with status 404 (Not Found)
     */
    @GetMapping("/insurances/{id}")
    @Timed
    public ResponseEntity<Insurance> getInsurance(@PathVariable Long id) {
        log.debug("REST request to get Insurance : {}", id);
        Insurance insurance = insuranceService.findOne(id);
        return Optional.ofNullable(insurance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insurances/:id : delete the "id" insurance.
     *
     * @param id the id of the insurance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurances/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        log.debug("REST request to delete Insurance : {}", id);
        insuranceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insurance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insurances?query=:query : search for the insurance corresponding
     * to the query.
     *
     * @param query the query of the insurance search 
     * @return the result of the search
     */
    @GetMapping("/_search/insurances")
    @Timed
    public List<Insurance> searchInsurances(@RequestParam String query) {
        log.debug("REST request to search Insurances for query {}", query);
        return insuranceService.search(query);
    }


}
