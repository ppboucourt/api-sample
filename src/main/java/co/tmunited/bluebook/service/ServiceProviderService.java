package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ServiceProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ServiceProvider.
 */
public interface ServiceProviderService {

    /**
     * Save a serviceProvider.
     *
     * @param serviceProvider the entity to save
     * @return the persisted entity
     */
    ServiceProvider save(ServiceProvider serviceProvider);

    /**
     *  Get all the serviceProviders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceProvider> findAll(Pageable pageable);

    /**
     *  Get the "id" serviceProvider.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServiceProvider findOne(Long id);

    /**
     *  Delete the "id" serviceProvider.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceProvider corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceProvider> search(String query, Pageable pageable);
}
