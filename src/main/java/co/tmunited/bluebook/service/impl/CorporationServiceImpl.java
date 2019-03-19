package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.CorporationService;
import co.tmunited.bluebook.domain.Corporation;
import co.tmunited.bluebook.repository.CorporationRepository;
import co.tmunited.bluebook.repository.search.CorporationSearchRepository;
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
 * Service Implementation for managing Corporation.
 */
@Service
@Transactional
public class CorporationServiceImpl implements CorporationService{

    private final Logger log = LoggerFactory.getLogger(CorporationServiceImpl.class);
    
    @Inject
    private CorporationRepository corporationRepository;

    @Inject
    private CorporationSearchRepository corporationSearchRepository;

    /**
     * Save a corporation.
     *
     * @param corporation the entity to save
     * @return the persisted entity
     */
    public Corporation save(Corporation corporation) {
        log.debug("Request to save Corporation : {}", corporation);
        Corporation result = corporationRepository.save(corporation);
        corporationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the corporations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Corporation> findAll() {
        log.debug("Request to get all Corporations");
        List<Corporation> result = corporationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one corporation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Corporation findOne(Long id) {
        log.debug("Request to get Corporation : {}", id);
        Corporation corporation = corporationRepository.findOne(id);
        return corporation;
    }

    /**
     *  Delete the  corporation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Corporation : {}", id);
      Corporation corporation = corporationRepository.findOne(id);
      corporation.setDelStatus(true);
      Corporation result = corporationRepository.save(corporation);
      
      corporationSearchRepository.save(result);
    }

    /**
     * Search for the corporation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Corporation> search(String query) {
        log.debug("Request to search Corporations for query {}", query);
        return StreamSupport
            .stream(corporationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
