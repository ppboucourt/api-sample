package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeRace;

import java.util.List;

/**
 * Service Interface for managing TypeRace.
 */
public interface TypeRaceService {

    /**
     * Save a typeRace.
     *
     * @param typeRace the entity to save
     * @return the persisted entity
     */
    TypeRace save(TypeRace typeRace);

    /**
     *  Get all the typeRaces.
     *  
     *  @return the list of entities
     */
    List<TypeRace> findAll();

    /**
     *  Get the "id" typeRace.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeRace findOne(Long id);

    /**
     *  Delete the "id" typeRace.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeRace corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeRace> search(String query);
}
