package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.InsuranceTypeService;
import co.tmunited.bluebook.domain.InsuranceType;
import co.tmunited.bluebook.repository.InsuranceTypeRepository;
import co.tmunited.bluebook.repository.search.InsuranceTypeSearchRepository;
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
 * Service Implementation for managing InsuranceType.
 */
@Service
@Transactional
public class InsuranceTypeServiceImpl implements InsuranceTypeService{

    private final Logger log = LoggerFactory.getLogger(InsuranceTypeServiceImpl.class);
    
    @Inject
    private InsuranceTypeRepository insuranceTypeRepository;

    @Inject
    private InsuranceTypeSearchRepository insuranceTypeSearchRepository;

    /**
     * Save a insuranceType.
     *
     * @param insuranceType the entity to save
     * @return the persisted entity
     */
    public InsuranceType save(InsuranceType insuranceType) {
        log.debug("Request to save InsuranceType : {}", insuranceType);
        InsuranceType result = insuranceTypeRepository.save(insuranceType);
        insuranceTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the insuranceTypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InsuranceType> findAll() {
        log.debug("Request to get all InsuranceTypes");
        List<InsuranceType> result = insuranceTypeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one insuranceType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InsuranceType findOne(Long id) {
        log.debug("Request to get InsuranceType : {}", id);
        InsuranceType insuranceType = insuranceTypeRepository.findOne(id);
        return insuranceType;
    }

    /**
     *  Delete the  insuranceType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsuranceType : {}", id);
      InsuranceType insuranceType = insuranceTypeRepository.findOne(id);
      insuranceType.setDelStatus(true);
      InsuranceType result = insuranceTypeRepository.save(insuranceType);
      
      insuranceTypeSearchRepository.save(result);
    }

    /**
     * Search for the insuranceType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsuranceType> search(String query) {
        log.debug("Request to search InsuranceTypes for query {}", query);
        return StreamSupport
            .stream(insuranceTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
