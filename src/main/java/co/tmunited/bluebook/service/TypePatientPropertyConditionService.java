package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePatientPropertyCondition;

import java.util.List;

/**
 * Service Interface for managing TypePatientPropertyCondition.
 */
public interface TypePatientPropertyConditionService {

    /**
     * Save a typePatientPropertyCondition.
     *
     * @param typePatientPropertyCondition the entity to save
     * @return the persisted entity
     */
    TypePatientPropertyCondition save(TypePatientPropertyCondition typePatientPropertyCondition);

    /**
     *  Get all the typePatientPropertyConditions.
     *  
     *  @return the list of entities
     */
    List<TypePatientPropertyCondition> findAll();

    /**
     *  Get the "id" typePatientPropertyCondition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePatientPropertyCondition findOne(Long id);

    /**
     *  Delete the "id" typePatientPropertyCondition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePatientPropertyCondition corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePatientPropertyCondition> search(String query);
}
