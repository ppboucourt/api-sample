package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.vo.ChartToLevelCareVO;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ChartToLevelCare;
import co.tmunited.bluebook.service.ChartToLevelCareService;
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
 * REST controller for managing ChartToLevelCare.
 */
@RestController
@RequestMapping("/api")
public class ChartToLevelCareResource {

    private final Logger log = LoggerFactory.getLogger(ChartToLevelCareResource.class);

    @Inject
    private ChartToLevelCareService chartToLevelCareService;

    /**
     * POST  /chart-to-level-cares : Create a new chartToLevelCare.
     *
     * @param chartToLevelCare the chartToLevelCare to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToLevelCare, or with status 400 (Bad Request) if the chartToLevelCare has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-level-cares")
    @Timed
    public ResponseEntity<ChartToLevelCare> createChartToLevelCare(@RequestBody ChartToLevelCare chartToLevelCare) throws URISyntaxException {
        log.debug("REST request to save ChartToLevelCare : {}", chartToLevelCare);
        if (chartToLevelCare.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToLevelCare", "idexists", "A new chartToLevelCare cannot already have an ID")).body(null);
        }
        ChartToLevelCare result = chartToLevelCareService.save(chartToLevelCare);
        return ResponseEntity.created(new URI("/api/chart-to-level-cares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToLevelCare", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-level-cares : Updates an existing chartToLevelCare.
     *
     * @param chartToLevelCare the chartToLevelCare to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToLevelCare,
     * or with status 400 (Bad Request) if the chartToLevelCare is not valid,
     * or with status 500 (Internal Server Error) if the chartToLevelCare couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-level-cares")
    @Timed
    public ResponseEntity<ChartToLevelCare> updateChartToLevelCare(@RequestBody ChartToLevelCare chartToLevelCare) throws URISyntaxException {
        log.debug("REST request to update ChartToLevelCare : {}", chartToLevelCare);
        if (chartToLevelCare.getId() == null) {
            return createChartToLevelCare(chartToLevelCare);
        }
        ChartToLevelCare result = chartToLevelCareService.save(chartToLevelCare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToLevelCare", chartToLevelCare.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-level-cares : get all the chartToLevelCares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToLevelCares in body
     */
    @GetMapping("/chart-to-level-cares")
    @Timed
    public List<ChartToLevelCare> getAllChartToLevelCares() {
        log.debug("REST request to get all ChartToLevelCares");
        return chartToLevelCareService.findAll();
    }

    /**
     * GET  /chart-to-level-cares/:id : get the "id" chartToLevelCare.
     *
     * @param id the id of the chartToLevelCare to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToLevelCare, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-level-cares/{id}")
    @Timed
    public ResponseEntity<ChartToLevelCare> getChartToLevelCare(@PathVariable Long id) {
        log.debug("REST request to get ChartToLevelCare : {}", id);
        ChartToLevelCare chartToLevelCare = chartToLevelCareService.findOne(id);
        return Optional.ofNullable(chartToLevelCare)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-level-cares/:id : delete the "id" chartToLevelCare.
     *
     * @param id the id of the chartToLevelCare to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-level-cares/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToLevelCare(@PathVariable Long id) {
        log.debug("REST request to delete ChartToLevelCare : {}", id);
        chartToLevelCareService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToLevelCare", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-level-cares?query=:query : search for the chartToLevelCare corresponding
     * to the query.
     *
     * @param query the query of the chartToLevelCare search
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-level-cares")
    @Timed
    public List<ChartToLevelCare> searchChartToLevelCares(@RequestParam String query) {
        log.debug("REST request to search ChartToLevelCares for query {}", query);
        return chartToLevelCareService.search(query);
    }

    /**
     * Search all levelCare assigned to a chart
     *
     * @param id
     * @return
     */
    @GetMapping("/chart-to-level-cares/chart/{id}")
    public List<ChartToLevelCareVO> findLOCsByChart(@PathVariable Long id) {
        log.info("REST request to bring all LOC by Chart {}", id);
        return chartToLevelCareService.findByChart(id) ;
    }

    /**
     * Search all levelCare assigned to a chart
     *
     * @param id
     * @return
     */
    @GetMapping("/chart-to-level-cares/last-by-chart/{id}")
    public ResponseEntity<ChartToLevelCare> findLastLOCsByChart(@PathVariable Long id) {
        log.info("REST request to bring the last LOC by Chart {}", id);
        ChartToLevelCare chartToLevelCare = chartToLevelCareService.findLastByChart(id);

        return Optional.ofNullable(chartToLevelCare)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
