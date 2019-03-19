package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeEthnicity;

import java.util.List;

/**
 * Service Interface for managing TypeEthnicity.
 */
public interface TypeEthnicityService {

    /**
     * Save a typeEthnicity.
     *
     * @param typeEthnicity the entity to save
     * @return the persisted entity
     */
    TypeEthnicity save(TypeEthnicity typeEthnicity);

    /**
     *  Get all the typeEthnicities.
     *  
     *  @return the list of entities
     */
    List<TypeEthnicity> findAll();

    /**
     *  Get the "id" typeEthnicity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeEthnicity findOne(Long id);

    /**
     *  Delete the "id" typeEthnicity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeEthnicity corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeEthnicity> search(String query);
}
