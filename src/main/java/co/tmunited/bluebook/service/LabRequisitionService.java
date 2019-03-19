package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.LabRequisition;

import java.util.List;

/**
 * Service Interface for managing LabRequisition.
 */
public interface LabRequisitionService {

    /**
     * Save a labRequisition.
     *
     * @param labRequisition the entity to save
     * @return the persisted entity
     */
    LabRequisition save(LabRequisition labRequisition);

    /**
     *  Get all the labRequisitions.
     *  
     *  @return the list of entities
     */
    List<LabRequisition> findAll();

    /**
     *  Get the "id" labRequisition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LabRequisition findOne(Long id);

    /**
     *  Delete the "id" labRequisition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the labRequisition corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<LabRequisition> search(String query);
}
