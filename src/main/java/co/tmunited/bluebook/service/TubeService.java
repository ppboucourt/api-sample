package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Tube;

import java.util.List;

/**
 * Service Interface for managing Tube.
 */
public interface TubeService {

    /**
     * Save a tube.
     *
     * @param tube the entity to save
     * @return the persisted entity
     */
    Tube save(Tube tube);

    /**
     *  Get all the tubes.
     *
     *  @return the list of entities
     */
    List<Tube> findAll();

    /**
     *  Get the "id" tube.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tube findOne(Long id);

    /**
     *  Delete the "id" tube.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tube corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Tube> search(String query);
}
