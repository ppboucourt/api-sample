package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.InsuranceRelationService;
import co.tmunited.bluebook.domain.InsuranceRelation;
import co.tmunited.bluebook.repository.InsuranceRelationRepository;
import co.tmunited.bluebook.repository.search.InsuranceRelationSearchRepository;
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
 * Service Implementation for managing InsuranceRelation.
 */
@Service
@Transactional
public class InsuranceRelationServiceImpl implements InsuranceRelationService{

    private final Logger log = LoggerFactory.getLogger(InsuranceRelationServiceImpl.class);
    
    @Inject
    private InsuranceRelationRepository insuranceRelationRepository;

    @Inject
    private InsuranceRelationSearchRepository insuranceRelationSearchRepository;

    /**
     * Save a insuranceRelation.
     *
     * @param insuranceRelation the entity to save
     * @return the persisted entity
     */
    public InsuranceRelation save(InsuranceRelation insuranceRelation) {
        log.debug("Request to save InsuranceRelation : {}", insuranceRelation);
        InsuranceRelation result = insuranceRelationRepository.save(insuranceRelation);
        insuranceRelationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the insuranceRelations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InsuranceRelation> findAll() {
        log.debug("Request to get all InsuranceRelations");
        List<InsuranceRelation> result = insuranceRelationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one insuranceRelation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InsuranceRelation findOne(Long id) {
        log.debug("Request to get InsuranceRelation : {}", id);
        InsuranceRelation insuranceRelation = insuranceRelationRepository.findOne(id);
        return insuranceRelation;
    }

    /**
     *  Delete the  insuranceRelation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsuranceRelation : {}", id);
      InsuranceRelation insuranceRelation = insuranceRelationRepository.findOne(id);
      insuranceRelation.setDelStatus(true);
      InsuranceRelation result = insuranceRelationRepository.save(insuranceRelation);
      
      insuranceRelationSearchRepository.save(result);
    }

    /**
     * Search for the insuranceRelation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsuranceRelation> search(String query) {
        log.debug("Request to search InsuranceRelations for query {}", query);
        return StreamSupport
            .stream(insuranceRelationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
