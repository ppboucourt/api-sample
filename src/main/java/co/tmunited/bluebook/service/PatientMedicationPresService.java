package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientMedicationPres;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing PatientMedicationPres.
 */
public interface PatientMedicationPresService {

    /**
     * Save a patientMedicationPres.
     *
     * @param patientMedicationPres the entity to save
     * @return the persisted entity
     */
    PatientMedicationPres save(PatientMedicationPres patientMedicationPres);

    /**
     * Get all the patientMedicationPres.
     *
     * @return the list of entities
     */
    List<PatientMedicationPres> findAll(Long id);

    /**
     * Get the "id" patientMedicationPres.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PatientMedicationPres findOne(Long id);

    /**
     * Delete the "id" patientMedicationPres.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientMedicationPres corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<PatientMedicationPres> search(String query);


    /**
     * Get all chart medication in a date
     * @param chartId
     * @return
     */
    List<PatientMedicationPres> findAllMedicationByChartAndDate(Long chartId);


}
