package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToIcd10;

import java.util.List;

/**
 * Service Interface for managing ChartToIcd10.
 */
public interface ChartToIcd10Service {

    /**
     * Save a chartToIcd10.
     *
     * @param chartToIcd10 the entity to save
     * @return the persisted entity
     */
    ChartToIcd10 save(ChartToIcd10 chartToIcd10);

    /**
     *  Get all the chartToIcd10S.
     *
     *  @return the list of entities
     */
    List<ChartToIcd10> findAll();

    /**
     *  Get the "id" chartToIcd10.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToIcd10 findOne(Long id);

    /**
     *  Delete the "id" chartToIcd10.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToIcd10 corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ChartToIcd10> search(String query);

    /**
     *  Get all the problemLists for a Patient.
     *
     *  @return the list of entities
     */
    List<ChartToIcd10> findAllByPatient(Long id);

    /**
     *  Get all the active problemLists for a Patient.
     *
     *  @return the list of entities
     */
    List<ChartToIcd10> findAllActiveByPatient(Long id);
}
