package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeEvaluationService;
import co.tmunited.bluebook.domain.TypeEvaluation;
import co.tmunited.bluebook.repository.TypeEvaluationRepository;
import co.tmunited.bluebook.repository.search.TypeEvaluationSearchRepository;
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
 * Service Implementation for managing TypeEvaluation.
 */
@Service
@Transactional
public class TypeEvaluationServiceImpl implements TypeEvaluationService{

    private final Logger log = LoggerFactory.getLogger(TypeEvaluationServiceImpl.class);
    
    @Inject
    private TypeEvaluationRepository typeEvaluationRepository;

    @Inject
    private TypeEvaluationSearchRepository typeEvaluationSearchRepository;

    /**
     * Save a typeEvaluation.
     *
     * @param typeEvaluation the entity to save
     * @return the persisted entity
     */
    public TypeEvaluation save(TypeEvaluation typeEvaluation) {
        log.debug("Request to save TypeEvaluation : {}", typeEvaluation);
        TypeEvaluation result = typeEvaluationRepository.save(typeEvaluation);
        typeEvaluationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeEvaluations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeEvaluation> findAll() {
        log.debug("Request to get all TypeEvaluations");
        List<TypeEvaluation> result = typeEvaluationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeEvaluation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeEvaluation findOne(Long id) {
        log.debug("Request to get TypeEvaluation : {}", id);
        TypeEvaluation typeEvaluation = typeEvaluationRepository.findOne(id);
        return typeEvaluation;
    }

    /**
     *  Delete the  typeEvaluation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEvaluation : {}", id);
      TypeEvaluation typeEvaluation = typeEvaluationRepository.findOne(id);
      typeEvaluation.setDelStatus(true);
      TypeEvaluation result = typeEvaluationRepository.save(typeEvaluation);
      
      typeEvaluationSearchRepository.save(result);
    }

    /**
     * Search for the typeEvaluation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeEvaluation> search(String query) {
        log.debug("Request to search TypeEvaluations for query {}", query);
        return StreamSupport
            .stream(typeEvaluationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
