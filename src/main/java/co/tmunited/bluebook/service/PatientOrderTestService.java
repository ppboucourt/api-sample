package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.PatientOrderTest;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing PatientOrderTest.
 */
public interface PatientOrderTestService {

    /**
     * Save a patientOrderTest.
     *
     * @param patientOrderTest the entity to save
     * @return the persisted entity
     */
    PatientOrderTest save(PatientOrderTest patientOrderTest);

    /**
     *  Get all the patientOrderTests.
     *
     *  @return the list of entities
     */
    List<PatientOrderTest> findAll(Long id);

    /**
     *  Get the "id" patientOrderTest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientOrderTest findOne(Long id);

    /**
     *  Delete the "id" patientOrderTest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientOrderTest corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientOrderTest> search(String query);

    Set<PatientOrderItem> getPatientOrderTestItems(Long id);

    PatientOrderTest getPatientOrderItemsForSchedules(Long id);
}
