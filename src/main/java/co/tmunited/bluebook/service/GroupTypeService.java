package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.GroupType;

import java.util.List;

/**
 * Service Interface for managing GroupType.
 */
public interface GroupTypeService {

    /**
     * Save a groupType.
     *
     * @param groupType the entity to save
     * @return the persisted entity
     */
    GroupType save(GroupType groupType);

    /**
     *  Get all the groupTypes.
     *  
     *  @return the list of entities
     */
    List<GroupType> findAll();

    /**
     *  Get the "id" groupType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GroupType findOne(Long id);

    /**
     *  Delete the "id" groupType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the groupType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<GroupType> search(String query);
}
