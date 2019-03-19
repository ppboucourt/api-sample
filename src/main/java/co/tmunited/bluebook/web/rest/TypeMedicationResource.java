package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeMedication;
import co.tmunited.bluebook.service.TypeMedicationService;
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
 * REST controller for managing TypeMedication.
 */
@RestController
@RequestMapping("/api")
public class TypeMedicationResource {

    private final Logger log = LoggerFactory.getLogger(TypeMedicationResource.class);
        
    @Inject
    private TypeMedicationService typeMedicationService;

    /**
     * POST  /type-medications : Create a new typeMedication.
     *
     * @param typeMedication the typeMedication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeMedication, or with status 400 (Bad Request) if the typeMedication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-medications")
    @Timed
    public ResponseEntity<TypeMedication> createTypeMedication(@RequestBody TypeMedication typeMedication) throws URISyntaxException {
        log.debug("REST request to save TypeMedication : {}", typeMedication);
        if (typeMedication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeMedication", "idexists", "A new typeMedication cannot already have an ID")).body(null);
        }
        TypeMedication result = typeMedicationService.save(typeMedication);
        return ResponseEntity.created(new URI("/api/type-medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeMedication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-medications : Updates an existing typeMedication.
     *
     * @param typeMedication the typeMedication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeMedication,
     * or with status 400 (Bad Request) if the typeMedication is not valid,
     * or with status 500 (Internal Server Error) if the typeMedication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-medications")
    @Timed
    public ResponseEntity<TypeMedication> updateTypeMedication(@RequestBody TypeMedication typeMedication) throws URISyntaxException {
        log.debug("REST request to update TypeMedication : {}", typeMedication);
        if (typeMedication.getId() == null) {
            return createTypeMedication(typeMedication);
        }
        TypeMedication result = typeMedicationService.save(typeMedication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeMedication", typeMedication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-medications : get all the typeMedications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeMedications in body
     */
    @GetMapping("/type-medications")
    @Timed
    public List<TypeMedication> getAllTypeMedications() {
        log.debug("REST request to get all TypeMedications");
        return typeMedicationService.findAll();
    }

    /**
     * GET  /type-medications/:id : get the "id" typeMedication.
     *
     * @param id the id of the typeMedication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeMedication, or with status 404 (Not Found)
     */
    @GetMapping("/type-medications/{id}")
    @Timed
    public ResponseEntity<TypeMedication> getTypeMedication(@PathVariable Long id) {
        log.debug("REST request to get TypeMedication : {}", id);
        TypeMedication typeMedication = typeMedicationService.findOne(id);
        return Optional.ofNullable(typeMedication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-medications/:id : delete the "id" typeMedication.
     *
     * @param id the id of the typeMedication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-medications/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeMedication(@PathVariable Long id) {
        log.debug("REST request to delete TypeMedication : {}", id);
        typeMedicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeMedication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-medications?query=:query : search for the typeMedication corresponding
     * to the query.
     *
     * @param query the query of the typeMedication search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-medications")
    @Timed
    public List<TypeMedication> searchTypeMedications(@RequestParam String query) {
        log.debug("REST request to search TypeMedications for query {}", query);
        return typeMedicationService.search(query);
    }


}
