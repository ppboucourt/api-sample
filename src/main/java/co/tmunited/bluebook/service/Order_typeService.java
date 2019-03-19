package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Order_type;

import java.util.List;

/**
 * Service Interface for managing Order_type.
 */
public interface Order_typeService {

    /**
     * Save a order_type.
     *
     * @param order_type the entity to save
     * @return the persisted entity
     */
    Order_type save(Order_type order_type);

    /**
     *  Get all the order_types.
     *  
     *  @return the list of entities
     */
    List<Order_type> findAll();

    /**
     *  Get the "id" order_type.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Order_type findOne(Long id);

    /**
     *  Delete the "id" order_type.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the order_type corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Order_type> search(String query);
}
