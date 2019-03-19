package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.LabRequisitionsComponents;

import java.util.List;

/**
 * Service Interface for managing LabRequisitionsComponents.
 */
public interface LabRequisitionsComponentsService {

    /**
     * Save a labRequisitionsComponents.
     *
     * @param labRequisitionsComponents the entity to save
     * @return the persisted entity
     */
    LabRequisitionsComponents save(LabRequisitionsComponents labRequisitionsComponents);

    /**
     *  Get all the labRequisitionsComponents.
     *  
     *  @return the list of entities
     */
    List<LabRequisitionsComponents> findAll();

    /**
     *  Get the "id" labRequisitionsComponents.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LabRequisitionsComponents findOne(Long id);

    /**
     *  Delete the "id" labRequisitionsComponents.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the labRequisitionsComponents corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<LabRequisitionsComponents> search(String query);
}
