package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.UrgentIssues;
import co.tmunited.bluebook.service.UrgentIssuesService;
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
 * REST controller for managing UrgentIssues.
 */
@RestController
@RequestMapping("/api")
public class UrgentIssuesResource {

    private final Logger log = LoggerFactory.getLogger(UrgentIssuesResource.class);

    @Inject
    private UrgentIssuesService urgentIssuesService;

    /**
     * POST  /urgent-issues : Create a new urgentIssues.
     *
     * @param urgentIssues the urgentIssues to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urgentIssues, or with status 400 (Bad Request) if the urgentIssues has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urgent-issues")
    @Timed
    public ResponseEntity<UrgentIssues> createUrgentIssues(@RequestBody UrgentIssues urgentIssues) throws URISyntaxException {
        log.debug("REST request to save UrgentIssues : {}", urgentIssues);
        if (urgentIssues.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("urgentIssues", "idexists", "A new urgentIssues cannot already have an ID")).body(null);
        }
        UrgentIssues result = urgentIssuesService.save(urgentIssues);
        return ResponseEntity.created(new URI("/api/urgent-issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("urgentIssues", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urgent-issues : Updates an existing urgentIssues.
     *
     * @param urgentIssues the urgentIssues to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urgentIssues,
     * or with status 400 (Bad Request) if the urgentIssues is not valid,
     * or with status 500 (Internal Server Error) if the urgentIssues couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urgent-issues")
    @Timed
    public ResponseEntity<UrgentIssues> updateUrgentIssues(@RequestBody UrgentIssues urgentIssues) throws URISyntaxException {
        log.debug("REST request to update UrgentIssues : {}", urgentIssues);
        if (urgentIssues.getId() == null) {
            return createUrgentIssues(urgentIssues);
        }
        UrgentIssues result = urgentIssuesService.save(urgentIssues);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("urgentIssues", urgentIssues.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urgent-issues : get all the urgentIssues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urgentIssues in body
     */
    @GetMapping("/urgent-issues")
    @Timed
    public List<UrgentIssues> getAllUrgentIssues() {
        log.debug("REST request to get all UrgentIssues");
        return urgentIssuesService.findAll();
    }

    /**
     * GET  /urgent-issues/:id : get the "id" urgentIssues.
     *
     * @param id the id of the urgentIssues to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urgentIssues, or with status 404 (Not Found)
     */
    @GetMapping("/urgent-issues/{id}")
    @Timed
    public ResponseEntity<UrgentIssues> getUrgentIssues(@PathVariable Long id) {
        log.debug("REST request to get UrgentIssues : {}", id);
        UrgentIssues urgentIssues = urgentIssuesService.findOne(id);
        return Optional.ofNullable(urgentIssues)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /urgent-issues/:id : delete the "id" urgentIssues.
     *
     * @param id the id of the urgentIssues to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urgent-issues/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrgentIssues(@PathVariable Long id) {
        log.debug("REST request to delete UrgentIssues : {}", id);
        urgentIssuesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("urgentIssues", id.toString())).build();
    }

    /**
     * SEARCH  /_search/urgent-issues?query=:query : search for the urgentIssues corresponding
     * to the query.
     *
     * @param query the query of the urgentIssues search
     * @return the result of the search
     */
    @GetMapping("/_search/urgent-issues")
    @Timed
    public List<UrgentIssues> searchUrgentIssues(@RequestParam String query) {
        log.debug("REST request to search UrgentIssues for query {}", query);
        return urgentIssuesService.search(query);
    }

    /**
     * GET  /urgent-issues : get all the urgentIssues by chart.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urgentIssues in body
     */
    @GetMapping("/urgent-issues/chart/{id}")
    @Timed
    public List<UrgentIssues> getAllUrgentIssuesByChart(@PathVariable Long id) {
        log.debug("REST request to get all UrgentIssues filtered by Chart", id);
        return urgentIssuesService.findAllByChart(id);
    }

    /**
     * GET  /urgent-issues : get all the urgentIssues by chart and employee.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of urgentIssues in body
     */
    @GetMapping("/urgent-issues/chart/{chartId}/{empId}")
    @Timed
    public List<UrgentIssues> getAllUrgentIssuesByChart(@PathVariable Long chartId, @PathVariable Long empId) {
        log.debug("REST request to get all UrgentIssues filtered by Chart", chartId + empId);
        return urgentIssuesService.findAllByChartAndEmployee(chartId, empId);
    }


}
