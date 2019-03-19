package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Patient;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing Patient.
 */
public interface PatientService {

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    Patient save(Patient patient);

    /**
     *  Get all the patients.
     *
     *  @return the list of entities
     */
    List<Patient> findAll();

    /**
     *  Get the "id" patient.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Patient findOne(Long id);

    /**
     *  Delete the "id" patient.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patient corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Patient> search(String query);

    int getPatientAge(ZonedDateTime dob);
}
