package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Allergies;

import java.util.List;

/**
 * Service Interface for managing Allergies.
 */
public interface AllergiesService {

    /**
     * Save a allergies.
     *
     * @param allergies the entity to save
     * @return the persisted entity
     */
    Allergies save(Allergies allergies);

    /**
     *  Get all the allergies.
     *
     *  @return the list of entities
     */
    List<Allergies> findAll();

    /**
     *  Get all the allergies by chartId.
     *
     *  @return the list of entities
     */
    List<Allergies> findAllByChartId(Long id);

    /**
     *  Get the "id" allergies.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Allergies findOne(Long id);

    /**
     *  Delete the "id" allergies.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the allergies corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Allergies> search(String query);
}
