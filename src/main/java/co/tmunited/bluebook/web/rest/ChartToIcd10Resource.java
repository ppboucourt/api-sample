package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.ChartToIcd10;
import co.tmunited.bluebook.service.ChartToIcd10Service;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChartToIcd10.
 */
@RestController
@RequestMapping("/api")
public class ChartToIcd10Resource {

    private final Logger log = LoggerFactory.getLogger(ChartToIcd10Resource.class);

    @Inject
    private ChartToIcd10Service chartToIcd10Service;

    /**
     * POST  /chart-to-icd-10-s : Create a new chartToIcd10.
     *
     * @param chartToIcd10 the chartToIcd10 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToIcd10, or with status 400 (Bad Request) if the chartToIcd10 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-icd-10-s")
    @Timed
    public ResponseEntity<ChartToIcd10> createChartToIcd10(@RequestBody ChartToIcd10 chartToIcd10) throws URISyntaxException {
        log.debug("REST request to save ChartToIcd10 : {}", chartToIcd10);
        if (chartToIcd10.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToIcd10", "idexists", "A new chartToIcd10 cannot already have an ID")).body(null);
        }
        ChartToIcd10 result = chartToIcd10Service.save(chartToIcd10);
        return ResponseEntity.created(new URI("/api/chart-to-icd-10-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToIcd10", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-icd-10-s : Updates an existing chartToIcd10.
     *
     * @param chartToIcd10 the chartToIcd10 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToIcd10,
     * or with status 400 (Bad Request) if the chartToIcd10 is not valid,
     * or with status 500 (Internal Server Error) if the chartToIcd10 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-icd-10-s")
    @Timed
    public ResponseEntity<ChartToIcd10> updateChartToIcd10(@RequestBody ChartToIcd10 chartToIcd10) throws URISyntaxException {
        log.debug("REST request to update ChartToIcd10 : {}", chartToIcd10);
        if (chartToIcd10.getId() == null) {
            return createChartToIcd10(chartToIcd10);
        }
        ChartToIcd10 result = chartToIcd10Service.save(chartToIcd10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToIcd10", chartToIcd10.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-icd-10-s : get all the chartToIcd10S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToIcd10S in body
     */
    @GetMapping("/chart-to-icd-10-s")
    @Timed
    public List<ChartToIcd10> getAllChartToIcd10S() {
        log.debug("REST request to get all ChartToIcd10S");
        return chartToIcd10Service.findAll();
    }

    /**
     * GET  /chart-to-icd-10-s/:id : get the "id" chartToIcd10.
     *
     * @param id the id of the chartToIcd10 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToIcd10, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-icd-10-s/{id}")
    @Timed
    public ResponseEntity<ChartToIcd10> getChartToIcd10(@PathVariable Long id) {
        log.debug("REST request to get ChartToIcd10 : {}", id);
        ChartToIcd10 chartToIcd10 = chartToIcd10Service.findOne(id);
        return Optional.ofNullable(chartToIcd10)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-icd-10-s/:id : delete the "id" chartToIcd10.
     *
     * @param id the id of the chartToIcd10 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-icd-10-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToIcd10(@PathVariable Long id) {
        log.debug("REST request to delete ChartToIcd10 : {}", id);
        chartToIcd10Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToIcd10", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-icd-10-s?query=:query : search for the chartToIcd10 corresponding
     * to the query.
     *
     * @param query the query of the chartToIcd10 search
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-icd-10-s")
    @Timed
    public List<ChartToIcd10> searchChartToIcd10S(@RequestParam String query) {
        log.debug("REST request to search ChartToIcd10S for query {}", query);
        return chartToIcd10Service.search(query);
    }

    /**
     * GET  /problem-list : get all the problem List for a patient.
     *
     * @return List
     */
    @GetMapping("/chart-to-icd-10-s-by-patient/{id}")
    @Timed
    public List<ChartToIcd10> getAllProblemListByPatient(@PathVariable Long id) {
        log.debug("REST request to get all CareTeams");
        return chartToIcd10Service.findAllByPatient(id);
    }

    /**
     * GET  /problem-list : get all the active problem List for a patient.
     *
     * @return List
     */
    @GetMapping("/active-problem-list-by-patient/{id}")
    @Timed
    public List<ChartToIcd10> getActiveListByPatient(@PathVariable Long id) {
        return chartToIcd10Service.findAllActiveByPatient(id);
    }
}
