package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientResult;
import co.tmunited.bluebook.domain.PatientResultExtended;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientResultVO;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing PatientResult.
 */
public interface PatientResultService {

    /**
     * Save a patientResult.
     *
     * @param patientResult the entity to save
     * @return the persisted entity
     */
    PatientResult save(PatientResult patientResult);

    /**
     * Get all the patientResults.
     *
     * @return the list of entities
     */
    List<PatientResult> findAll();

    /**
     * Get the "id" patientResult.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PatientResult findOne(Long id);

    /**
     * Delete the "id" patientResult.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientResult corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<PatientResult> search(String query);

    /**
     * Get all patient result by clinic beteewn days
     *
     * @param id
     * @return
     */
    List<PatientResult> findAllByClinic(Long id, ZonedDateTime starDate, ZonedDateTime endDate);

    List<PatientResult> findAllByClinic(Long id);

    List<PatientResultVO> findAllByPatientId(Long id);

    /**
     * Process HL7 patientResult files
     * @param patientResultExtended
     * @return
     */
    PatientResult processPatientResult(PatientResultExtended patientResultExtended);

    PatientResult findOneByAccessionNumber(String barcode);

    List<PatientResult> getMonthlyResultByClinic(Long id, String status);

    List<PatientResult> getUnassignedResultByClinic(Long id);

    void assign(CollectedBody collectedBody);

    List<PatientResult> getCriticalResultByClinic(Long id);

    List<PatientResult> getNonPerformTestByClinic(Long id);
}
