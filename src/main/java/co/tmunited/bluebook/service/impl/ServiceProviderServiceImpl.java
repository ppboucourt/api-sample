package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Diet;
import co.tmunited.bluebook.service.ServiceProviderService;
import co.tmunited.bluebook.domain.ServiceProvider;
import co.tmunited.bluebook.repository.ServiceProviderRepository;
import co.tmunited.bluebook.repository.search.ServiceProviderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceProvider.
 */
@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService{

    private final Logger log = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    @Inject
    private ServiceProviderRepository serviceProviderRepository;

    @Inject
    private ServiceProviderSearchRepository serviceProviderSearchRepository;

    /**
     * Save a serviceProvider.
     *
     * @param serviceProvider the entity to save
     * @return the persisted entity
     */
    public ServiceProvider save(ServiceProvider serviceProvider) {
        log.debug("Request to save ServiceProvider : {}", serviceProvider);
        ServiceProvider result = serviceProviderRepository.save(serviceProvider);
        serviceProviderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the serviceProviders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ServiceProvider> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProviders");
        Page<ServiceProvider> result = serviceProviderRepository.findAllByDelStatusFalse(pageable);
        return result;
    }

    /**
     *  Get one serviceProvider by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ServiceProvider findOne(Long id) {
        log.debug("Request to get ServiceProvider : {}", id);
        ServiceProvider serviceProvider = serviceProviderRepository.findOne(id);
        return serviceProvider;
    }

    /**
     *  Delete the  serviceProvider by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvider : {}", id);

        ServiceProvider serviceProvider = serviceProviderRepository.findOne(id);
        serviceProvider.setDelStatus(true);
        ServiceProvider result = serviceProviderRepository.save(serviceProvider);

        serviceProviderSearchRepository.save(result);
    }

    /**
     * Search for the serviceProvider corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ServiceProvider> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceProviders for query {}", query);
        Page<ServiceProvider> result = serviceProviderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
