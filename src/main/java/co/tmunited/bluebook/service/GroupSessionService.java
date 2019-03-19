package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.GroupSession;
import co.tmunited.bluebook.domain.enumeration.Progress;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Service Interface for managing GroupSession.
 */
public interface GroupSessionService {

    /**
     * Save a groupSession.
     *
     * @param groupSession the entity to save
     * @return the persisted entity
     */
    GroupSession save(GroupSession groupSession);

    /**
     *  Get all the groupSessions.
     *
     *  @return the list of entities
     */
    List<GroupSession> findAll();

    /**
     *  Get the "id" groupSession.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GroupSession findOne(Long id);

    /**
     *  Delete the "id" groupSession.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the groupSession corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<GroupSession> search(String query);

    /**
     *  Get all the groupSessions filtering by facility.
     *
     *  @return the list of entities
     */
    List<GroupSession> findAllByFacility(Long id);

    /**
     * Get all the groupSessions from this day of week and filtered by facility
     *
     * @param id facility
     *
     * @return the list of groupSessions
     */

    List<GroupSession> findAllByFacilityTodayDayOfWeek(Long id);

    List<GroupSession> findAllByFacilityAndDay(Long id, LocalDate date);

    List<GroupSession> findAllByFacilityDEnd(Long id, ZonedDateTime end);

    List<GroupSession> findAllByFacilityAndDayForReport(Long id, LocalDate date);
}
