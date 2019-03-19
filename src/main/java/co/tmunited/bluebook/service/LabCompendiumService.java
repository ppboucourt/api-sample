package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.LabCompendium;

import java.util.List;

/**
 * Service Interface for managing LabCompendium.
 */
public interface LabCompendiumService {

    /**
     * Save a labCompendium.
     *
     * @param labCompendium the entity to save
     * @return the persisted entity
     */
    LabCompendium save(LabCompendium labCompendium);

    /**
     *  Get all the labCompendiums.
     *  
     *  @return the list of entities
     */
    List<LabCompendium> findAll();

    /**
     *  Get the "id" labCompendium.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LabCompendium findOne(Long id);

    /**
     *  Delete the "id" labCompendium.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the labCompendium corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<LabCompendium> search(String query);
}
