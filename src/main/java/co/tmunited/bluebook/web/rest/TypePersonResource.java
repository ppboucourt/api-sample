package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePerson;
import co.tmunited.bluebook.service.TypePersonService;
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
 * REST controller for managing TypePerson.
 */
@RestController
@RequestMapping("/api")
public class TypePersonResource {

    private final Logger log = LoggerFactory.getLogger(TypePersonResource.class);
        
    @Inject
    private TypePersonService typePersonService;

    /**
     * POST  /type-people : Create a new typePerson.
     *
     * @param typePerson the typePerson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePerson, or with status 400 (Bad Request) if the typePerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-people")
    @Timed
    public ResponseEntity<TypePerson> createTypePerson(@Valid @RequestBody TypePerson typePerson) throws URISyntaxException {
        log.debug("REST request to save TypePerson : {}", typePerson);
        if (typePerson.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePerson", "idexists", "A new typePerson cannot already have an ID")).body(null);
        }
        TypePerson result = typePersonService.save(typePerson);
        return ResponseEntity.created(new URI("/api/type-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePerson", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-people : Updates an existing typePerson.
     *
     * @param typePerson the typePerson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePerson,
     * or with status 400 (Bad Request) if the typePerson is not valid,
     * or with status 500 (Internal Server Error) if the typePerson couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-people")
    @Timed
    public ResponseEntity<TypePerson> updateTypePerson(@Valid @RequestBody TypePerson typePerson) throws URISyntaxException {
        log.debug("REST request to update TypePerson : {}", typePerson);
        if (typePerson.getId() == null) {
            return createTypePerson(typePerson);
        }
        TypePerson result = typePersonService.save(typePerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePerson", typePerson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-people : get all the typePeople.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePeople in body
     */
    @GetMapping("/type-people")
    @Timed
    public List<TypePerson> getAllTypePeople() {
        log.debug("REST request to get all TypePeople");
        return typePersonService.findAll();
    }

    /**
     * GET  /type-people/:id : get the "id" typePerson.
     *
     * @param id the id of the typePerson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePerson, or with status 404 (Not Found)
     */
    @GetMapping("/type-people/{id}")
    @Timed
    public ResponseEntity<TypePerson> getTypePerson(@PathVariable Long id) {
        log.debug("REST request to get TypePerson : {}", id);
        TypePerson typePerson = typePersonService.findOne(id);
        return Optional.ofNullable(typePerson)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-people/:id : delete the "id" typePerson.
     *
     * @param id the id of the typePerson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePerson(@PathVariable Long id) {
        log.debug("REST request to delete TypePerson : {}", id);
        typePersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePerson", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-people?query=:query : search for the typePerson corresponding
     * to the query.
     *
     * @param query the query of the typePerson search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-people")
    @Timed
    public List<TypePerson> searchTypePeople(@RequestParam String query) {
        log.debug("REST request to search TypePeople for query {}", query);
        return typePersonService.search(query);
    }


}
