package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeEthnicityService;
import co.tmunited.bluebook.domain.TypeEthnicity;
import co.tmunited.bluebook.repository.TypeEthnicityRepository;
import co.tmunited.bluebook.repository.search.TypeEthnicitySearchRepository;
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
 * Service Implementation for managing TypeEthnicity.
 */
@Service
@Transactional
public class TypeEthnicityServiceImpl implements TypeEthnicityService{

    private final Logger log = LoggerFactory.getLogger(TypeEthnicityServiceImpl.class);
    
    @Inject
    private TypeEthnicityRepository typeEthnicityRepository;

    @Inject
    private TypeEthnicitySearchRepository typeEthnicitySearchRepository;

    /**
     * Save a typeEthnicity.
     *
     * @param typeEthnicity the entity to save
     * @return the persisted entity
     */
    public TypeEthnicity save(TypeEthnicity typeEthnicity) {
        log.debug("Request to save TypeEthnicity : {}", typeEthnicity);
        TypeEthnicity result = typeEthnicityRepository.save(typeEthnicity);
        typeEthnicitySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeEthnicities.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeEthnicity> findAll() {
        log.debug("Request to get all TypeEthnicities");
        List<TypeEthnicity> result = typeEthnicityRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeEthnicity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeEthnicity findOne(Long id) {
        log.debug("Request to get TypeEthnicity : {}", id);
        TypeEthnicity typeEthnicity = typeEthnicityRepository.findOne(id);
        return typeEthnicity;
    }

    /**
     *  Delete the  typeEthnicity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEthnicity : {}", id);
      TypeEthnicity typeEthnicity = typeEthnicityRepository.findOne(id);
      typeEthnicity.setDelStatus(true);
      TypeEthnicity result = typeEthnicityRepository.save(typeEthnicity);
      
      typeEthnicitySearchRepository.save(result);
    }

    /**
     * Search for the typeEthnicity corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeEthnicity> search(String query) {
        log.debug("Request to search TypeEthnicities for query {}", query);
        return StreamSupport
            .stream(typeEthnicitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
