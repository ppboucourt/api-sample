package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.EvaluationItems;

import java.util.List;

/**
 * Service Interface for managing EvaluationItems.
 */
public interface EvaluationItemsService {

    /**
     * Save a evaluationItems.
     *
     * @param evaluationItems the entity to save
     * @return the persisted entity
     */
    EvaluationItems save(EvaluationItems evaluationItems);

    /**
     *  Get all the evaluationItems.
     *  
     *  @return the list of entities
     */
    List<EvaluationItems> findAll();

    /**
     *  Get the "id" evaluationItems.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EvaluationItems findOne(Long id);

    /**
     *  Delete the "id" evaluationItems.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationItems corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EvaluationItems> search(String query);
}
