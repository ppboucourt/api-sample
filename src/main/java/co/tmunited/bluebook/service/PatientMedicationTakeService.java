package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientMedicationTake;

import java.util.List;

/**
 * Service Interface for managing PatientMedicationTake.
 */
public interface PatientMedicationTakeService {

    /**
     * Save a patientMedicationTake.
     *
     * @param patientMedicationTake the entity to save
     * @return the persisted entity
     */
    PatientMedicationTake save(PatientMedicationTake patientMedicationTake);

    /**
     *  Get all the patientMedicationTakes.
     *  
     *  @return the list of entities
     */
    List<PatientMedicationTake> findAll();

    /**
     *  Get the "id" patientMedicationTake.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientMedicationTake findOne(Long id);

    /**
     *  Delete the "id" patientMedicationTake.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientMedicationTake corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<PatientMedicationTake> search(String query);
}
