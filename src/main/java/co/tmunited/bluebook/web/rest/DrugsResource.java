package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Drugs;
import co.tmunited.bluebook.service.DrugsService;
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
 * REST controller for managing Drugs.
 */
@RestController
@RequestMapping("/api")
public class DrugsResource {

    private final Logger log = LoggerFactory.getLogger(DrugsResource.class);
        
    @Inject
    private DrugsService drugsService;

    /**
     * POST  /drugs : Create a new drugs.
     *
     * @param drugs the drugs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugs, or with status 400 (Bad Request) if the drugs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drugs")
    @Timed
    public ResponseEntity<Drugs> createDrugs(@Valid @RequestBody Drugs drugs) throws URISyntaxException {
        log.debug("REST request to save Drugs : {}", drugs);
        if (drugs.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drugs", "idexists", "A new drugs cannot already have an ID")).body(null);
        }
        Drugs result = drugsService.save(drugs);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drugs", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drugs.
     *
     * @param drugs the drugs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugs,
     * or with status 400 (Bad Request) if the drugs is not valid,
     * or with status 500 (Internal Server Error) if the drugs couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drugs")
    @Timed
    public ResponseEntity<Drugs> updateDrugs(@Valid @RequestBody Drugs drugs) throws URISyntaxException {
        log.debug("REST request to update Drugs : {}", drugs);
        if (drugs.getId() == null) {
            return createDrugs(drugs);
        }
        Drugs result = drugsService.save(drugs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drugs", drugs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @GetMapping("/drugs")
    @Timed
    public List<Drugs> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugsService.findAll();
    }

    /**
     * GET  /drugs/:id : get the "id" drugs.
     *
     * @param id the id of the drugs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugs, or with status 404 (Not Found)
     */
    @GetMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Drugs> getDrugs(@PathVariable Long id) {
        log.debug("REST request to get Drugs : {}", id);
        Drugs drugs = drugsService.findOne(id);
        return Optional.ofNullable(drugs)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drugs.
     *
     * @param id the id of the drugs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrugs(@PathVariable Long id) {
        log.debug("REST request to delete Drugs : {}", id);
        drugsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drugs", id.toString())).build();
    }

    /**
     * SEARCH  /_search/drugs?query=:query : search for the drugs corresponding
     * to the query.
     *
     * @param query the query of the drugs search 
     * @return the result of the search
     */
    @GetMapping("/_search/drugs")
    @Timed
    public List<Drugs> searchDrugs(@RequestParam String query) {
        log.debug("REST request to search Drugs for query {}", query);
        return drugsService.search(query);
    }


}
