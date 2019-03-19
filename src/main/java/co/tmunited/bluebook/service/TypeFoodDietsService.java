package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeFoodDiets;

import java.util.List;

/**
 * Service Interface for managing TypeFoodDiets.
 */
public interface TypeFoodDietsService {

    /**
     * Save a typeFoodDiets.
     *
     * @param typeFoodDiets the entity to save
     * @return the persisted entity
     */
    TypeFoodDiets save(TypeFoodDiets typeFoodDiets);

    /**
     *  Get all the typeFoodDiets.
     *  
     *  @return the list of entities
     */
    List<TypeFoodDiets> findAll();

    /**
     *  Get the "id" typeFoodDiets.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeFoodDiets findOne(Long id);

    /**
     *  Delete the "id" typeFoodDiets.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeFoodDiets corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeFoodDiets> search(String query);
}
