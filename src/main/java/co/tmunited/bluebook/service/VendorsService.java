package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Vendors;

import java.util.List;

/**
 * Service Interface for managing Vendors.
 */
public interface VendorsService {

    /**
     * Save a vendors.
     *
     * @param vendors the entity to save
     * @return the persisted entity
     */
    Vendors save(Vendors vendors);

    /**
     *  Get all the vendors.
     *  
     *  @return the list of entities
     */
    List<Vendors> findAll();

    /**
     *  Get the "id" vendors.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Vendors findOne(Long id);

    /**
     *  Delete the "id" vendors.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vendors corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Vendors> search(String query);
}
