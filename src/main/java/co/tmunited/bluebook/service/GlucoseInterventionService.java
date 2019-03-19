package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.GlucoseIntervention;

import java.util.List;

/**
 * Service Interface for managing GlucoseIntervention.
 */
public interface GlucoseInterventionService {

    /**
     * Save a glucoseIntervention.
     *
     * @param glucoseIntervention the entity to save
     * @return the persisted entity
     */
    GlucoseIntervention save(GlucoseIntervention glucoseIntervention);

    /**
     *  Get all the glucoseInterventions.
     *  
     *  @return the list of entities
     */
    List<GlucoseIntervention> findAll();

    /**
     *  Get the "id" glucoseIntervention.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GlucoseIntervention findOne(Long id);

    /**
     *  Delete the "id" glucoseIntervention.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the glucoseIntervention corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<GlucoseIntervention> search(String query);
}
