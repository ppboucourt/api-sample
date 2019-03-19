package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.OrderComponent;

import java.util.List;

/**
 * Service Interface for managing OrderComponent.
 */
public interface OrderComponentService {

    /**
     * Save a orderComponent.
     *
     * @param orderComponent the entity to save
     * @return the persisted entity
     */
    OrderComponent save(OrderComponent orderComponent);

    /**
     *  Get all the orderComponents.
     *
     *  @return the list of entities
     */
    List<OrderComponent> findAll();

    /**
     *  Get the "id" orderComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderComponent findOne(Long id);

    /**
     *  Delete the "id" orderComponent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderComponent corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<OrderComponent> search(String query);

    /**
     *  Get all the orderComponents filtered by allOrder.
     *
     *  @return the list of entities
     */
    List<OrderComponent> findAllByAllOrders(Long id);
}
