package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.ChartCareTeam;
import co.tmunited.bluebook.domain.vo.ChartCareTeamVO;
import co.tmunited.bluebook.service.ChartCareTeamService;
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
 * REST controller for managing ChartCareTeam.
 */
@RestController
@RequestMapping("/api")
public class ChartCareTeamResource {

    private final Logger log = LoggerFactory.getLogger(ChartCareTeamResource.class);

    @Inject
    private ChartCareTeamService ChartCareTeamService;

    /**
     * POST  /chart-care-team : Create a new ChartCareTeam.
     *
     * @param ChartCareTeam the ChartCareTeam to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ChartCareTeam, or with status 400 (Bad Request) if the ChartCareTeam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-care-team")
    @Timed
    public ResponseEntity<ChartCareTeam> createChartCareTeam(@RequestBody ChartCareTeam ChartCareTeam) throws URISyntaxException {
        log.debug("REST request to save ChartCareTeam : {}", ChartCareTeam);
        if (ChartCareTeam.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ChartCareTeam", "idexists", "A new ChartCareTeam cannot already have an ID")).body(null);
        }
        ChartCareTeam result = ChartCareTeamService.save(ChartCareTeam);
        return ResponseEntity.created(new URI("/api/chart-care-team/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ChartCareTeam", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-care-team : Updates an existing ChartCareTeam.
     *
     * @param ChartCareTeam the ChartCareTeam to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ChartCareTeam,
     * or with status 400 (Bad Request) if the ChartCareTeam is not valid,
     * or with status 500 (Internal Server Error) if the ChartCareTeam couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-care-team")
    @Timed
    public ResponseEntity<ChartCareTeam> updateChartCareTeam(@RequestBody ChartCareTeam ChartCareTeam) throws URISyntaxException {
        log.debug("REST request to update ChartCareTeam : {}", ChartCareTeam);
        if (ChartCareTeam.getId() == null) {
            return createChartCareTeam(ChartCareTeam);
        }
        ChartCareTeam result = ChartCareTeamService.save(ChartCareTeam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ChartCareTeam", ChartCareTeam.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-care-team : get all the ChartCareTeams.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ChartCareTeams in body
     */
    @GetMapping("/chart-care-team")
    @Timed
    public List<ChartCareTeam> getAllChartCareTeams() {
        log.debug("REST request to get all ChartCareTeams");
        return ChartCareTeamService.findAll();
    }

    /**
     * GET  /chart-care-team/:id : get the "id" ChartCareTeam.
     *
     * @param id the id of the ChartCareTeam to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ChartCareTeam, or with status 404 (Not Found)
     */
    @GetMapping("/chart-care-team/{id}")
    @Timed
    public ResponseEntity<ChartCareTeam> getChartCareTeam(@PathVariable Long id) {
        log.debug("REST request to get ChartCareTeam : {}", id);
        ChartCareTeam ChartCareTeam = ChartCareTeamService.findOne(id);
        return Optional.ofNullable(ChartCareTeam)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /chart-care-team-by-chart/:id : get the "id" Chart.
     *
     * @param id the id of the Chart to retrieve
     * @return the List of ChartCareTeam
     */
    @GetMapping("/chart-care-team-by-chart/{id}")
    @Timed
    public List<ChartCareTeam> getChartCareTeamByChart(@PathVariable Long id) {
        log.debug("REST request to get ChartCareTeam by Chart: {}", id);
        return ChartCareTeamService.findChartCareTeamByChart(id);
    }


    /**
     * GET  /chart-care-team/vo-chart/:id : get the "id" Chart.
     *
     * @param id the id of the Chart to retrieve
     * @return the List of ChartCareTeam
     */
    @GetMapping("/chart-care-team/vo-chart/{id}")
    @Timed
    public List<ChartCareTeamVO> getChartCareTeamVOByChart(@PathVariable Long id) {
        log.debug("REST request to get ChartCareTeam by Chart: {}", id);
        return ChartCareTeamService.findChartCareTeamVOByChart(id);
    }


    /**
     * GET  /chart-care-team-by-employee/:id : get the "id" Employee.
     *
     * @param id the id of the Employee to retrieve
     * @return the List of ChartCareTeam
     */
    @GetMapping("/chart-care-team-by-employee/{id}")
    @Timed
    public List<ChartCareTeam> getChartCareTeamByEmployee(@PathVariable Long id) {
        log.debug("REST request to get ChartCareTeam by Employee: {}", id);
        return ChartCareTeamService.findChartCareTeamByEmployee(id);
    }


    /**
     * DELETE  /chart-care-team/:id : delete the "id" ChartCareTeam.
     *
     * @param id the id of the ChartCareTeam to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-care-team/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartCareTeam(@PathVariable Long id) {
        log.debug("REST request to delete ChartCareTeam : {}", id);
        ChartCareTeamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ChartCareTeam", id.toString())).build();
    }

//    /**
//     * SEARCH  /_search/chart-care-team?query=:query : search for the ChartCareTeam corresponding
//     * to the query.
//     *
//     * @param query the query of the ChartCareTeam search
//     * @return the result of the search
//     */
//    @GetMapping("/_search/chart-care-team")
//    @Timed
//    public List<ChartCareTeam> searchChartCareTeams(@RequestParam String query) {
//        log.debug("REST request to search ChartCareTeams for query {}", query);
//        return ChartCareTeamService.search(query);
//    }


}
