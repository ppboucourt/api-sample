package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ChartToGroupSession;
import co.tmunited.bluebook.service.ChartToGroupSessionService;
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
 * REST controller for managing ChartToGroupSession.
 */
@RestController
@RequestMapping("/api")
public class ChartToGroupSessionResource {

    private final Logger log = LoggerFactory.getLogger(ChartToGroupSessionResource.class);

    @Inject
    private ChartToGroupSessionService chartToGroupSessionService;

    /**
     * POST  /chart-to-group-sessions : Create a new chartToGroupSession.
     *
     * @param chartToGroupSession the chartToGroupSession to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToGroupSession, or with status 400 (Bad Request) if the chartToGroupSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-group-sessions")
    @Timed
    public ResponseEntity<ChartToGroupSession> createChartToGroupSession(@RequestBody ChartToGroupSession chartToGroupSession) throws URISyntaxException {
        log.debug("REST request to save ChartToGroupSession : {}", chartToGroupSession);
        if (chartToGroupSession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToGroupSession", "idexists", "A new chartToGroupSession cannot already have an ID")).body(null);
        }
        ChartToGroupSession result = chartToGroupSessionService.save(chartToGroupSession);
        return ResponseEntity.created(new URI("/api/chart-to-group-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToGroupSession", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-group-sessions : Updates an existing chartToGroupSession.
     *
     * @param chartToGroupSession the chartToGroupSession to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToGroupSession,
     * or with status 400 (Bad Request) if the chartToGroupSession is not valid,
     * or with status 500 (Internal Server Error) if the chartToGroupSession couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-group-sessions")
    @Timed
    public ResponseEntity<ChartToGroupSession> updateChartToGroupSession(@RequestBody ChartToGroupSession chartToGroupSession) throws URISyntaxException {
        log.debug("REST request to update ChartToGroupSession : {}", chartToGroupSession);
        if (chartToGroupSession.getId() == null) {
            return createChartToGroupSession(chartToGroupSession);
        }
        ChartToGroupSession result = chartToGroupSessionService.save(chartToGroupSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToGroupSession", chartToGroupSession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-group-sessions : get all the chartToGroupSessions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToGroupSessions in body
     */
    @GetMapping("/chart-to-group-sessions")
    @Timed
    public List<ChartToGroupSession> getAllChartToGroupSessions() {
        log.debug("REST request to get all ChartToGroupSessions");
        return chartToGroupSessionService.findAll();
    }

    /**
     * GET  /chart-to-group-sessions/:id : get the "id" chartToGroupSession.
     *
     * @param id the id of the chartToGroupSession to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToGroupSession, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-group-sessions/{id}")
    @Timed
    public ResponseEntity<ChartToGroupSession> getChartToGroupSession(@PathVariable Long id) {
        log.debug("REST request to get ChartToGroupSession : {}", id);
        ChartToGroupSession chartToGroupSession = chartToGroupSessionService.findOne(id);
        return Optional.ofNullable(chartToGroupSession)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-group-sessions/:id : delete the "id" chartToGroupSession.
     *
     * @param id the id of the chartToGroupSession to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-group-sessions/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToGroupSession(@PathVariable Long id) {
        log.debug("REST request to delete ChartToGroupSession : {}", id);
        chartToGroupSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToGroupSession", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-group-sessions?query=:query : search for the chartToGroupSession corresponding
     * to the query.
     *
     * @param query the query of the chartToGroupSession search
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-group-sessions")
    @Timed
    public List<ChartToGroupSession> searchChartToGroupSessions(@RequestParam String query) {
        log.debug("REST request to search ChartToGroupSessions for query {}", query);
        return chartToGroupSessionService.search(query);
    }


}
