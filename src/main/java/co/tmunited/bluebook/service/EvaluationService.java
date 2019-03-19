package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.dto.FileDTO;

import java.util.List;

/**
 * Service Interface for managing Evaluation.
 */
public interface EvaluationService {

    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save
     * @return the persisted entity
     */
    Evaluation save(Evaluation evaluation);

    /**
     *  Get all the evaluations.
     *
     *  @return the list of entities
     */
    List<Evaluation> findAll();

    /**
     *  Get the "id" evaluation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Evaluation findOne(Long id);

    /**
     *  Delete the "id" evaluation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluation corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Evaluation> search(String query);

    List<Evaluation> findAllByPatientProcess(Long ppId);

    List<FormVO> findAllByPatientProcessAndChart(Long chId, Long ppId);

    void assignForms(CollectedBody collectedBody);

    File attachEvaluationFile(FileDTO fileDTO);
}
