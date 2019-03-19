package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.AllOrders;

import java.util.List;

/**
 * Service Interface for managing AllOrders.
 */
public interface AllOrdersService {

    /**
     * Save a allOrders.
     *
     * @param allOrders the entity to save
     * @return the persisted entity
     */
    AllOrders save(AllOrders allOrders);

    /**
     *  Get all the allOrders.
     *  
     *  @return the list of entities
     */
    List<AllOrders> findAll();

    /**
     *  Get the "id" allOrders.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AllOrders findOne(Long id);

    /**
     *  Delete the "id" allOrders.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the allOrders corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AllOrders> search(String query);
}
