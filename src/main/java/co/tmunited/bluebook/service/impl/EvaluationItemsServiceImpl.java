package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.EvaluationItemsService;
import co.tmunited.bluebook.domain.EvaluationItems;
import co.tmunited.bluebook.repository.EvaluationItemsRepository;
import co.tmunited.bluebook.repository.search.EvaluationItemsSearchRepository;
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
 * Service Implementation for managing EvaluationItems.
 */
@Service
@Transactional
public class EvaluationItemsServiceImpl implements EvaluationItemsService{

    private final Logger log = LoggerFactory.getLogger(EvaluationItemsServiceImpl.class);
    
    @Inject
    private EvaluationItemsRepository evaluationItemsRepository;

    @Inject
    private EvaluationItemsSearchRepository evaluationItemsSearchRepository;

    /**
     * Save a evaluationItems.
     *
     * @param evaluationItems the entity to save
     * @return the persisted entity
     */
    public EvaluationItems save(EvaluationItems evaluationItems) {
        log.debug("Request to save EvaluationItems : {}", evaluationItems);
        EvaluationItems result = evaluationItemsRepository.save(evaluationItems);
        evaluationItemsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the evaluationItems.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EvaluationItems> findAll() {
        log.debug("Request to get all EvaluationItems");
        List<EvaluationItems> result = evaluationItemsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one evaluationItems by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EvaluationItems findOne(Long id) {
        log.debug("Request to get EvaluationItems : {}", id);
        EvaluationItems evaluationItems = evaluationItemsRepository.findOne(id);
        return evaluationItems;
    }

    /**
     *  Delete the  evaluationItems by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluationItems : {}", id);
      EvaluationItems evaluationItems = evaluationItemsRepository.findOne(id);
      evaluationItems.setDelStatus(true);
      EvaluationItems result = evaluationItemsRepository.save(evaluationItems);
      
      evaluationItemsSearchRepository.save(result);
    }

    /**
     * Search for the evaluationItems corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationItems> search(String query) {
        log.debug("Request to search EvaluationItems for query {}", query);
        return StreamSupport
            .stream(evaluationItemsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
