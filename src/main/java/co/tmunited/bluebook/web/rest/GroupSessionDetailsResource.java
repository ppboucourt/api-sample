package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import co.tmunited.bluebook.domain.enumeration.Progress;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;
import co.tmunited.bluebook.service.GroupSessionService;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.GroupSessionDetails;
import co.tmunited.bluebook.service.GroupSessionDetailsService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GroupSessionDetails.
 */
@RestController
@RequestMapping("/api")
public class GroupSessionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(GroupSessionDetailsResource.class);

    @Inject
    private GroupSessionDetailsService groupSessionDetailsService;

    @Inject
    private GroupSessionService groupSessionService;

    /**
     * POST  /group-session-details : Create a new groupSessionDetails.
     *
     * @param groupSessionDetails the groupSessionDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupSessionDetails, or with status 400 (Bad Request) if the groupSessionDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-session-details")
    @Timed
    public ResponseEntity<GroupSessionDetails> createGroupSessionDetails(@Valid @RequestBody GroupSessionDetails groupSessionDetails) throws URISyntaxException {
        log.debug("REST request to save GroupSessionDetails : {}", groupSessionDetails);

        if (groupSessionDetails.getGroupSession().getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupSessionDetails", "idnull", "A new groupSessionDetails cannot have GroupSession null")).body(null);
        }

        groupSessionDetails.setProgress(Progress.InProcess);
        GroupSessionDetails result = groupSessionDetailsService.save(groupSessionDetails);

        groupSessionDetails.getGroupSession().setStatus("INA");

        groupSessionService.save(groupSessionDetails.getGroupSession());

        return ResponseEntity.created(new URI("/api/group-session-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupSessionDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-session-details : Updates an existing groupSessionDetails.
     *
     * @param groupSessionDetails the groupSessionDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupSessionDetails,
     * or with status 400 (Bad Request) if the groupSessionDetails is not valid,
     * or with status 500 (Internal Server Error) if the groupSessionDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-session-details")
    @Timed
    public ResponseEntity<GroupSessionDetails> updateGroupSessionDetails(@Valid @RequestBody GroupSessionDetails groupSessionDetails, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update GroupSessionDetails : {}", groupSessionDetails);
        if (groupSessionDetails.getId() == null) {
            return createGroupSessionDetails(groupSessionDetails);
        }

        GroupSessionDetails result = groupSessionDetailsService.update(groupSessionDetails, getClientIpAddr(request));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupSessionDetails", groupSessionDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-session-details : get all the groupSessionDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessionDetails in body
     */
    @GetMapping("/group-session-details")
    @Timed
    public List<GroupSessionDetails> getAllGroupSessionDetails() {
        log.debug("REST request to get all GroupSessionDetails");
        return groupSessionDetailsService.findAll();
    }

    /**
     * GET  /group-session-details/:id : get the "id" groupSessionDetails.
     *
     * @param id the id of the groupSessionDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupSessionDetails, or with status 404 (Not Found)
     */
    @GetMapping("/group-session-details/{id}")
    @Timed
    public ResponseEntity<GroupSessionDetails> getGroupSessionDetails(@PathVariable Long id) {
        log.debug("REST request to get GroupSessionDetails : {}", id);
        GroupSessionDetails groupSessionDetails = groupSessionDetailsService.findOne(id);
        return Optional.ofNullable(groupSessionDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-session-details/:id : delete the "id" groupSessionDetails.
     *
     * @param id the id of the groupSessionDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-session-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupSessionDetails(@PathVariable Long id) {
        log.debug("REST request to delete GroupSessionDetails : {}", id);
        groupSessionDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupSessionDetails", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-session-details?query=:query : search for the groupSessionDetails corresponding
     * to the query.
     *
     * @param query the query of the groupSessionDetails search
     * @return the result of the search
     */
    @GetMapping("/_search/group-session-details")
    @Timed
    public List<GroupSessionDetails> searchGroupSessionDetails(@RequestParam String query) {
        log.debug("REST request to search GroupSessionDetails for query {}", query);
        return groupSessionDetailsService.search(query);
    }


    /**
     * SEARCH  /_search/group-session-details?query=:query : search for the groupSessionDetails corresponding
     * to the query.
     *
     * @param date the query of the groupSessionDetails search
     * @return the result of the search
     */
    @GetMapping("/group-session-details-by-date/{id}/{date}")
    @Timed
    public List<GroupSessionDetails> searchByDate(@PathVariable Long id, @PathVariable Date date) {
        log.debug("REST request to search GroupSessionDetails for query {}", date);
        return groupSessionDetailsService.searchByDate(id, date);
    }


    /**
     * POST  /assign-charts-to-group-session-details : Assign Charts to GroupSessionDetails.
     *
     * @param collectedBody the groupSessionDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupSessionDetails, or with status 400 (Bad Request) if the groupSessionDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assign-charts-to-group-session-details")
    @Timed
    public ResponseEntity<GroupSessionDetails> assignChartsToGroupSessionDetails(@Valid @RequestBody CollectedBody collectedBody) throws URISyntaxException {
        log.debug("REST request to assignChartsToGroupSessionDetail : {}", collectedBody.toString());

        if (collectedBody.getOwnerId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupSessionDetails", "idnull", "A new groupSessionDetails cannot have GroupSession null")).body(null);
        }

        GroupSessionDetails groupSessionDetails = groupSessionDetailsService.assignChartsToGroupSessionDetails(collectedBody.getOwnerId(), collectedBody.getIds() );

        return new ResponseEntity<>(
            groupSessionDetails,
            HttpStatus.OK);
    }



    /**
     *   /group-session-details-charts
     * to the query.
     *
     * @param id the query of the groupSessionDetails search
     * @return the result of the search
     */
    @GetMapping("/group-session-details-list-charts-vo/{id}")
    @Timed
    public List<GroupSessionDetailsChartVO> groupSessionDetailsCharts(@PathVariable Long id) {
        log.debug("REST request to search GroupSessionDetailsChart for id {}", id);
        return groupSessionDetailsService.findChartList(id);
    }


    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        log.debug("Update GroupSessionDetails using IP: {}", ip);
        return ip;
    }

}
