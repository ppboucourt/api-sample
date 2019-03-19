package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TreatmentHistory;

import java.util.List;

/**
 * Service Interface for managing TreatmentHistory.
 */
public interface TreatmentHistoryService {

    /**
     * Save a treatmentHistory.
     *
     * @param treatmentHistory the entity to save
     * @return the persisted entity
     */
    TreatmentHistory save(TreatmentHistory treatmentHistory);

    /**
     *  Get all the treatmentHistories.
     *  
     *  @return the list of entities
     */
    List<TreatmentHistory> findAll();

    /**
     *  Get the "id" treatmentHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TreatmentHistory findOne(Long id);

    /**
     *  Delete the "id" treatmentHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the treatmentHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TreatmentHistory> search(String query);
}
