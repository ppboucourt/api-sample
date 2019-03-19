package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePaymentMethods;

import java.util.List;

/**
 * Service Interface for managing TypePaymentMethods.
 */
public interface TypePaymentMethodsService {

    /**
     * Save a typePaymentMethods.
     *
     * @param typePaymentMethods the entity to save
     * @return the persisted entity
     */
    TypePaymentMethods save(TypePaymentMethods typePaymentMethods);

    /**
     *  Get all the typePaymentMethods.
     *  
     *  @return the list of entities
     */
    List<TypePaymentMethods> findAll();

    /**
     *  Get the "id" typePaymentMethods.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePaymentMethods findOne(Long id);

    /**
     *  Delete the "id" typePaymentMethods.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePaymentMethods corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePaymentMethods> search(String query);
}
