package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.EvaluationContentService;
import co.tmunited.bluebook.domain.EvaluationContent;
import co.tmunited.bluebook.repository.EvaluationContentRepository;
import co.tmunited.bluebook.repository.search.EvaluationContentSearchRepository;
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
 * Service Implementation for managing EvaluationContent.
 */
@Service
@Transactional
public class EvaluationContentServiceImpl implements EvaluationContentService{

    private final Logger log = LoggerFactory.getLogger(EvaluationContentServiceImpl.class);
    
    @Inject
    private EvaluationContentRepository evaluationContentRepository;

    @Inject
    private EvaluationContentSearchRepository evaluationContentSearchRepository;

    /**
     * Save a evaluationContent.
     *
     * @param evaluationContent the entity to save
     * @return the persisted entity
     */
    public EvaluationContent save(EvaluationContent evaluationContent) {
        log.debug("Request to save EvaluationContent : {}", evaluationContent);
        EvaluationContent result = evaluationContentRepository.save(evaluationContent);
        evaluationContentSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the evaluationContents.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EvaluationContent> findAll() {
        log.debug("Request to get all EvaluationContents");
        List<EvaluationContent> result = evaluationContentRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one evaluationContent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EvaluationContent findOne(Long id) {
        log.debug("Request to get EvaluationContent : {}", id);
        EvaluationContent evaluationContent = evaluationContentRepository.findOne(id);
        return evaluationContent;
    }

    /**
     *  Delete the  evaluationContent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluationContent : {}", id);
      EvaluationContent evaluationContent = evaluationContentRepository.findOne(id);
      evaluationContent.setDelStatus(true);
      EvaluationContent result = evaluationContentRepository.save(evaluationContent);
      
      evaluationContentSearchRepository.save(result);
    }

    /**
     * Search for the evaluationContent corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationContent> search(String query) {
        log.debug("Request to search EvaluationContents for query {}", query);
        return StreamSupport
            .stream(evaluationContentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
