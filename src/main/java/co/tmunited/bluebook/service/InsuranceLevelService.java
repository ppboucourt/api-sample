package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.InsuranceLevel;

import java.util.List;

/**
 * Service Interface for managing InsuranceLevel.
 */
public interface InsuranceLevelService {

    /**
     * Save a insuranceLevel.
     *
     * @param insuranceLevel the entity to save
     * @return the persisted entity
     */
    InsuranceLevel save(InsuranceLevel insuranceLevel);

    /**
     *  Get all the insuranceLevels.
     *  
     *  @return the list of entities
     */
    List<InsuranceLevel> findAll();

    /**
     *  Get the "id" insuranceLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InsuranceLevel findOne(Long id);

    /**
     *  Delete the "id" insuranceLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insuranceLevel corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<InsuranceLevel> search(String query);
}
