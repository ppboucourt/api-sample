package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Patient_properties;
import co.tmunited.bluebook.service.Patient_propertiesService;
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
 * REST controller for managing Patient_properties.
 */
@RestController
@RequestMapping("/api")
public class Patient_propertiesResource {

    private final Logger log = LoggerFactory.getLogger(Patient_propertiesResource.class);
        
    @Inject
    private Patient_propertiesService patient_propertiesService;

    /**
     * POST  /patient-properties : Create a new patient_properties.
     *
     * @param patient_properties the patient_properties to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patient_properties, or with status 400 (Bad Request) if the patient_properties has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-properties")
    @Timed
    public ResponseEntity<Patient_properties> createPatient_properties(@RequestBody Patient_properties patient_properties) throws URISyntaxException {
        log.debug("REST request to save Patient_properties : {}", patient_properties);
        if (patient_properties.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patient_properties", "idexists", "A new patient_properties cannot already have an ID")).body(null);
        }
        Patient_properties result = patient_propertiesService.save(patient_properties);
        return ResponseEntity.created(new URI("/api/patient-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patient_properties", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-properties : Updates an existing patient_properties.
     *
     * @param patient_properties the patient_properties to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patient_properties,
     * or with status 400 (Bad Request) if the patient_properties is not valid,
     * or with status 500 (Internal Server Error) if the patient_properties couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-properties")
    @Timed
    public ResponseEntity<Patient_properties> updatePatient_properties(@RequestBody Patient_properties patient_properties) throws URISyntaxException {
        log.debug("REST request to update Patient_properties : {}", patient_properties);
        if (patient_properties.getId() == null) {
            return createPatient_properties(patient_properties);
        }
        Patient_properties result = patient_propertiesService.save(patient_properties);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patient_properties", patient_properties.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-properties : get all the patient_properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patient_properties in body
     */
    @GetMapping("/patient-properties")
    @Timed
    public List<Patient_properties> getAllPatient_properties() {
        log.debug("REST request to get all Patient_properties");
        return patient_propertiesService.findAll();
    }

    /**
     * GET  /patient-properties/:id : get the "id" patient_properties.
     *
     * @param id the id of the patient_properties to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patient_properties, or with status 404 (Not Found)
     */
    @GetMapping("/patient-properties/{id}")
    @Timed
    public ResponseEntity<Patient_properties> getPatient_properties(@PathVariable Long id) {
        log.debug("REST request to get Patient_properties : {}", id);
        Patient_properties patient_properties = patient_propertiesService.findOne(id);
        return Optional.ofNullable(patient_properties)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-properties/:id : delete the "id" patient_properties.
     *
     * @param id the id of the patient_properties to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-properties/{id}")
    @Timed
    public ResponseEntity<Void> deletePatient_properties(@PathVariable Long id) {
        log.debug("REST request to delete Patient_properties : {}", id);
        patient_propertiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patient_properties", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-properties?query=:query : search for the patient_properties corresponding
     * to the query.
     *
     * @param query the query of the patient_properties search 
     * @return the result of the search
     */
    @GetMapping("/_search/patient-properties")
    @Timed
    public List<Patient_properties> searchPatient_properties(@RequestParam String query) {
        log.debug("REST request to search Patient_properties for query {}", query);
        return patient_propertiesService.search(query);
    }


}
