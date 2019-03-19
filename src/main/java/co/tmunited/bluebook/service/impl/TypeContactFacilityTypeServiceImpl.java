package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeContactFacilityTypeService;
import co.tmunited.bluebook.domain.TypeContactFacilityType;
import co.tmunited.bluebook.repository.TypeContactFacilityTypeRepository;
import co.tmunited.bluebook.repository.search.TypeContactFacilityTypeSearchRepository;
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
 * Service Implementation for managing TypeContactFacilityType.
 */
@Service
@Transactional
public class TypeContactFacilityTypeServiceImpl implements TypeContactFacilityTypeService{

    private final Logger log = LoggerFactory.getLogger(TypeContactFacilityTypeServiceImpl.class);
    
    @Inject
    private TypeContactFacilityTypeRepository typeContactFacilityTypeRepository;

    @Inject
    private TypeContactFacilityTypeSearchRepository typeContactFacilityTypeSearchRepository;

    /**
     * Save a typeContactFacilityType.
     *
     * @param typeContactFacilityType the entity to save
     * @return the persisted entity
     */
    public TypeContactFacilityType save(TypeContactFacilityType typeContactFacilityType) {
        log.debug("Request to save TypeContactFacilityType : {}", typeContactFacilityType);
        TypeContactFacilityType result = typeContactFacilityTypeRepository.save(typeContactFacilityType);
        typeContactFacilityTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeContactFacilityTypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeContactFacilityType> findAll() {
        log.debug("Request to get all TypeContactFacilityTypes");
        List<TypeContactFacilityType> result = typeContactFacilityTypeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeContactFacilityType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeContactFacilityType findOne(Long id) {
        log.debug("Request to get TypeContactFacilityType : {}", id);
        TypeContactFacilityType typeContactFacilityType = typeContactFacilityTypeRepository.findOne(id);
        return typeContactFacilityType;
    }

    /**
     *  Delete the  typeContactFacilityType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeContactFacilityType : {}", id);
      TypeContactFacilityType typeContactFacilityType = typeContactFacilityTypeRepository.findOne(id);
      typeContactFacilityType.setDelStatus(true);
      TypeContactFacilityType result = typeContactFacilityTypeRepository.save(typeContactFacilityType);
      
      typeContactFacilityTypeSearchRepository.save(result);
    }

    /**
     * Search for the typeContactFacilityType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeContactFacilityType> search(String query) {
        log.debug("Request to search TypeContactFacilityTypes for query {}", query);
        return StreamSupport
            .stream(typeContactFacilityTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
