package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.EvaluationSignature;
import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;

import java.util.List;

/**
 * Service Interface for managing EvaluationSignature.
 */
public interface EvaluationSignatureService {

    /**
     * Save a evaluationSignature.
     *
     * @param evaluationSignature the entity to save
     * @return the persisted entity
     */
    EvaluationSignature save(EvaluationSignature evaluationSignature, String ip);

    /**
     * Get all the evaluationSignatures.
     *
     * @return the list of entities
     */
    List<EvaluationSignature> findAll();

    /**
     * Get the "id" evaluationSignature.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EvaluationSignature findOne(Long id);

    /**
     * Delete the "id" evaluationSignature.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationSignature corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<EvaluationSignature> search(String query);


    /**
     * Get the "id" Evaluation.
     *
     * @param id the id of the entity
     * @return the List
     **/
    List<EvaluationSignature> findByEvaluationId(Long id);

    EvaluationSignatureVO findByEvaluation(Long id);
}
