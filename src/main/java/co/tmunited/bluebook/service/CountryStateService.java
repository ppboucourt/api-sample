package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.CountryState;

import java.util.List;

/**
 * Service Interface for managing CountryState.
 */
public interface CountryStateService {

    /**
     * Save a countryState.
     *
     * @param countryState the entity to save
     * @return the persisted entity
     */
    CountryState save(CountryState countryState);

    /**
     *  Get all the countryStates.
     *  
     *  @return the list of entities
     */
    List<CountryState> findAll();

    /**
     *  Get the "id" countryState.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CountryState findOne(Long id);

    /**
     *  Delete the "id" countryState.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the countryState corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CountryState> search(String query);
}
