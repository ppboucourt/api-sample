package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeAdmissionStatus;
import co.tmunited.bluebook.service.TypeAdmissionStatusService;
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
 * REST controller for managing TypeAdmissionStatus.
 */
@RestController
@RequestMapping("/api")
public class TypeAdmissionStatusResource {

    private final Logger log = LoggerFactory.getLogger(TypeAdmissionStatusResource.class);
        
    @Inject
    private TypeAdmissionStatusService typeAdmissionStatusService;

    /**
     * POST  /type-admission-statuses : Create a new typeAdmissionStatus.
     *
     * @param typeAdmissionStatus the typeAdmissionStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeAdmissionStatus, or with status 400 (Bad Request) if the typeAdmissionStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-admission-statuses")
    @Timed
    public ResponseEntity<TypeAdmissionStatus> createTypeAdmissionStatus(@RequestBody TypeAdmissionStatus typeAdmissionStatus) throws URISyntaxException {
        log.debug("REST request to save TypeAdmissionStatus : {}", typeAdmissionStatus);
        if (typeAdmissionStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeAdmissionStatus", "idexists", "A new typeAdmissionStatus cannot already have an ID")).body(null);
        }
        TypeAdmissionStatus result = typeAdmissionStatusService.save(typeAdmissionStatus);
        return ResponseEntity.created(new URI("/api/type-admission-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeAdmissionStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-admission-statuses : Updates an existing typeAdmissionStatus.
     *
     * @param typeAdmissionStatus the typeAdmissionStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeAdmissionStatus,
     * or with status 400 (Bad Request) if the typeAdmissionStatus is not valid,
     * or with status 500 (Internal Server Error) if the typeAdmissionStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-admission-statuses")
    @Timed
    public ResponseEntity<TypeAdmissionStatus> updateTypeAdmissionStatus(@RequestBody TypeAdmissionStatus typeAdmissionStatus) throws URISyntaxException {
        log.debug("REST request to update TypeAdmissionStatus : {}", typeAdmissionStatus);
        if (typeAdmissionStatus.getId() == null) {
            return createTypeAdmissionStatus(typeAdmissionStatus);
        }
        TypeAdmissionStatus result = typeAdmissionStatusService.save(typeAdmissionStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeAdmissionStatus", typeAdmissionStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-admission-statuses : get all the typeAdmissionStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeAdmissionStatuses in body
     */
    @GetMapping("/type-admission-statuses")
    @Timed
    public List<TypeAdmissionStatus> getAllTypeAdmissionStatuses() {
        log.debug("REST request to get all TypeAdmissionStatuses");
        return typeAdmissionStatusService.findAll();
    }

    /**
     * GET  /type-admission-statuses/:id : get the "id" typeAdmissionStatus.
     *
     * @param id the id of the typeAdmissionStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeAdmissionStatus, or with status 404 (Not Found)
     */
    @GetMapping("/type-admission-statuses/{id}")
    @Timed
    public ResponseEntity<TypeAdmissionStatus> getTypeAdmissionStatus(@PathVariable Long id) {
        log.debug("REST request to get TypeAdmissionStatus : {}", id);
        TypeAdmissionStatus typeAdmissionStatus = typeAdmissionStatusService.findOne(id);
        return Optional.ofNullable(typeAdmissionStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-admission-statuses/:id : delete the "id" typeAdmissionStatus.
     *
     * @param id the id of the typeAdmissionStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-admission-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeAdmissionStatus(@PathVariable Long id) {
        log.debug("REST request to delete TypeAdmissionStatus : {}", id);
        typeAdmissionStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeAdmissionStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-admission-statuses?query=:query : search for the typeAdmissionStatus corresponding
     * to the query.
     *
     * @param query the query of the typeAdmissionStatus search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-admission-statuses")
    @Timed
    public List<TypeAdmissionStatus> searchTypeAdmissionStatuses(@RequestParam String query) {
        log.debug("REST request to search TypeAdmissionStatuses for query {}", query);
        return typeAdmissionStatusService.search(query);
    }


}
