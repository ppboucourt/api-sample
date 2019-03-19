package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Medications;

import java.util.List;

/**
 * Service Interface for managing Medications.
 */
public interface MedicationsService {

    /**
     * Save a medications.
     *
     * @param medications the entity to save
     * @return the persisted entity
     */
    Medications save(Medications medications);

    /**
     *  Get all the medications.
     *
     *  @return the list of entities
     */
    List<Medications> findAll();

    /**
     *  Get the "id" medications.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Medications findOne(Long id);

    /**
     *  Delete the "id" medications.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the medications corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Medications> search(String query);
}
