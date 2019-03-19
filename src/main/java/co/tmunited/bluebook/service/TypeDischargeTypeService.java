package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeDischargeType;

import java.util.List;

/**
 * Service Interface for managing TypeDischargeType.
 */
public interface TypeDischargeTypeService {

    /**
     * Save a typeDischargeType.
     *
     * @param typeDischargeType the entity to save
     * @return the persisted entity
     */
    TypeDischargeType save(TypeDischargeType typeDischargeType);

    /**
     *  Get all the typeDischargeTypes.
     *  
     *  @return the list of entities
     */
    List<TypeDischargeType> findAll();

    /**
     *  Get the "id" typeDischargeType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeDischargeType findOne(Long id);

    /**
     *  Delete the "id" typeDischargeType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeDischargeType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeDischargeType> search(String query);
}
