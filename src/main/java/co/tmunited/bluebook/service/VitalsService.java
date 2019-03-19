package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Vitals;

import java.util.List;

/**
 * Service Interface for managing Vitals.
 */
public interface VitalsService {

    /**
     * Save a vitals.
     *
     * @param vitals the entity to save
     * @return the persisted entity
     */
    Vitals save(Vitals vitals);

    /**
     *  Get all the vitals.
     *
     *  @return the list of entities
     */
    List<Vitals> findAll();

    /**
     *  Get the "id" vitals.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Vitals findOne(Long id);

    /**
     *  Delete the "id" vitals.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vitals corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Vitals> search(String query);

    List<Vitals> findByChart(Long id);
}
