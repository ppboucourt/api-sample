package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.RoutesService;
import co.tmunited.bluebook.domain.Routes;
import co.tmunited.bluebook.repository.RoutesRepository;
import co.tmunited.bluebook.repository.search.RoutesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Routes.
 */
@Service
@Transactional
public class RoutesServiceImpl implements RoutesService{

    private final Logger log = LoggerFactory.getLogger(RoutesServiceImpl.class);
    
    @Inject
    private RoutesRepository routesRepository;

    @Inject
    private RoutesSearchRepository routesSearchRepository;

    /**
     * Save a routes.
     *
     * @param routes the entity to save
     * @return the persisted entity
     */
    public Routes save(Routes routes) {
        log.debug("Request to save Routes : {}", routes);
        Routes result = routesRepository.save(routes);
        routesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the routes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Routes> findAll() {
        log.debug("Request to get all Routes");
        List<Routes> result = routesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one routes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Routes findOne(Long id) {
        log.debug("Request to get Routes : {}", id);
        Routes routes = routesRepository.findOne(id);
        return routes;
    }

    /**
     *  Delete the  routes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Routes : {}", id);
      Routes routes = routesRepository.findOne(id);
      routes.setDelStatus(true);
      Routes result = routesRepository.save(routes);
      
      routesSearchRepository.save(result);
    }

    /**
     * Search for the routes corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Routes> search(String query) {
        log.debug("Request to search Routes for query {}", query);
        return StreamSupport
            .stream(routesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
