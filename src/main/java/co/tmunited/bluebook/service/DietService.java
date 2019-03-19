package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Diet;

import java.util.List;

/**
 * Service Interface for managing Diet.
 */
public interface DietService {

    /**
     * Save a diet.
     *
     * @param diet the entity to save
     * @return the persisted entity
     */
    Diet save(Diet diet);

    /**
     *  Get all the diets.
     *  
     *  @return the list of entities
     */
    List<Diet> findAll();

    /**
     *  Get the "id" diet.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Diet findOne(Long id);

    /**
     *  Delete the "id" diet.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the diet corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Diet> search(String query);
}
