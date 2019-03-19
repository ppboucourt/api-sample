package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeContactFacilityType;

import java.util.List;

/**
 * Service Interface for managing TypeContactFacilityType.
 */
public interface TypeContactFacilityTypeService {

    /**
     * Save a typeContactFacilityType.
     *
     * @param typeContactFacilityType the entity to save
     * @return the persisted entity
     */
    TypeContactFacilityType save(TypeContactFacilityType typeContactFacilityType);

    /**
     *  Get all the typeContactFacilityTypes.
     *  
     *  @return the list of entities
     */
    List<TypeContactFacilityType> findAll();

    /**
     *  Get the "id" typeContactFacilityType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeContactFacilityType findOne(Long id);

    /**
     *  Delete the "id" typeContactFacilityType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeContactFacilityType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeContactFacilityType> search(String query);
}
