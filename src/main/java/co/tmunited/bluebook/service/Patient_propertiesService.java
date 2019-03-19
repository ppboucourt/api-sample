package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Patient_properties;

import java.util.List;

/**
 * Service Interface for managing Patient_properties.
 */
public interface Patient_propertiesService {

    /**
     * Save a patient_properties.
     *
     * @param patient_properties the entity to save
     * @return the persisted entity
     */
    Patient_properties save(Patient_properties patient_properties);

    /**
     *  Get all the patient_properties.
     *  
     *  @return the list of entities
     */
    List<Patient_properties> findAll();

    /**
     *  Get the "id" patient_properties.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Patient_properties findOne(Long id);

    /**
     *  Delete the "id" patient_properties.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patient_properties corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Patient_properties> search(String query);
}
