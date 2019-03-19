package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.InsuranceType;
import co.tmunited.bluebook.service.InsuranceTypeService;
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
 * REST controller for managing InsuranceType.
 */
@RestController
@RequestMapping("/api")
public class InsuranceTypeResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceTypeResource.class);
        
    @Inject
    private InsuranceTypeService insuranceTypeService;

    /**
     * POST  /insurance-types : Create a new insuranceType.
     *
     * @param insuranceType the insuranceType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceType, or with status 400 (Bad Request) if the insuranceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-types")
    @Timed
    public ResponseEntity<InsuranceType> createInsuranceType(@Valid @RequestBody InsuranceType insuranceType) throws URISyntaxException {
        log.debug("REST request to save InsuranceType : {}", insuranceType);
        if (insuranceType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("insuranceType", "idexists", "A new insuranceType cannot already have an ID")).body(null);
        }
        InsuranceType result = insuranceTypeService.save(insuranceType);
        return ResponseEntity.created(new URI("/api/insurance-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("insuranceType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-types : Updates an existing insuranceType.
     *
     * @param insuranceType the insuranceType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceType,
     * or with status 400 (Bad Request) if the insuranceType is not valid,
     * or with status 500 (Internal Server Error) if the insuranceType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-types")
    @Timed
    public ResponseEntity<InsuranceType> updateInsuranceType(@Valid @RequestBody InsuranceType insuranceType) throws URISyntaxException {
        log.debug("REST request to update InsuranceType : {}", insuranceType);
        if (insuranceType.getId() == null) {
            return createInsuranceType(insuranceType);
        }
        InsuranceType result = insuranceTypeService.save(insuranceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insuranceType", insuranceType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-types : get all the insuranceTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceTypes in body
     */
    @GetMapping("/insurance-types")
    @Timed
    public List<InsuranceType> getAllInsuranceTypes() {
        log.debug("REST request to get all InsuranceTypes");
        return insuranceTypeService.findAll();
    }

    /**
     * GET  /insurance-types/:id : get the "id" insuranceType.
     *
     * @param id the id of the insuranceType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceType, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-types/{id}")
    @Timed
    public ResponseEntity<InsuranceType> getInsuranceType(@PathVariable Long id) {
        log.debug("REST request to get InsuranceType : {}", id);
        InsuranceType insuranceType = insuranceTypeService.findOne(id);
        return Optional.ofNullable(insuranceType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /insurance-types/:id : delete the "id" insuranceType.
     *
     * @param id the id of the insuranceType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceType(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceType : {}", id);
        insuranceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insuranceType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insurance-types?query=:query : search for the insuranceType corresponding
     * to the query.
     *
     * @param query the query of the insuranceType search 
     * @return the result of the search
     */
    @GetMapping("/_search/insurance-types")
    @Timed
    public List<InsuranceType> searchInsuranceTypes(@RequestParam String query) {
        log.debug("REST request to search InsuranceTypes for query {}", query);
        return insuranceTypeService.search(query);
    }


}
