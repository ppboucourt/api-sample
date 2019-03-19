package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.InsuranceType;

import java.util.List;

/**
 * Service Interface for managing InsuranceType.
 */
public interface InsuranceTypeService {

    /**
     * Save a insuranceType.
     *
     * @param insuranceType the entity to save
     * @return the persisted entity
     */
    InsuranceType save(InsuranceType insuranceType);

    /**
     *  Get all the insuranceTypes.
     *  
     *  @return the list of entities
     */
    List<InsuranceType> findAll();

    /**
     *  Get the "id" insuranceType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InsuranceType findOne(Long id);

    /**
     *  Delete the "id" insuranceType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insuranceType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<InsuranceType> search(String query);
}
