package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.EvaluationContent;

import java.util.List;

/**
 * Service Interface for managing EvaluationContent.
 */
public interface EvaluationContentService {

    /**
     * Save a evaluationContent.
     *
     * @param evaluationContent the entity to save
     * @return the persisted entity
     */
    EvaluationContent save(EvaluationContent evaluationContent);

    /**
     *  Get all the evaluationContents.
     *  
     *  @return the list of entities
     */
    List<EvaluationContent> findAll();

    /**
     *  Get the "id" evaluationContent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EvaluationContent findOne(Long id);

    /**
     *  Delete the "id" evaluationContent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationContent corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EvaluationContent> search(String query);
}
