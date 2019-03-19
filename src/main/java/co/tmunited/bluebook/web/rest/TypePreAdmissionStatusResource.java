package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePreAdmissionStatus;
import co.tmunited.bluebook.service.TypePreAdmissionStatusService;
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
 * REST controller for managing TypePreAdmissionStatus.
 */
@RestController
@RequestMapping("/api")
public class TypePreAdmissionStatusResource {

    private final Logger log = LoggerFactory.getLogger(TypePreAdmissionStatusResource.class);
        
    @Inject
    private TypePreAdmissionStatusService typePreAdmissionStatusService;

    /**
     * POST  /type-pre-admission-statuses : Create a new typePreAdmissionStatus.
     *
     * @param typePreAdmissionStatus the typePreAdmissionStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePreAdmissionStatus, or with status 400 (Bad Request) if the typePreAdmissionStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-pre-admission-statuses")
    @Timed
    public ResponseEntity<TypePreAdmissionStatus> createTypePreAdmissionStatus(@RequestBody TypePreAdmissionStatus typePreAdmissionStatus) throws URISyntaxException {
        log.debug("REST request to save TypePreAdmissionStatus : {}", typePreAdmissionStatus);
        if (typePreAdmissionStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePreAdmissionStatus", "idexists", "A new typePreAdmissionStatus cannot already have an ID")).body(null);
        }
        TypePreAdmissionStatus result = typePreAdmissionStatusService.save(typePreAdmissionStatus);
        return ResponseEntity.created(new URI("/api/type-pre-admission-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePreAdmissionStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-pre-admission-statuses : Updates an existing typePreAdmissionStatus.
     *
     * @param typePreAdmissionStatus the typePreAdmissionStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePreAdmissionStatus,
     * or with status 400 (Bad Request) if the typePreAdmissionStatus is not valid,
     * or with status 500 (Internal Server Error) if the typePreAdmissionStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-pre-admission-statuses")
    @Timed
    public ResponseEntity<TypePreAdmissionStatus> updateTypePreAdmissionStatus(@RequestBody TypePreAdmissionStatus typePreAdmissionStatus) throws URISyntaxException {
        log.debug("REST request to update TypePreAdmissionStatus : {}", typePreAdmissionStatus);
        if (typePreAdmissionStatus.getId() == null) {
            return createTypePreAdmissionStatus(typePreAdmissionStatus);
        }
        TypePreAdmissionStatus result = typePreAdmissionStatusService.save(typePreAdmissionStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePreAdmissionStatus", typePreAdmissionStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-pre-admission-statuses : get all the typePreAdmissionStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePreAdmissionStatuses in body
     */
    @GetMapping("/type-pre-admission-statuses")
    @Timed
    public List<TypePreAdmissionStatus> getAllTypePreAdmissionStatuses() {
        log.debug("REST request to get all TypePreAdmissionStatuses");
        return typePreAdmissionStatusService.findAll();
    }

    /**
     * GET  /type-pre-admission-statuses/:id : get the "id" typePreAdmissionStatus.
     *
     * @param id the id of the typePreAdmissionStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePreAdmissionStatus, or with status 404 (Not Found)
     */
    @GetMapping("/type-pre-admission-statuses/{id}")
    @Timed
    public ResponseEntity<TypePreAdmissionStatus> getTypePreAdmissionStatus(@PathVariable Long id) {
        log.debug("REST request to get TypePreAdmissionStatus : {}", id);
        TypePreAdmissionStatus typePreAdmissionStatus = typePreAdmissionStatusService.findOne(id);
        return Optional.ofNullable(typePreAdmissionStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-pre-admission-statuses/:id : delete the "id" typePreAdmissionStatus.
     *
     * @param id the id of the typePreAdmissionStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-pre-admission-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePreAdmissionStatus(@PathVariable Long id) {
        log.debug("REST request to delete TypePreAdmissionStatus : {}", id);
        typePreAdmissionStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePreAdmissionStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-pre-admission-statuses?query=:query : search for the typePreAdmissionStatus corresponding
     * to the query.
     *
     * @param query the query of the typePreAdmissionStatus search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-pre-admission-statuses")
    @Timed
    public List<TypePreAdmissionStatus> searchTypePreAdmissionStatuses(@RequestParam String query) {
        log.debug("REST request to search TypePreAdmissionStatuses for query {}", query);
        return typePreAdmissionStatusService.search(query);
    }


}
