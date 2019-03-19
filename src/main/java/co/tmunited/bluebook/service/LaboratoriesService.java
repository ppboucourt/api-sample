package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Laboratories;

import java.util.List;

/**
 * Service Interface for managing Laboratories.
 */
public interface LaboratoriesService {

    /**
     * Save a laboratories.
     *
     * @param laboratories the entity to save
     * @return the persisted entity
     */
    Laboratories save(Laboratories laboratories);

    /**
     *  Get all the laboratories.
     *  
     *  @return the list of entities
     */
    List<Laboratories> findAll();

    /**
     *  Get the "id" laboratories.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Laboratories findOne(Long id);

    /**
     *  Delete the "id" laboratories.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the laboratories corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Laboratories> search(String query);
}
