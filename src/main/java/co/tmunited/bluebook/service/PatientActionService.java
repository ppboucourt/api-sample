package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientAction;
import co.tmunited.bluebook.domain.PatientActionPre;
import co.tmunited.bluebook.domain.PatientActionTake;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientActionTakeVO;
import co.tmunited.bluebook.domain.vo.PatientActionVO;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing PatientAction.
 */
public interface PatientActionService {

    /**
     * Save a patientAction.
     *
     * @param patientAction the entity to save
     * @return the persisted entity
     */
    PatientAction save(PatientAction patientAction);

    /**
     *  Get all the patientActions.
     *
     *  @return the list of entities
     */
    List<PatientAction> findAll();

    /**
     *  Get the "id" patientAction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientAction findOne(Long id);

    /**
     *  Delete the "id" patientAction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientAction corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientAction> search(String query);

    /**
     * Get all medication for a Chart
     *
     * @param id
     * @return
     */
    List<PatientAction> findAllByChart(Long id);

    /**
     * Schedule medication prescription
     *
     * @param patientAction
     * @return
     */
    PatientAction schedulePatientAction(PatientAction patientAction);

    List<PatientActionTake> findAllActionsByDate(Long id, LocalDate date);

    void take(CollectedBody collectedBody);

    List<PatientActionPre> findAllActionsAsNeededByDate(long id);

    void addPatientActionTakeAsNeeded(long id, ZonedDateTime date);

    /**
     * Get unsigned actions from user with role PHYSICIAN
     *
     * @return
     */
    List<PatientAction> getUnsignedActions(Long id);

    List<PatientAction> getAllUnsignedActions(Long id);

    /**
     * Sign physician actions
     *
     * @param collectedBody
     */
    void signActions(CollectedBody collectedBody);

    List<PatientActionVO> getAllPatientActionByChartVO(Long chartId);

    PatientAction cancelPatientAction(Long id);

    List<PatientActionTakeVO> findAllActionsByDateVO(Long id, LocalDate date);
}
