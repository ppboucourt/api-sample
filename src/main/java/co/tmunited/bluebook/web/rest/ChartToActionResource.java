package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ChartToAction;
import co.tmunited.bluebook.service.ChartToActionService;
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
 * REST controller for managing ChartToAction.
 */
@RestController
@RequestMapping("/api")
public class ChartToActionResource {

    private final Logger log = LoggerFactory.getLogger(ChartToActionResource.class);
        
    @Inject
    private ChartToActionService chartToActionService;

    /**
     * POST  /chart-to-actions : Create a new chartToAction.
     *
     * @param chartToAction the chartToAction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToAction, or with status 400 (Bad Request) if the chartToAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-actions")
    @Timed
    public ResponseEntity<ChartToAction> createChartToAction(@RequestBody ChartToAction chartToAction) throws URISyntaxException {
        log.debug("REST request to save ChartToAction : {}", chartToAction);
        if (chartToAction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToAction", "idexists", "A new chartToAction cannot already have an ID")).body(null);
        }
        ChartToAction result = chartToActionService.save(chartToAction);
        return ResponseEntity.created(new URI("/api/chart-to-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToAction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-actions : Updates an existing chartToAction.
     *
     * @param chartToAction the chartToAction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToAction,
     * or with status 400 (Bad Request) if the chartToAction is not valid,
     * or with status 500 (Internal Server Error) if the chartToAction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-actions")
    @Timed
    public ResponseEntity<ChartToAction> updateChartToAction(@RequestBody ChartToAction chartToAction) throws URISyntaxException {
        log.debug("REST request to update ChartToAction : {}", chartToAction);
        if (chartToAction.getId() == null) {
            return createChartToAction(chartToAction);
        }
        ChartToAction result = chartToActionService.save(chartToAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToAction", chartToAction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-actions : get all the chartToActions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToActions in body
     */
    @GetMapping("/chart-to-actions")
    @Timed
    public List<ChartToAction> getAllChartToActions() {
        log.debug("REST request to get all ChartToActions");
        return chartToActionService.findAll();
    }

    /**
     * GET  /chart-to-actions/:id : get the "id" chartToAction.
     *
     * @param id the id of the chartToAction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToAction, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-actions/{id}")
    @Timed
    public ResponseEntity<ChartToAction> getChartToAction(@PathVariable Long id) {
        log.debug("REST request to get ChartToAction : {}", id);
        ChartToAction chartToAction = chartToActionService.findOne(id);
        return Optional.ofNullable(chartToAction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-actions/:id : delete the "id" chartToAction.
     *
     * @param id the id of the chartToAction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToAction(@PathVariable Long id) {
        log.debug("REST request to delete ChartToAction : {}", id);
        chartToActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToAction", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-actions?query=:query : search for the chartToAction corresponding
     * to the query.
     *
     * @param query the query of the chartToAction search 
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-actions")
    @Timed
    public List<ChartToAction> searchChartToActions(@RequestParam String query) {
        log.debug("REST request to search ChartToActions for query {}", query);
        return chartToActionService.search(query);
    }


}
