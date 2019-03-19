package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Fields;
import co.tmunited.bluebook.service.FieldsService;
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
 * REST controller for managing Fields.
 */
@RestController
@RequestMapping("/api")
public class FieldsResource {

    private final Logger log = LoggerFactory.getLogger(FieldsResource.class);
        
    @Inject
    private FieldsService fieldsService;

    /**
     * POST  /fields : Create a new fields.
     *
     * @param fields the fields to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fields, or with status 400 (Bad Request) if the fields has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fields")
    @Timed
    public ResponseEntity<Fields> createFields(@Valid @RequestBody Fields fields) throws URISyntaxException {
        log.debug("REST request to save Fields : {}", fields);
        if (fields.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fields", "idexists", "A new fields cannot already have an ID")).body(null);
        }
        Fields result = fieldsService.save(fields);
        return ResponseEntity.created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fields", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fields : Updates an existing fields.
     *
     * @param fields the fields to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fields,
     * or with status 400 (Bad Request) if the fields is not valid,
     * or with status 500 (Internal Server Error) if the fields couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fields")
    @Timed
    public ResponseEntity<Fields> updateFields(@Valid @RequestBody Fields fields) throws URISyntaxException {
        log.debug("REST request to update Fields : {}", fields);
        if (fields.getId() == null) {
            return createFields(fields);
        }
        Fields result = fieldsService.save(fields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fields", fields.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fields : get all the fields.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fields in body
     */
    @GetMapping("/fields")
    @Timed
    public List<Fields> getAllFields() {
        log.debug("REST request to get all Fields");
        return fieldsService.findAll();
    }

    /**
     * GET  /fields/:id : get the "id" fields.
     *
     * @param id the id of the fields to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fields, or with status 404 (Not Found)
     */
    @GetMapping("/fields/{id}")
    @Timed
    public ResponseEntity<Fields> getFields(@PathVariable Long id) {
        log.debug("REST request to get Fields : {}", id);
        Fields fields = fieldsService.findOne(id);
        return Optional.ofNullable(fields)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fields/:id : delete the "id" fields.
     *
     * @param id the id of the fields to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteFields(@PathVariable Long id) {
        log.debug("REST request to delete Fields : {}", id);
        fieldsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fields", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fields?query=:query : search for the fields corresponding
     * to the query.
     *
     * @param query the query of the fields search 
     * @return the result of the search
     */
    @GetMapping("/_search/fields")
    @Timed
    public List<Fields> searchFields(@RequestParam String query) {
        log.debug("REST request to search Fields for query {}", query);
        return fieldsService.search(query);
    }


}
