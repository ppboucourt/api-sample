package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientToShift;

import java.util.List;

/**
 * Service Interface for managing PatientToShift.
 */
public interface PatientToShiftService {

    /**
     * Save a patientToShift.
     *
     * @param patientToShift the entity to save
     * @return the persisted entity
     */
    PatientToShift save(PatientToShift patientToShift);

    /**
     *  Get all the patientToShifts.
     *  
     *  @return the list of entities
     */
    List<PatientToShift> findAll();

    /**
     *  Get the "id" patientToShift.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientToShift findOne(Long id);

    /**
     *  Delete the "id" patientToShift.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientToShift corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<PatientToShift> search(String query);
}
