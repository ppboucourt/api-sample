package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.InsuranceCarrier;

import java.util.List;

/**
 * Service Interface for managing InsuranceCarrier.
 */
public interface InsuranceCarrierService {

    /**
     * Save a insuranceCarrier.
     *
     * @param insuranceCarrier the entity to save
     * @return the persisted entity
     */
    InsuranceCarrier save(InsuranceCarrier insuranceCarrier);

    /**
     *  Get all the insuranceCarriers.
     *  
     *  @return the list of entities
     */
    List<InsuranceCarrier> findAll();

    /**
     *  Get the "id" insuranceCarrier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InsuranceCarrier findOne(Long id);

    /**
     *  Delete the "id" insuranceCarrier.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insuranceCarrier corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<InsuranceCarrier> search(String query);
}
