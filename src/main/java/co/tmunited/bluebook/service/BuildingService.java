package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Building;

import java.util.List;

/**
 * Service Interface for managing Building.
 */
public interface BuildingService {

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    Building save(Building building);

    /**
     *  Get all the buildings.
     *
     *  @return the list of entities
     */
    List<Building> findAll();

    /**
     *  Get the "id" building.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Building findOne(Long id);

    /**
     *  Delete the "id" building.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the building corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Building> search(String query);

    List<Building> findAllByFacility(Long id);
}
