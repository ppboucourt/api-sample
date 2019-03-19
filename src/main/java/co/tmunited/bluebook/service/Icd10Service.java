package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Icd10;

import java.util.List;

/**
 * Service Interface for managing Icd10.
 */
public interface Icd10Service {

    /**
     * Save a icd10.
     *
     * @param icd10 the entity to save
     * @return the persisted entity
     */
    Icd10 save(Icd10 icd10);

    /**
     *  Get all the icd10S.
     *  
     *  @return the list of entities
     */
    List<Icd10> findAll();

    /**
     *  Get the "id" icd10.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Icd10 findOne(Long id);

    /**
     *  Delete the "id" icd10.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the icd10 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Icd10> search(String query);
}
