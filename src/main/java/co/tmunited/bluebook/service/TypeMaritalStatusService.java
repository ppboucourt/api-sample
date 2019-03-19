package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeMaritalStatus;

import java.util.List;

/**
 * Service Interface for managing TypeMaritalStatus.
 */
public interface TypeMaritalStatusService {

    /**
     * Save a typeMaritalStatus.
     *
     * @param typeMaritalStatus the entity to save
     * @return the persisted entity
     */
    TypeMaritalStatus save(TypeMaritalStatus typeMaritalStatus);

    /**
     *  Get all the typeMaritalStatuses.
     *  
     *  @return the list of entities
     */
    List<TypeMaritalStatus> findAll();

    /**
     *  Get the "id" typeMaritalStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeMaritalStatus findOne(Long id);

    /**
     *  Delete the "id" typeMaritalStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeMaritalStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeMaritalStatus> search(String query);
}
