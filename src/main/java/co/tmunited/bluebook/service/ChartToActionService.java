package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToAction;

import java.util.List;

/**
 * Service Interface for managing ChartToAction.
 */
public interface ChartToActionService {

    /**
     * Save a chartToAction.
     *
     * @param chartToAction the entity to save
     * @return the persisted entity
     */
    ChartToAction save(ChartToAction chartToAction);

    /**
     *  Get all the chartToActions.
     *  
     *  @return the list of entities
     */
    List<ChartToAction> findAll();

    /**
     *  Get the "id" chartToAction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToAction findOne(Long id);

    /**
     *  Delete the "id" chartToAction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToAction corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ChartToAction> search(String query);
}
