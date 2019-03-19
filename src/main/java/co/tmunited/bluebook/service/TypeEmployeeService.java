package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeEmployee;

import java.util.List;

/**
 * Service Interface for managing TypeEmployee.
 */
public interface TypeEmployeeService {

    /**
     * Save a typeEmployee.
     *
     * @param typeEmployee the entity to save
     * @return the persisted entity
     */
    TypeEmployee save(TypeEmployee typeEmployee);

    /**
     *  Get all the typeEmployees.
     *  
     *  @return the list of entities
     */
    List<TypeEmployee> findAll();

    /**
     *  Get the "id" typeEmployee.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeEmployee findOne(Long id);

    /**
     *  Delete the "id" typeEmployee.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeEmployee corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeEmployee> search(String query);
}
