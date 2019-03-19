package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import co.tmunited.bluebook.service.GroupSessionDetailsChartService;
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
 * REST controller for managing GroupSessionDetailsChart.
 */
@RestController
@RequestMapping("/api")
public class GroupSessionDetailsChartResource {

    private final Logger log = LoggerFactory.getLogger(GroupSessionDetailsChartResource.class);

    @Inject
    private GroupSessionDetailsChartService groupSessionDetailsChartService;

    /**
     * POST  /group-session-details-charts : Create a new groupSessionDetailsChart.
     *
     * @param groupSessionDetailsChart the groupSessionDetailsChart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupSessionDetailsChart, or with status 400 (Bad Request) if the groupSessionDetailsChart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-session-details-charts")
    @Timed
    public ResponseEntity<GroupSessionDetailsChart> createGroupSessionDetailsChart(@Valid @RequestBody GroupSessionDetailsChart groupSessionDetailsChart) throws URISyntaxException {
        log.debug("REST request to save GroupSessionDetailsChart : {}", groupSessionDetailsChart);
        if (groupSessionDetailsChart.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupSessionDetailsChart", "idexists", "A new groupSessionDetailsChart cannot already have an ID")).body(null);
        }
        GroupSessionDetailsChart result = groupSessionDetailsChartService.save(groupSessionDetailsChart);
        return ResponseEntity.created(new URI("/api/group-session-details-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupSessionDetailsChart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-session-details-charts : Updates an existing groupSessionDetailsChart.
     *
     * @param groupSessionDetailsChart the groupSessionDetailsChart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupSessionDetailsChart,
     * or with status 400 (Bad Request) if the groupSessionDetailsChart is not valid,
     * or with status 500 (Internal Server Error) if the groupSessionDetailsChart couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-session-details-charts")
    @Timed
    public ResponseEntity<GroupSessionDetailsChart> updateGroupSessionDetailsChart(@Valid @RequestBody GroupSessionDetailsChart groupSessionDetailsChart) throws URISyntaxException {
        log.debug("REST request to update GroupSessionDetailsChart : {}", groupSessionDetailsChart);
        if (groupSessionDetailsChart.getId() == null) {
            return createGroupSessionDetailsChart(groupSessionDetailsChart);
        }
        GroupSessionDetailsChart result = groupSessionDetailsChartService.save(groupSessionDetailsChart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupSessionDetailsChart", groupSessionDetailsChart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-session-details-charts : get all the groupSessionDetailsCharts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessionDetailsCharts in body
     */
    @GetMapping("/group-session-details-charts")
    @Timed
    public List<GroupSessionDetailsChart> getAllGroupSessionDetailsCharts() {
        log.debug("REST request to get all GroupSessionDetailsCharts");
        return groupSessionDetailsChartService.findAll();
    }

    /**
     * GET  /group-session-details-charts-chart-id : get all the groupSessionDetailsCharts by ChartId.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessionDetailsCharts in body
     */
    @GetMapping("/group-session-details-charts-chart-id/{id}")
    @Timed
    public List<GroupSessionDetailsChartVO> getAllGroupSessionDetailsChartsByChartId(@PathVariable Long id) {
        log.debug("REST request to get all GroupSessionDetailsCharts by ChartID");
        return groupSessionDetailsChartService.findAllByDelStatusIsFalseAndChartId(id);
    }

    /**
     * GET  /group-session-details-charts/:id : get the "id" groupSessionDetailsChart.
     *
     * @param id the id of the groupSessionDetailsChart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupSessionDetailsChart, or with status 404 (Not Found)
     */
    @GetMapping("/group-session-details-charts/{id}")
    @Timed
    public ResponseEntity<GroupSessionDetailsChart> getGroupSessionDetailsChart(@PathVariable Long id) {
        log.debug("REST request to get GroupSessionDetailsChart : {}", id);
        GroupSessionDetailsChart groupSessionDetailsChart = groupSessionDetailsChartService.findOne(id);
        return Optional.ofNullable(groupSessionDetailsChart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-session-details-charts/:id : delete the "id" groupSessionDetailsChart.
     *
     * @param id the id of the groupSessionDetailsChart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-session-details-charts/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupSessionDetailsChart(@PathVariable Long id) {
        log.debug("REST request to delete GroupSessionDetailsChart : {}", id);
        groupSessionDetailsChartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupSessionDetailsChart", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-session-details-charts?query=:query : search for the groupSessionDetailsChart corresponding
     * to the query.
     *
     * @param query the query of the groupSessionDetailsChart search
     * @return the result of the search
     */
    @GetMapping("/_search/group-session-details-charts")
    @Timed
    public List<GroupSessionDetailsChart> searchGroupSessionDetailsCharts(@RequestParam String query) {
        log.debug("REST request to search GroupSessionDetailsCharts for query {}", query);
        return groupSessionDetailsChartService.search(query);
    }

    /**
     * Get the List of GroupSessionDetailsChart by GroupSession
     * @param gsId
     * @return List of GroupSessionDetailsChart
     */
    @GetMapping("/search-by-group-session/{gsId}")
    @Timed
    public List<GroupSessionDetailsChart> searchByGroupSession(@PathVariable Long gsId){
        return groupSessionDetailsChartService.findAllGroupSessionByPatientInCurrentFacility(gsId);
    }



    /**
     * GET  /group-session-details-chart-by-chart/:chartId : get the "id" groupSessionDetailsChart.
     *
     * @param chartId the id of the groupSessionDetailsChart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupSessionDetailsChart, or with status 404 (Not Found)
     */
    @GetMapping("/group-session-details-chart-by-chart/{id}")
    @Timed
    public ResponseEntity<List<GroupSessionDetailsChart>> getGroupSessionDetailsChartByChart(@PathVariable Long chartId) {
        log.debug("REST request to get getGroupSessionDetailsChartByChart : {}", chartId);
        List<GroupSessionDetailsChart> groupSessionDetailsCharts = groupSessionDetailsChartService.getGroupSessionDetailsChartByChart(chartId);
        return Optional.ofNullable(groupSessionDetailsCharts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
