package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeMaritalStatus;
import co.tmunited.bluebook.service.TypeMaritalStatusService;
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
 * REST controller for managing TypeMaritalStatus.
 */
@RestController
@RequestMapping("/api")
public class TypeMaritalStatusResource {

    private final Logger log = LoggerFactory.getLogger(TypeMaritalStatusResource.class);
        
    @Inject
    private TypeMaritalStatusService typeMaritalStatusService;

    /**
     * POST  /type-marital-statuses : Create a new typeMaritalStatus.
     *
     * @param typeMaritalStatus the typeMaritalStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeMaritalStatus, or with status 400 (Bad Request) if the typeMaritalStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-marital-statuses")
    @Timed
    public ResponseEntity<TypeMaritalStatus> createTypeMaritalStatus(@RequestBody TypeMaritalStatus typeMaritalStatus) throws URISyntaxException {
        log.debug("REST request to save TypeMaritalStatus : {}", typeMaritalStatus);
        if (typeMaritalStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeMaritalStatus", "idexists", "A new typeMaritalStatus cannot already have an ID")).body(null);
        }
        TypeMaritalStatus result = typeMaritalStatusService.save(typeMaritalStatus);
        return ResponseEntity.created(new URI("/api/type-marital-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeMaritalStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-marital-statuses : Updates an existing typeMaritalStatus.
     *
     * @param typeMaritalStatus the typeMaritalStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeMaritalStatus,
     * or with status 400 (Bad Request) if the typeMaritalStatus is not valid,
     * or with status 500 (Internal Server Error) if the typeMaritalStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-marital-statuses")
    @Timed
    public ResponseEntity<TypeMaritalStatus> updateTypeMaritalStatus(@RequestBody TypeMaritalStatus typeMaritalStatus) throws URISyntaxException {
        log.debug("REST request to update TypeMaritalStatus : {}", typeMaritalStatus);
        if (typeMaritalStatus.getId() == null) {
            return createTypeMaritalStatus(typeMaritalStatus);
        }
        TypeMaritalStatus result = typeMaritalStatusService.save(typeMaritalStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeMaritalStatus", typeMaritalStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-marital-statuses : get all the typeMaritalStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeMaritalStatuses in body
     */
    @GetMapping("/type-marital-statuses")
    @Timed
    public List<TypeMaritalStatus> getAllTypeMaritalStatuses() {
        log.debug("REST request to get all TypeMaritalStatuses");
        return typeMaritalStatusService.findAll();
    }

    /**
     * GET  /type-marital-statuses/:id : get the "id" typeMaritalStatus.
     *
     * @param id the id of the typeMaritalStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeMaritalStatus, or with status 404 (Not Found)
     */
    @GetMapping("/type-marital-statuses/{id}")
    @Timed
    public ResponseEntity<TypeMaritalStatus> getTypeMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to get TypeMaritalStatus : {}", id);
        TypeMaritalStatus typeMaritalStatus = typeMaritalStatusService.findOne(id);
        return Optional.ofNullable(typeMaritalStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-marital-statuses/:id : delete the "id" typeMaritalStatus.
     *
     * @param id the id of the typeMaritalStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-marital-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to delete TypeMaritalStatus : {}", id);
        typeMaritalStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeMaritalStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-marital-statuses?query=:query : search for the typeMaritalStatus corresponding
     * to the query.
     *
     * @param query the query of the typeMaritalStatus search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-marital-statuses")
    @Timed
    public List<TypeMaritalStatus> searchTypeMaritalStatuses(@RequestParam String query) {
        log.debug("REST request to search TypeMaritalStatuses for query {}", query);
        return typeMaritalStatusService.search(query);
    }


}
