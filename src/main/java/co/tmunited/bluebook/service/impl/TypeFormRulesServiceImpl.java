package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeFormRulesService;
import co.tmunited.bluebook.domain.TypeFormRules;
import co.tmunited.bluebook.repository.TypeFormRulesRepository;
import co.tmunited.bluebook.repository.search.TypeFormRulesSearchRepository;
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
 * Service Implementation for managing TypeFormRules.
 */
@Service
@Transactional
public class TypeFormRulesServiceImpl implements TypeFormRulesService{

    private final Logger log = LoggerFactory.getLogger(TypeFormRulesServiceImpl.class);
    
    @Inject
    private TypeFormRulesRepository typeFormRulesRepository;

    @Inject
    private TypeFormRulesSearchRepository typeFormRulesSearchRepository;

    /**
     * Save a typeFormRules.
     *
     * @param typeFormRules the entity to save
     * @return the persisted entity
     */
    public TypeFormRules save(TypeFormRules typeFormRules) {
        log.debug("Request to save TypeFormRules : {}", typeFormRules);
        TypeFormRules result = typeFormRulesRepository.save(typeFormRules);
        typeFormRulesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeFormRules.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeFormRules> findAll() {
        log.debug("Request to get all TypeFormRules");
        List<TypeFormRules> result = typeFormRulesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeFormRules by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeFormRules findOne(Long id) {
        log.debug("Request to get TypeFormRules : {}", id);
        TypeFormRules typeFormRules = typeFormRulesRepository.findOne(id);
        return typeFormRules;
    }

    /**
     *  Delete the  typeFormRules by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeFormRules : {}", id);
      TypeFormRules typeFormRules = typeFormRulesRepository.findOne(id);
      typeFormRules.setDelStatus(true);
      TypeFormRules result = typeFormRulesRepository.save(typeFormRules);
      
      typeFormRulesSearchRepository.save(result);
    }

    /**
     * Search for the typeFormRules corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeFormRules> search(String query) {
        log.debug("Request to search TypeFormRules for query {}", query);
        return StreamSupport
            .stream(typeFormRulesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
