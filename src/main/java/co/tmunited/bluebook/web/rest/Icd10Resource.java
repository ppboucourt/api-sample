package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Icd10;
import co.tmunited.bluebook.service.Icd10Service;
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
 * REST controller for managing Icd10.
 */
@RestController
@RequestMapping("/api")
public class Icd10Resource {

    private final Logger log = LoggerFactory.getLogger(Icd10Resource.class);
        
    @Inject
    private Icd10Service icd10Service;

    /**
     * POST  /icd-10-s : Create a new icd10.
     *
     * @param icd10 the icd10 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new icd10, or with status 400 (Bad Request) if the icd10 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/icd-10-s")
    @Timed
    public ResponseEntity<Icd10> createIcd10(@RequestBody Icd10 icd10) throws URISyntaxException {
        log.debug("REST request to save Icd10 : {}", icd10);
        if (icd10.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("icd10", "idexists", "A new icd10 cannot already have an ID")).body(null);
        }
        Icd10 result = icd10Service.save(icd10);
        return ResponseEntity.created(new URI("/api/icd-10-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("icd10", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /icd-10-s : Updates an existing icd10.
     *
     * @param icd10 the icd10 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated icd10,
     * or with status 400 (Bad Request) if the icd10 is not valid,
     * or with status 500 (Internal Server Error) if the icd10 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/icd-10-s")
    @Timed
    public ResponseEntity<Icd10> updateIcd10(@RequestBody Icd10 icd10) throws URISyntaxException {
        log.debug("REST request to update Icd10 : {}", icd10);
        if (icd10.getId() == null) {
            return createIcd10(icd10);
        }
        Icd10 result = icd10Service.save(icd10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("icd10", icd10.getId().toString()))
            .body(result);
    }

    /**
     * GET  /icd-10-s : get all the icd10S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of icd10S in body
     */
    @GetMapping("/icd-10-s")
    @Timed
    public List<Icd10> getAllIcd10S() {
        log.debug("REST request to get all Icd10S");
        return icd10Service.findAll();
    }

    /**
     * GET  /icd-10-s/:id : get the "id" icd10.
     *
     * @param id the id of the icd10 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the icd10, or with status 404 (Not Found)
     */
    @GetMapping("/icd-10-s/{id}")
    @Timed
    public ResponseEntity<Icd10> getIcd10(@PathVariable Long id) {
        log.debug("REST request to get Icd10 : {}", id);
        Icd10 icd10 = icd10Service.findOne(id);
        return Optional.ofNullable(icd10)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /icd-10-s/:id : delete the "id" icd10.
     *
     * @param id the id of the icd10 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/icd-10-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteIcd10(@PathVariable Long id) {
        log.debug("REST request to delete Icd10 : {}", id);
        icd10Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("icd10", id.toString())).build();
    }

    /**
     * SEARCH  /_search/icd-10-s?query=:query : search for the icd10 corresponding
     * to the query.
     *
     * @param query the query of the icd10 search 
     * @return the result of the search
     */
    @GetMapping("/_search/icd-10-s")
    @Timed
    public List<Icd10> searchIcd10S(@RequestParam String query) {
        log.debug("REST request to search Icd10S for query {}", query);
        return icd10Service.search(query);
    }


}
