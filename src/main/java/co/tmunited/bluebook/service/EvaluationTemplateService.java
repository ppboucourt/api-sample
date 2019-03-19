package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.EvaluationTemplate;
import co.tmunited.bluebook.domain.vo.FormVO;

import java.util.List;

/**
 * Service Interface for managing EvaluationTemplate.
 */
public interface EvaluationTemplateService {

    /**
     * Save a evaluationTemplate.
     *
     * @param evaluationTemplate the entity to save
     * @return the persisted entity
     */
    EvaluationTemplate save(EvaluationTemplate evaluationTemplate);

    /**
     *  Get all the evaluationTemplates.
     *
     *  @return the list of entities
     */
    List<EvaluationTemplate> findAll();

    /**
     *  Get the "id" evaluationTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EvaluationTemplate findOne(Long id);

    /**
     *  Delete the "id" evaluationTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<EvaluationTemplate> search(String query);

    List<FormVO> findAllByPatientProcess(Long ppId, Long facId);

    List<EvaluationTemplate> findAllByFacility(Long id);

    List<FormVO> findAllByPatientProcessLevelCareFacility(Long ppId, Long facId, Long lcId);
}
