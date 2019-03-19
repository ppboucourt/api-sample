package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientResultDet;

import java.util.List;

/**
 * Service Interface for managing PatientResultDet.
 */
public interface PatientResultDetService {

    /**
     * Save a patientResultDet.
     *
     * @param patientResultDet the entity to save
     * @return the persisted entity
     */
    PatientResultDet save(PatientResultDet patientResultDet);

    /**
     *  Get all the patientResultDets.
     *
     *  @return the list of entities
     */
    List<PatientResultDet> findAll(Long id);

    /**
     *  Get the "id" patientResultDet.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientResultDet findOne(Long id);

    /**
     *  Delete the "id" patientResultDet.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientResultDet corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientResultDet> search(String query);
}
