package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Corporation;

import java.util.List;

/**
 * Service Interface for managing Corporation.
 */
public interface CorporationService {

    /**
     * Save a corporation.
     *
     * @param corporation the entity to save
     * @return the persisted entity
     */
    Corporation save(Corporation corporation);

    /**
     *  Get all the corporations.
     *  
     *  @return the list of entities
     */
    List<Corporation> findAll();

    /**
     *  Get the "id" corporation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Corporation findOne(Long id);

    /**
     *  Delete the "id" corporation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the corporation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Corporation> search(String query);
}
