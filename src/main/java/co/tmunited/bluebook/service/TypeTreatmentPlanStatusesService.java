package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeTreatmentPlanStatuses;

import java.util.List;

/**
 * Service Interface for managing TypeTreatmentPlanStatuses.
 */
public interface TypeTreatmentPlanStatusesService {

    /**
     * Save a typeTreatmentPlanStatuses.
     *
     * @param typeTreatmentPlanStatuses the entity to save
     * @return the persisted entity
     */
    TypeTreatmentPlanStatuses save(TypeTreatmentPlanStatuses typeTreatmentPlanStatuses);

    /**
     *  Get all the typeTreatmentPlanStatuses.
     *  
     *  @return the list of entities
     */
    List<TypeTreatmentPlanStatuses> findAll();

    /**
     *  Get the "id" typeTreatmentPlanStatuses.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeTreatmentPlanStatuses findOne(Long id);

    /**
     *  Delete the "id" typeTreatmentPlanStatuses.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeTreatmentPlanStatuses corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeTreatmentPlanStatuses> search(String query);
}
