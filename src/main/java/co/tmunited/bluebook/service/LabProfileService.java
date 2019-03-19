package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.LabProfile;

import java.util.List;

/**
 * Service Interface for managing LabProfile.
 */
public interface LabProfileService {

    /**
     * Save a labProfile.
     *
     * @param labProfile the entity to save
     * @return the persisted entity
     */
    LabProfile save(LabProfile labProfile);

    /**
     *  Get all the labProfiles.
     *
     *  @return the list of entities
     */
    List<LabProfile> findAll();

    /**
     *  Get the "id" labProfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LabProfile findOne(Long id);

    /**
     *  Delete the "id" labProfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the labProfile corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<LabProfile> search(String query);

    List<LabProfile> findAllByFacility(Long id);
}
