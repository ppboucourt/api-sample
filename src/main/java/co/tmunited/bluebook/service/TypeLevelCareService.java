package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.TypeLevelCare;

import java.util.List;

/**
 * Service Interface for managing TypeLevelCare.
 */
public interface TypeLevelCareService {

    /**
     * Save a typeLevelCare.
     *
     * @param typeLevelCare the entity to save
     * @return the persisted entity
     */
    TypeLevelCare save(TypeLevelCare typeLevelCare);

    /**
     *  Get all the typeLevelCares.
     *
     *  @return the list of entities
     */
    List<TypeLevelCare> findAll();

    /**
     *  Get the "id" typeLevelCare.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeLevelCare findOne(Long id);

    /**
     *  Delete the "id" typeLevelCare.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeLevelCare corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<TypeLevelCare> search(String query);

    List<Forms> findAllByFacilityAndLevelCareLoadedAutomatic(Long facId, Long lcId);

    List<TypeLevelCare> findAllByFacility(Long id);
}
