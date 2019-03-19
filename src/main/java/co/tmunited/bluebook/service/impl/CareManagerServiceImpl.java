package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.CareManagerService;
import co.tmunited.bluebook.domain.CareManager;
import co.tmunited.bluebook.repository.CareManagerRepository;
import co.tmunited.bluebook.repository.search.CareManagerSearchRepository;
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
 * Service Implementation for managing CareManager.
 */
@Service
@Transactional
public class CareManagerServiceImpl implements CareManagerService{

    private final Logger log = LoggerFactory.getLogger(CareManagerServiceImpl.class);
    
    @Inject
    private CareManagerRepository careManagerRepository;

    @Inject
    private CareManagerSearchRepository careManagerSearchRepository;

    /**
     * Save a careManager.
     *
     * @param careManager the entity to save
     * @return the persisted entity
     */
    public CareManager save(CareManager careManager) {
        log.debug("Request to save CareManager : {}", careManager);
        CareManager result = careManagerRepository.save(careManager);
        careManagerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the careManagers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CareManager> findAll() {
        log.debug("Request to get all CareManagers");
        List<CareManager> result = careManagerRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one careManager by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CareManager findOne(Long id) {
        log.debug("Request to get CareManager : {}", id);
        CareManager careManager = careManagerRepository.findOne(id);
        return careManager;
    }

    /**
     *  Delete the  careManager by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CareManager : {}", id);
      CareManager careManager = careManagerRepository.findOne(id);
      careManager.setDelStatus(true);
      CareManager result = careManagerRepository.save(careManager);
      
      careManagerSearchRepository.save(result);
    }

    /**
     * Search for the careManager corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CareManager> search(String query) {
        log.debug("Request to search CareManagers for query {}", query);
        return StreamSupport
            .stream(careManagerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
