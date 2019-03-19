package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Weight;

import java.util.List;

/**
 * Service Interface for managing Weight.
 */
public interface WeightService {

    /**
     * Save a weight.
     *
     * @param weight the entity to save
     * @return the persisted entity
     */
    Weight save(Weight weight);

    /**
     *  Get all the weights.
     *
     *  @return the list of entities
     */
    List<Weight> findAll();

    /**
     *  Get the "id" weight.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Weight findOne(Long id);

    /**
     *  Delete the "id" weight.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the weight corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Weight> search(String query);

    List<Weight> findByChart(Long id);
}
