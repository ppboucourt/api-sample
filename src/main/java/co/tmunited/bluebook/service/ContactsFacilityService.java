package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ContactsFacility;

import java.util.List;

/**
 * Service Interface for managing ContactsFacility.
 */
public interface ContactsFacilityService {

    /**
     * Save a contactsFacility.
     *
     * @param contactsFacility the entity to save
     * @return the persisted entity
     */
    ContactsFacility save(ContactsFacility contactsFacility);

    /**
     *  Get all the contactsFacilities.
     *
     *  @return the list of entities
     */
    List<ContactsFacility> findAll();

    /**
     *  Get the "id" contactsFacility.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactsFacility findOne(Long id);

    /**
     *  Delete the "id" contactsFacility.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contactsFacility corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ContactsFacility> search(String query);

    /**
     * Search the contacts corresponding to a facility
     *
     * @param facilityId
     *
     * @return List of contactsFacility
     */
    List<ContactsFacility> findAllByDelStatusIsFalseAndFacilityId(Long facilityId);
}
