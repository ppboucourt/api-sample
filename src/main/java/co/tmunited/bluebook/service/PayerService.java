package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Payer;

import java.util.List;

/**
 * Service Interface for managing Payer.
 */
public interface PayerService {

    /**
     * Save a payer.
     *
     * @param payer the entity to save
     * @return the persisted entity
     */
    Payer save(Payer payer);

    /**
     *  Get all the payers.
     *  
     *  @return the list of entities
     */
    List<Payer> findAll();

    /**
     *  Get the "id" payer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Payer findOne(Long id);

    /**
     *  Delete the "id" payer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the payer corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Payer> search(String query);
}
