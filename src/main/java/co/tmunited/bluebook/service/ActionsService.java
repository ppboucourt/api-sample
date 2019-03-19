package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Actions;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * Service Interface for managing Actions.
 */
public interface ActionsService {

    Float f = 12.5f;
    Boolean b = Boolean.FALSE;
    /**
     * Save a actions.
     *
     * @param actions the entity to save
     * @return the persisted entity
     */
    Actions save(Actions actions);

    /**
     *  Get all the actions.
     *  
     *  @return the list of entities
     */
    List<Actions> findAll();

    /**
     *  Get the "id" actions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Actions findOne(Long id);

    /**
     *  Delete the "id" actions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the actions corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Actions> search(String query);
}
