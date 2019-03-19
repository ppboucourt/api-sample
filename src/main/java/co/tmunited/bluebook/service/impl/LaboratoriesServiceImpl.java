package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.LaboratoriesService;
import co.tmunited.bluebook.domain.Laboratories;
import co.tmunited.bluebook.repository.LaboratoriesRepository;
import co.tmunited.bluebook.repository.search.LaboratoriesSearchRepository;
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
 * Service Implementation for managing Laboratories.
 */
@Service
@Transactional
public class LaboratoriesServiceImpl implements LaboratoriesService{

    private final Logger log = LoggerFactory.getLogger(LaboratoriesServiceImpl.class);
    
    @Inject
    private LaboratoriesRepository laboratoriesRepository;

    @Inject
    private LaboratoriesSearchRepository laboratoriesSearchRepository;

    /**
     * Save a laboratories.
     *
     * @param laboratories the entity to save
     * @return the persisted entity
     */
    public Laboratories save(Laboratories laboratories) {
        log.debug("Request to save Laboratories : {}", laboratories);
        Laboratories result = laboratoriesRepository.save(laboratories);
        laboratoriesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the laboratories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Laboratories> findAll() {
        log.debug("Request to get all Laboratories");
        List<Laboratories> result = laboratoriesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one laboratories by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Laboratories findOne(Long id) {
        log.debug("Request to get Laboratories : {}", id);
        Laboratories laboratories = laboratoriesRepository.findOne(id);
        return laboratories;
    }

    /**
     *  Delete the  laboratories by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Laboratories : {}", id);
      Laboratories laboratories = laboratoriesRepository.findOne(id);
      laboratories.setDelStatus(true);
      Laboratories result = laboratoriesRepository.save(laboratories);
      
      laboratoriesSearchRepository.save(result);
    }

    /**
     * Search for the laboratories corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Laboratories> search(String query) {
        log.debug("Request to search Laboratories for query {}", query);
        return StreamSupport
            .stream(laboratoriesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
