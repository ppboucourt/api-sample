package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Hospitalization;

import java.util.List;

/**
 * Service Interface for managing Hospitalization.
 */
public interface HospitalizationService {

    /**
     * Save a hospitalization.
     *
     * @param hospitalization the entity to save
     * @return the persisted entity
     */
    Hospitalization save(Hospitalization hospitalization);

    /**
     *  Get all the hospitalizations.
     *  
     *  @return the list of entities
     */
    List<Hospitalization> findAll();

    /**
     *  Get the "id" hospitalization.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Hospitalization findOne(Long id);

    /**
     *  Delete the "id" hospitalization.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hospitalization corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Hospitalization> search(String query);
}
