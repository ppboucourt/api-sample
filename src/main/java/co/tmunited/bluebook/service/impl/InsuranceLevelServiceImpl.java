package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.InsuranceLevelService;
import co.tmunited.bluebook.domain.InsuranceLevel;
import co.tmunited.bluebook.repository.InsuranceLevelRepository;
import co.tmunited.bluebook.repository.search.InsuranceLevelSearchRepository;
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
 * Service Implementation for managing InsuranceLevel.
 */
@Service
@Transactional
public class InsuranceLevelServiceImpl implements InsuranceLevelService{

    private final Logger log = LoggerFactory.getLogger(InsuranceLevelServiceImpl.class);
    
    @Inject
    private InsuranceLevelRepository insuranceLevelRepository;

    @Inject
    private InsuranceLevelSearchRepository insuranceLevelSearchRepository;

    /**
     * Save a insuranceLevel.
     *
     * @param insuranceLevel the entity to save
     * @return the persisted entity
     */
    public InsuranceLevel save(InsuranceLevel insuranceLevel) {
        log.debug("Request to save InsuranceLevel : {}", insuranceLevel);
        InsuranceLevel result = insuranceLevelRepository.save(insuranceLevel);
        insuranceLevelSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the insuranceLevels.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InsuranceLevel> findAll() {
        log.debug("Request to get all InsuranceLevels");
        List<InsuranceLevel> result = insuranceLevelRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one insuranceLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InsuranceLevel findOne(Long id) {
        log.debug("Request to get InsuranceLevel : {}", id);
        InsuranceLevel insuranceLevel = insuranceLevelRepository.findOne(id);
        return insuranceLevel;
    }

    /**
     *  Delete the  insuranceLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsuranceLevel : {}", id);
      InsuranceLevel insuranceLevel = insuranceLevelRepository.findOne(id);
      insuranceLevel.setDelStatus(true);
      InsuranceLevel result = insuranceLevelRepository.save(insuranceLevel);
      
      insuranceLevelSearchRepository.save(result);
    }

    /**
     * Search for the insuranceLevel corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsuranceLevel> search(String query) {
        log.debug("Request to search InsuranceLevels for query {}", query);
        return StreamSupport
            .stream(insuranceLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
