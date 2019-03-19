package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Facility;

import java.util.List;

/**
 * Service Interface for managing Facility.
 */
public interface FacilityService {

    /**
     * Save a facility.
     *
     * @param facility the entity to save
     * @return the persisted entity
     */
    Facility save(Facility facility);

    /**
     *  Get all the facilities.
     *
     *  @return the list of entities
     */
    List<Facility> findAll();

    /**
     *  Get the "id" facility.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Facility findOne(Long id);

    /**
     *  Delete the "id" facility.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the facility corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Facility> search(String query);

    void initFacility(Facility facility);
}
