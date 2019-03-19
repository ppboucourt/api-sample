package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeEvaluation;

import java.util.List;

/**
 * Service Interface for managing TypeEvaluation.
 */
public interface TypeEvaluationService {

    /**
     * Save a typeEvaluation.
     *
     * @param typeEvaluation the entity to save
     * @return the persisted entity
     */
    TypeEvaluation save(TypeEvaluation typeEvaluation);

    /**
     *  Get all the typeEvaluations.
     *  
     *  @return the list of entities
     */
    List<TypeEvaluation> findAll();

    /**
     *  Get the "id" typeEvaluation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeEvaluation findOne(Long id);

    /**
     *  Delete the "id" typeEvaluation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeEvaluation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeEvaluation> search(String query);
}
