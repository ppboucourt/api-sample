package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientActionTake;

import java.util.List;

/**
 * Service Interface for managing PatientActionTake.
 */
public interface PatientActionTakeService {

    /**
     * Save a patientActionTake.
     *
     * @param patientActionTake the entity to save
     * @return the persisted entity
     */
    PatientActionTake save(PatientActionTake patientActionTake);

    /**
     *  Get all the patientActionTakes.
     *  
     *  @return the list of entities
     */
    List<PatientActionTake> findAll();

    /**
     *  Get the "id" patientActionTake.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientActionTake findOne(Long id);

    /**
     *  Delete the "id" patientActionTake.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientActionTake corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<PatientActionTake> search(String query);
}
