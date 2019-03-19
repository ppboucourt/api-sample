package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Routes;

import java.util.List;

/**
 * Service Interface for managing Routes.
 */
public interface RoutesService {

    /**
     * Save a routes.
     *
     * @param routes the entity to save
     * @return the persisted entity
     */
    Routes save(Routes routes);

    /**
     *  Get all the routes.
     *  
     *  @return the list of entities
     */
    List<Routes> findAll();

    /**
     *  Get the "id" routes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Routes findOne(Long id);

    /**
     *  Delete the "id" routes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the routes corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Routes> search(String query);
}
