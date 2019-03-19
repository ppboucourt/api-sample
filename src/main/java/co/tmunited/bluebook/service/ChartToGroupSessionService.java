package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToGroupSession;

import java.util.List;

/**
 * Service Interface for managing ChartToGroupSession.
 */
public interface ChartToGroupSessionService {

    /**
     * Save a chartToGroupSession.
     *
     * @param chartToGroupSession the entity to save
     * @return the persisted entity
     */
    ChartToGroupSession save(ChartToGroupSession chartToGroupSession);

    /**
     *  Get all the chartToGroupSessions.
     *
     *  @return the list of entities
     */
    List<ChartToGroupSession> findAll();

    /**
     *  Get the "id" chartToGroupSession.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToGroupSession findOne(Long id);

    /**
     *  Delete the "id" chartToGroupSession.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToGroupSession corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ChartToGroupSession> search(String query);

}
