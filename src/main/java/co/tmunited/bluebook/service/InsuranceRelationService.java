package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.InsuranceRelation;

import java.util.List;

/**
 * Service Interface for managing InsuranceRelation.
 */
public interface InsuranceRelationService {

    /**
     * Save a insuranceRelation.
     *
     * @param insuranceRelation the entity to save
     * @return the persisted entity
     */
    InsuranceRelation save(InsuranceRelation insuranceRelation);

    /**
     *  Get all the insuranceRelations.
     *  
     *  @return the list of entities
     */
    List<InsuranceRelation> findAll();

    /**
     *  Get the "id" insuranceRelation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InsuranceRelation findOne(Long id);

    /**
     *  Delete the "id" insuranceRelation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insuranceRelation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<InsuranceRelation> search(String query);
}
