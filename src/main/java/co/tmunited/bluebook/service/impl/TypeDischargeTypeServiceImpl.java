package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeDischargeTypeService;
import co.tmunited.bluebook.domain.TypeDischargeType;
import co.tmunited.bluebook.repository.TypeDischargeTypeRepository;
import co.tmunited.bluebook.repository.search.TypeDischargeTypeSearchRepository;
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
 * Service Implementation for managing TypeDischargeType.
 */
@Service
@Transactional
public class TypeDischargeTypeServiceImpl implements TypeDischargeTypeService{

    private final Logger log = LoggerFactory.getLogger(TypeDischargeTypeServiceImpl.class);
    
    @Inject
    private TypeDischargeTypeRepository typeDischargeTypeRepository;

    @Inject
    private TypeDischargeTypeSearchRepository typeDischargeTypeSearchRepository;

    /**
     * Save a typeDischargeType.
     *
     * @param typeDischargeType the entity to save
     * @return the persisted entity
     */
    public TypeDischargeType save(TypeDischargeType typeDischargeType) {
        log.debug("Request to save TypeDischargeType : {}", typeDischargeType);
        TypeDischargeType result = typeDischargeTypeRepository.save(typeDischargeType);
        typeDischargeTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeDischargeTypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeDischargeType> findAll() {
        log.debug("Request to get all TypeDischargeTypes");
        List<TypeDischargeType> result = typeDischargeTypeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeDischargeType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeDischargeType findOne(Long id) {
        log.debug("Request to get TypeDischargeType : {}", id);
        TypeDischargeType typeDischargeType = typeDischargeTypeRepository.findOne(id);
        return typeDischargeType;
    }

    /**
     *  Delete the  typeDischargeType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeDischargeType : {}", id);
      TypeDischargeType typeDischargeType = typeDischargeTypeRepository.findOne(id);
      typeDischargeType.setDelStatus(true);
      TypeDischargeType result = typeDischargeTypeRepository.save(typeDischargeType);
      
      typeDischargeTypeSearchRepository.save(result);
    }

    /**
     * Search for the typeDischargeType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeDischargeType> search(String query) {
        log.debug("Request to search TypeDischargeTypes for query {}", query);
        return StreamSupport
            .stream(typeDischargeTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
