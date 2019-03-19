package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePatientPropertyConditionService;
import co.tmunited.bluebook.domain.TypePatientPropertyCondition;
import co.tmunited.bluebook.repository.TypePatientPropertyConditionRepository;
import co.tmunited.bluebook.repository.search.TypePatientPropertyConditionSearchRepository;
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
 * Service Implementation for managing TypePatientPropertyCondition.
 */
@Service
@Transactional
public class TypePatientPropertyConditionServiceImpl implements TypePatientPropertyConditionService{

    private final Logger log = LoggerFactory.getLogger(TypePatientPropertyConditionServiceImpl.class);
    
    @Inject
    private TypePatientPropertyConditionRepository typePatientPropertyConditionRepository;

    @Inject
    private TypePatientPropertyConditionSearchRepository typePatientPropertyConditionSearchRepository;

    /**
     * Save a typePatientPropertyCondition.
     *
     * @param typePatientPropertyCondition the entity to save
     * @return the persisted entity
     */
    public TypePatientPropertyCondition save(TypePatientPropertyCondition typePatientPropertyCondition) {
        log.debug("Request to save TypePatientPropertyCondition : {}", typePatientPropertyCondition);
        TypePatientPropertyCondition result = typePatientPropertyConditionRepository.save(typePatientPropertyCondition);
        typePatientPropertyConditionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePatientPropertyConditions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePatientPropertyCondition> findAll() {
        log.debug("Request to get all TypePatientPropertyConditions");
        List<TypePatientPropertyCondition> result = typePatientPropertyConditionRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePatientPropertyCondition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePatientPropertyCondition findOne(Long id) {
        log.debug("Request to get TypePatientPropertyCondition : {}", id);
        TypePatientPropertyCondition typePatientPropertyCondition = typePatientPropertyConditionRepository.findOne(id);
        return typePatientPropertyCondition;
    }

    /**
     *  Delete the  typePatientPropertyCondition by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePatientPropertyCondition : {}", id);
      TypePatientPropertyCondition typePatientPropertyCondition = typePatientPropertyConditionRepository.findOne(id);
      typePatientPropertyCondition.setDelStatus(true);
      TypePatientPropertyCondition result = typePatientPropertyConditionRepository.save(typePatientPropertyCondition);
      
      typePatientPropertyConditionSearchRepository.save(result);
    }

    /**
     * Search for the typePatientPropertyCondition corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientPropertyCondition> search(String query) {
        log.debug("Request to search TypePatientPropertyConditions for query {}", query);
        return StreamSupport
            .stream(typePatientPropertyConditionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
