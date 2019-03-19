package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientMedication;
import co.tmunited.bluebook.domain.PatientMedicationPres;
import co.tmunited.bluebook.domain.PatientMedicationTake;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientMedicationDetailsVO;
import co.tmunited.bluebook.domain.vo.PatientMedicationVO;
import co.tmunited.bluebook.web.rest.util.FaxWrapper;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing PatientMedication.
 */
public interface PatientMedicationService {

    /**
     * Save a patientMedication.
     *
     * @param patientMedication the entity to save
     * @return the persisted entity
     */
    PatientMedication save(PatientMedication patientMedication);

    /**
     *  Get all the patientMedications.
     *
     *  @return the list of entities
     */
    List<PatientMedication> findAll();

    /**
     *  Get the "id" patientMedication.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientMedication findOne(Long id);

    /**
     *  Delete the "id" patientMedication.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientMedication corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientMedication> search(String query);

    /**
     * Get all medication for a Chart
     *
     * @param id
     * @return
     */
    List<PatientMedication> findAllByChart(Long id);

    /**
     * Schedule medication prescription
     *
     * @param patientMedication
     * @return
     */
    PatientMedication schedulePatientMedication(PatientMedication patientMedication);

    List<PatientMedicationTake> findAllMedicationsByDate(Long id, LocalDate date);

    void take(CollectedBody collectedBody);

    List<PatientMedicationPres> findAllMedicationsAsNeededByDate(long id);

    void addPatientMedicationTakeAsNeeded(long id, ZonedDateTime date);

    /**
     * Get unsigned medications from user with role PHYSICIAN
     *
     * @return
     */
    List<PatientMedication> getUnsignedMedications(Long id);

    List<PatientMedication> getAllUnsignedMedications(Long id);

    /**
     * Sign physician medications
     *
     * @param collectedBody
     */
    void signMedications(CollectedBody collectedBody);


    PatientMedication findPatientMedicationByTake(@Param("id") Long id);

    String generatePrescriptionPDF(FaxWrapper faxWrapper) throws Exception;

    PatientMedication findPatientMedicationByPress(@Param("id") Long id);

    List<PatientMedicationVO> findAllByChartVO(Long id);

    PatientMedication cancelPatientMedication(@Param("id") Long id);

    PatientMedicationDetailsVO findPatientMedicationDetailsByPress(Long id);

    Boolean sendFax(FaxWrapper faxWrapper);
}
