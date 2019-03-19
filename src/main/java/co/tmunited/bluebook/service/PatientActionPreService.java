package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientActionPre;

import java.util.List;

/**
 * Service Interface for managing PatientActionPre.
 */
public interface PatientActionPreService {

    /**
     * Save a patientActionPre.
     *
     * @param patientActionPre the entity to save
     * @return the persisted entity
     */
    PatientActionPre save(PatientActionPre patientActionPre);

    /**
     *  Get all the patientActionPres.
     *
     *  @return the list of entities
     */
    List<PatientActionPre> findAll(Long id);

    /**
     *  Get the "id" patientActionPre.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientActionPre findOne(Long id);

    /**
     *  Delete the "id" patientActionPre.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientActionPre corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientActionPre> search(String query);
}
