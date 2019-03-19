package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.enumeration.Progress;
import co.tmunited.bluebook.service.GroupSessionService;
import co.tmunited.bluebook.domain.GroupSession;
import co.tmunited.bluebook.repository.GroupSessionRepository;
import co.tmunited.bluebook.repository.search.GroupSessionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GroupSession.
 */
@Service
@Transactional
public class GroupSessionServiceImpl implements GroupSessionService{

    private final Logger log = LoggerFactory.getLogger(GroupSessionServiceImpl.class);

    @Inject
    private GroupSessionRepository groupSessionRepository;

    @Inject
    private GroupSessionSearchRepository groupSessionSearchRepository;

    /**
     * Save a groupSession.
     *
     * @param groupSession the entity to save
     * @return the persisted entity
     */
    public GroupSession save(GroupSession groupSession) {
        log.debug("Request to save GroupSession : {}", groupSession);
        GroupSession result = groupSessionRepository.save(groupSession);
        groupSessionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the groupSessions.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSession> findAll() {
        log.debug("Request to get all GroupSessions");
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one groupSession by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GroupSession findOne(Long id) {
        log.debug("Request to get GroupSession : {}", id);
        GroupSession groupSession = groupSessionRepository.findOne(id);
        return groupSession;
    }

    /**
     *  Delete the  groupSession by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupSession : {}", id);
      GroupSession groupSession = groupSessionRepository.findOne(id);
      groupSession.setDelStatus(true);
      GroupSession result = groupSessionRepository.save(groupSession);

      groupSessionSearchRepository.save(result);
    }

    /**
     * Search for the groupSession corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSession> search(String query) {
        log.debug("Request to search GroupSessions for query {}", query);
        return StreamSupport
            .stream(groupSessionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<GroupSession> findAllByFacility(Long id) {
        log.debug("Request to get all GroupSessions");
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityId(id);

        return result;
    }

    /**
     * Search the groupSession for the actual day of the week and by progress.
     *
     * @param id facility
     *
     * @return List of GroupSession
     */
    @Override
    public List<GroupSession> findAllByFacilityTodayDayOfWeek(Long id) {
        log.debug("Request to get all GroupSessions by Facility and the day of the Week");
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityId(id);

        LocalDate currentDate = LocalDate.now();
        Integer dayOfWeek = currentDate.getDayOfWeek().getValue() - 1;

        result.removeIf(x -> (x.getDayWeek().charAt(dayOfWeek) == '0') );

        return result;
    }


    /**
     * Search the groupSession for the parameter day..
     *
     * @param id facility,
     *
     * @param date to filter
     *
     * @return List of GroupSession
     */
    @Override
    public List<GroupSession> findAllByFacilityAndDay(Long id, LocalDate date) {
        log.debug("Request to get all GroupSessions by Facility and the day of the Week");
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityId(id);

        Integer dayOfWeek = date.getDayOfWeek().getValue() - 1;
        result.removeIf(x -> (x.getDayWeek().charAt(dayOfWeek) == '0') );

        return result;
    }

    @Override
    public List<GroupSession> findAllByFacilityDEnd(Long id, ZonedDateTime end) {
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityIdAndEndTimeLessThan(id, end);
        return result;
    }
    /**
     * Search the groupSession for the parameter day..
     *
     * @param id facility,
     *
     * @param date to filter
     *
     * @return List of GroupSession
     */


    @Override
    public List<GroupSession> findAllByFacilityAndDayForReport(Long id, LocalDate date) {
        log.debug("Request to get all GroupSessions by Facility and the day for Report");
        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityId(id);

        Integer dayOfWeek = date.getDayOfWeek().getValue() - 1;
        result.removeIf(x -> (x.getDayWeek().charAt(dayOfWeek) == '0') );

        return result;
    }
}
