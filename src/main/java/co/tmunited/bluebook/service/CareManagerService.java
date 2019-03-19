package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.CareManager;

import java.util.List;

/**
 * Service Interface for managing CareManager.
 */
public interface CareManagerService {

    /**
     * Save a careManager.
     *
     * @param careManager the entity to save
     * @return the persisted entity
     */
    CareManager save(CareManager careManager);

    /**
     *  Get all the careManagers.
     *  
     *  @return the list of entities
     */
    List<CareManager> findAll();

    /**
     *  Get the "id" careManager.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CareManager findOne(Long id);

    /**
     *  Delete the "id" careManager.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the careManager corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CareManager> search(String query);
}
