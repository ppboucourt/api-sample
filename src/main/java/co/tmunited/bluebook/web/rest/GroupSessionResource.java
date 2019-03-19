package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.enumeration.Progress;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.GroupSession;
import co.tmunited.bluebook.service.GroupSessionService;
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
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GroupSession.
 */
@RestController
@RequestMapping("/api")
public class GroupSessionResource {

    private final Logger log = LoggerFactory.getLogger(GroupSessionResource.class);

    @Inject
    private GroupSessionService groupSessionService;

    /**
     * POST  /group-sessions : Create a new groupSession.
     *
     * @param groupSession the groupSession to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupSession, or with status 400 (Bad Request) if the groupSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-sessions")
    @Timed
    public ResponseEntity<GroupSession> createGroupSession(@Valid @RequestBody GroupSession groupSession) throws URISyntaxException {
        log.debug("REST request to save GroupSession : {}", groupSession);
        if (groupSession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupSession", "idexists", "A new groupSession cannot already have an ID")).body(null);
        }
        GroupSession result = groupSessionService.save(groupSession);
        return ResponseEntity.created(new URI("/api/group-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupSession", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-sessions : Updates an existing groupSession.
     *
     * @param groupSession the groupSession to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupSession,
     * or with status 400 (Bad Request) if the groupSession is not valid,
     * or with status 500 (Internal Server Error) if the groupSession couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-sessions")
    @Timed
    public ResponseEntity<GroupSession> updateGroupSession(@Valid @RequestBody GroupSession groupSession) throws URISyntaxException {
        log.debug("REST request to update GroupSession : {}", groupSession);
        if (groupSession.getId() == null) {
            return createGroupSession(groupSession);
        }
        GroupSession result = groupSessionService.save(groupSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupSession", groupSession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-sessions : get all the groupSessions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessions in body
     */
    @GetMapping("/group-sessions")
    @Timed
    public List<GroupSession> getAllGroupSessions() {
        log.debug("REST request to get all GroupSessions");
        return groupSessionService.findAll();
    }

    /**
     * GET  /group-sessions/:id : get the "id" groupSession.
     *
     * @param id the id of the groupSession to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupSession, or with status 404 (Not Found)
     */
    @GetMapping("/group-sessions/{id}")
    @Timed
    public ResponseEntity<GroupSession> getGroupSession(@PathVariable Long id) {
        log.debug("REST request to get GroupSession : {}", id);
        GroupSession groupSession = groupSessionService.findOne(id);
        return Optional.ofNullable(groupSession)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-sessions/:id : delete the "id" groupSession.
     *
     * @param id the id of the groupSession to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-sessions/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupSession(@PathVariable Long id) {
        log.debug("REST request to delete GroupSession : {}", id);
        groupSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupSession", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-sessions?query=:query : search for the groupSession corresponding
     * to the query.
     *
     * @param query the query of the groupSession search
     * @return the result of the search
     */
    @GetMapping("/_search/group-sessions")
    @Timed
    public List<GroupSession> searchGroupSessions(@RequestParam String query) {
        log.debug("REST request to search GroupSessions for query {}", query);
        return groupSessionService.search(query);
    }

    /**
     * GET  /group-sessions : get all the groupSessions filtering by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessions in body
     */
    @GetMapping("/group-sessions-facility/{id}")
    @Timed
    public List<GroupSession> getAllGroupSessionsByFacility(@PathVariable Long id) {
        log.debug("REST request to get all GroupSessions");
        return groupSessionService.findAllByFacility(id);
    }

    /**
     * GET  /group-sessions : get all the groupSessions filtering by facility and by day of the week.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessions in body
     */
    @GetMapping("/group-sessions-today/{id}")
    @Timed
    public List<GroupSession> getAllGroupSessionsByDayOfWeekProgress(@PathVariable Long id) {
        log.debug("REST request to get all GroupSessions");
        return groupSessionService.findAllByFacilityTodayDayOfWeek(id);
    }

    /**
     * GET  /group-sessions : get all the groupSessions filtering by facility and by day of the week.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessions in body
     */
    @GetMapping("/group-sessions-day/{id}/{date}")
    @Timed
    public List<GroupSession> getAllGroupSessionsByDayOfWeek(@PathVariable Long id, @PathVariable LocalDate date) {
        log.debug("REST request to get all getAllGroupSessionsByDayOfWeek");
        return groupSessionService.findAllByFacilityAndDay(id, date);
    }

    /**
     * GET  /group-sessions : get all the groupSessions filtering by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupSessions in body
     */
    @GetMapping("/group-sessions-facility-end/{id}")
    @Timed
    public List<GroupSession> getAllGroupSessionsEndByFacility(@PathVariable Long id) {
        log.debug("REST request to get all GroupSessions");
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime yesterday = now.minusDays(1);

        return groupSessionService.findAllByFacilityDEnd(id, yesterday);
    }
}
