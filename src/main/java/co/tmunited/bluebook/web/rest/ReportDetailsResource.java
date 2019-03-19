package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ReportDetails;
import co.tmunited.bluebook.service.ReportDetailsService;
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
 * REST controller for managing ReportDetails.
 */
@RestController
@RequestMapping("/api")
public class ReportDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ReportDetailsResource.class);
        
    @Inject
    private ReportDetailsService reportDetailsService;

    /**
     * POST  /report-details : Create a new reportDetails.
     *
     * @param reportDetails the reportDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportDetails, or with status 400 (Bad Request) if the reportDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report-details")
    @Timed
    public ResponseEntity<ReportDetails> createReportDetails(@Valid @RequestBody ReportDetails reportDetails) throws URISyntaxException {
        log.debug("REST request to save ReportDetails : {}", reportDetails);
        if (reportDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reportDetails", "idexists", "A new reportDetails cannot already have an ID")).body(null);
        }
        ReportDetails result = reportDetailsService.save(reportDetails);
        return ResponseEntity.created(new URI("/api/report-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reportDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /report-details : Updates an existing reportDetails.
     *
     * @param reportDetails the reportDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportDetails,
     * or with status 400 (Bad Request) if the reportDetails is not valid,
     * or with status 500 (Internal Server Error) if the reportDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/report-details")
    @Timed
    public ResponseEntity<ReportDetails> updateReportDetails(@Valid @RequestBody ReportDetails reportDetails) throws URISyntaxException {
        log.debug("REST request to update ReportDetails : {}", reportDetails);
        if (reportDetails.getId() == null) {
            return createReportDetails(reportDetails);
        }
        ReportDetails result = reportDetailsService.save(reportDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reportDetails", reportDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /report-details : get all the reportDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reportDetails in body
     */
    @GetMapping("/report-details")
    @Timed
    public List<ReportDetails> getAllReportDetails() {
        log.debug("REST request to get all ReportDetails");
        return reportDetailsService.findAll();
    }

    /**
     * GET  /report-details/:id : get the "id" reportDetails.
     *
     * @param id the id of the reportDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportDetails, or with status 404 (Not Found)
     */
    @GetMapping("/report-details/{id}")
    @Timed
    public ResponseEntity<ReportDetails> getReportDetails(@PathVariable Long id) {
        log.debug("REST request to get ReportDetails : {}", id);
        ReportDetails reportDetails = reportDetailsService.findOne(id);
        return Optional.ofNullable(reportDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /report-details/:id : delete the "id" reportDetails.
     *
     * @param id the id of the reportDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/report-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteReportDetails(@PathVariable Long id) {
        log.debug("REST request to delete ReportDetails : {}", id);
        reportDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reportDetails", id.toString())).build();
    }

    /**
     * SEARCH  /_search/report-details?query=:query : search for the reportDetails corresponding
     * to the query.
     *
     * @param query the query of the reportDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/report-details")
    @Timed
    public List<ReportDetails> searchReportDetails(@RequestParam String query) {
        log.debug("REST request to search ReportDetails for query {}", query);
        return reportDetailsService.search(query);
    }


}
