package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Tables;

import java.util.List;

/**
 * Service Interface for managing Tables.
 */
public interface TablesService {

    /**
     * Save a tables.
     *
     * @param tables the entity to save
     * @return the persisted entity
     */
    Tables save(Tables tables);

    /**
     *  Get all the tables.
     *  
     *  @return the list of entities
     */
    List<Tables> findAll();

    /**
     *  Get the "id" tables.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tables findOne(Long id);

    /**
     *  Delete the "id" tables.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tables corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Tables> search(String query);
}
