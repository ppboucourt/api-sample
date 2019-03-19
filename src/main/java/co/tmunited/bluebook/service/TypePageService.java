package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePage;

import java.util.List;

/**
 * Service Interface for managing TypePage.
 */
public interface TypePageService {

    /**
     * Save a typePage.
     *
     * @param typePage the entity to save
     * @return the persisted entity
     */
    TypePage save(TypePage typePage);

    /**
     *  Get all the typePages.
     *  
     *  @return the list of entities
     */
    List<TypePage> findAll();

    /**
     *  Get the "id" typePage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePage findOne(Long id);

    /**
     *  Delete the "id" typePage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePage> search(String query);
}
