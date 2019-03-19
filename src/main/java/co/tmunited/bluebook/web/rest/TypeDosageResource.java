package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeDosage;
import co.tmunited.bluebook.service.TypeDosageService;
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
 * REST controller for managing TypeDosage.
 */
@RestController
@RequestMapping("/api")
public class TypeDosageResource {

    private final Logger log = LoggerFactory.getLogger(TypeDosageResource.class);
        
    @Inject
    private TypeDosageService typeDosageService;

    /**
     * POST  /type-dosages : Create a new typeDosage.
     *
     * @param typeDosage the typeDosage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDosage, or with status 400 (Bad Request) if the typeDosage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-dosages")
    @Timed
    public ResponseEntity<TypeDosage> createTypeDosage(@Valid @RequestBody TypeDosage typeDosage) throws URISyntaxException {
        log.debug("REST request to save TypeDosage : {}", typeDosage);
        if (typeDosage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeDosage", "idexists", "A new typeDosage cannot already have an ID")).body(null);
        }
        TypeDosage result = typeDosageService.save(typeDosage);
        return ResponseEntity.created(new URI("/api/type-dosages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeDosage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-dosages : Updates an existing typeDosage.
     *
     * @param typeDosage the typeDosage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDosage,
     * or with status 400 (Bad Request) if the typeDosage is not valid,
     * or with status 500 (Internal Server Error) if the typeDosage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-dosages")
    @Timed
    public ResponseEntity<TypeDosage> updateTypeDosage(@Valid @RequestBody TypeDosage typeDosage) throws URISyntaxException {
        log.debug("REST request to update TypeDosage : {}", typeDosage);
        if (typeDosage.getId() == null) {
            return createTypeDosage(typeDosage);
        }
        TypeDosage result = typeDosageService.save(typeDosage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeDosage", typeDosage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-dosages : get all the typeDosages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeDosages in body
     */
    @GetMapping("/type-dosages")
    @Timed
    public List<TypeDosage> getAllTypeDosages() {
        log.debug("REST request to get all TypeDosages");
        return typeDosageService.findAll();
    }

    /**
     * GET  /type-dosages/:id : get the "id" typeDosage.
     *
     * @param id the id of the typeDosage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDosage, or with status 404 (Not Found)
     */
    @GetMapping("/type-dosages/{id}")
    @Timed
    public ResponseEntity<TypeDosage> getTypeDosage(@PathVariable Long id) {
        log.debug("REST request to get TypeDosage : {}", id);
        TypeDosage typeDosage = typeDosageService.findOne(id);
        return Optional.ofNullable(typeDosage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-dosages/:id : delete the "id" typeDosage.
     *
     * @param id the id of the typeDosage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-dosages/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeDosage(@PathVariable Long id) {
        log.debug("REST request to delete TypeDosage : {}", id);
        typeDosageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeDosage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-dosages?query=:query : search for the typeDosage corresponding
     * to the query.
     *
     * @param query the query of the typeDosage search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-dosages")
    @Timed
    public List<TypeDosage> searchTypeDosages(@RequestParam String query) {
        log.debug("REST request to search TypeDosages for query {}", query);
        return typeDosageService.search(query);
    }


}
