package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.TypeSpeciality;
import co.tmunited.bluebook.service.TypeSpecialityService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeSpeciality.
 */
@RestController
@RequestMapping("/api")
public class TypeSpecialistyResource {

    private final Logger log = LoggerFactory.getLogger(TypeSpecialistyResource.class);

    @Inject
    private TypeSpecialityService TypeSpecialityService;

    /**
     * POST  /type-speciality : Create a new TypeSpeciality.
     *
     * @param TypeSpeciality the TypeSpeciality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new TypeSpeciality, or with status 400 (Bad Request) if the TypeSpeciality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-speciality")
    @Timed
    public ResponseEntity<TypeSpeciality> createTypeSpeciality(@Valid @RequestBody TypeSpeciality TypeSpeciality) throws URISyntaxException {
        log.debug("REST request to save TypeSpeciality : {}", TypeSpeciality);
        if (TypeSpeciality.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TypeSpeciality", "idexists", "A new TypeSpeciality cannot already have an ID")).body(null);
        }
        TypeSpeciality result = TypeSpecialityService.save(TypeSpeciality);
        return ResponseEntity.created(new URI("/api/type-speciality/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("TypeSpeciality", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-speciality : Updates an existing TypeSpeciality.
     *
     * @param TypeSpeciality the TypeSpeciality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated TypeSpeciality,
     * or with status 400 (Bad Request) if the TypeSpeciality is not valid,
     * or with status 500 (Internal Server Error) if the TypeSpeciality couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-speciality")
    @Timed
    public ResponseEntity<TypeSpeciality> updateTypeSpeciality(@Valid @RequestBody TypeSpeciality TypeSpeciality) throws URISyntaxException {
        log.debug("REST request to update TypeSpeciality : {}", TypeSpeciality);
        if (TypeSpeciality.getId() == null) {
            return createTypeSpeciality(TypeSpeciality);
        }
        TypeSpeciality result = TypeSpecialityService.save(TypeSpeciality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TypeSpeciality", TypeSpeciality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-speciality : get all the TypeSpeciality.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of TypeSpeciality in body
     */
    @GetMapping("/type-speciality")
    @Timed
    public List<TypeSpeciality> getAllTypeSpeciality() {
        log.debug("REST request to get all TypeSpeciality");
        return TypeSpecialityService.findAll();
    }

    /**
     * GET  /type-speciality/:id : get the "id" TypeSpeciality.
     *
     * @param id the id of the TypeSpeciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the TypeSpeciality, or with status 404 (Not Found)
     */
    @GetMapping("/type-speciality/{id}")
    @Timed
    public ResponseEntity<TypeSpeciality> getTypeSpeciality(@PathVariable Long id) {
        log.debug("REST request to get TypeSpeciality : {}", id);
        TypeSpeciality TypeSpeciality = TypeSpecialityService.findOne(id);
        return Optional.ofNullable(TypeSpeciality)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-speciality/:id : delete the "id" TypeSpeciality.
     *
     * @param id the id of the TypeSpeciality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-speciality/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeSpeciality(@PathVariable Long id) {
        log.debug("REST request to delete TypeSpeciality : {}", id);
        TypeSpecialityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TypeSpeciality", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-speciality?query=:query : search for the TypeSpeciality corresponding
     * to the query.
     *
     * @param query the query of the TypeSpeciality search
     * @return the result of the search
     */
    @GetMapping("/_search/type-speciality")
    @Timed
    public List<TypeSpeciality> searchTypeSpeciality(@RequestParam String query) {
        log.debug("REST request to search TypeSpeciality for query {}", query);
        return TypeSpecialityService.search(query);
    }


}
