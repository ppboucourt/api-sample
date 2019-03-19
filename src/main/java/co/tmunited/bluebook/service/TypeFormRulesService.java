package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeFormRules;

import java.util.List;

/**
 * Service Interface for managing TypeFormRules.
 */
public interface TypeFormRulesService {

    /**
     * Save a typeFormRules.
     *
     * @param typeFormRules the entity to save
     * @return the persisted entity
     */
    TypeFormRules save(TypeFormRules typeFormRules);

    /**
     *  Get all the typeFormRules.
     *  
     *  @return the list of entities
     */
    List<TypeFormRules> findAll();

    /**
     *  Get the "id" typeFormRules.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeFormRules findOne(Long id);

    /**
     *  Delete the "id" typeFormRules.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeFormRules corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeFormRules> search(String query);
}
