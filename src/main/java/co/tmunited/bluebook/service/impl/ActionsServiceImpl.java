package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ActionsService;
import co.tmunited.bluebook.domain.Actions;
import co.tmunited.bluebook.repository.ActionsRepository;
import co.tmunited.bluebook.repository.search.ActionsSearchRepository;
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
 * Service Implementation for managing Actions.
 */
@Service
@Transactional
public class ActionsServiceImpl implements ActionsService{

    private final Logger log = LoggerFactory.getLogger(ActionsServiceImpl.class);
    
    @Inject
    private ActionsRepository actionsRepository;

    @Inject
    private ActionsSearchRepository actionsSearchRepository;

    /**
     * Save a actions.
     *
     * @param actions the entity to save
     * @return the persisted entity
     */
    public Actions save(Actions actions) {
        log.debug("Request to save Actions : {}", actions);
        Actions result = actionsRepository.save(actions);
        actionsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the actions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Actions> findAll() {
        log.debug("Request to get all Actions");
        List<Actions> result = actionsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one actions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Actions findOne(Long id) {
        log.debug("Request to get Actions : {}", id);
        Actions actions = actionsRepository.findOne(id);
        return actions;
    }

    /**
     *  Delete the  actions by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Actions : {}", id);
      Actions actions = actionsRepository.findOne(id);
      actions.setDelStatus(true);
      Actions result = actionsRepository.save(actions);
      
      actionsSearchRepository.save(result);
    }

    /**
     * Search for the actions corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Actions> search(String query) {
        log.debug("Request to search Actions for query {}", query);
        return StreamSupport
            .stream(actionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
