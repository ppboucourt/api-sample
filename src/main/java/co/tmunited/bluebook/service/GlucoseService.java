package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Glucose;

import java.util.List;

/**
 * Service Interface for managing Glucose.
 */
public interface GlucoseService {

    /**
     * Save a glucose.
     *
     * @param glucose the entity to save
     * @return the persisted entity
     */
    Glucose save(Glucose glucose);

    /**
     *  Get all the glucoses.
     *
     *  @return the list of entities
     */
    List<Glucose> findAll();

    /**
     *  Get the "id" glucose.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Glucose findOne(Long id);

    /**
     *  Delete the "id" glucose.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the glucose corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Glucose> search(String query);

    List<Glucose> findByChart(Long id);
}
