package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ChartToActionService;
import co.tmunited.bluebook.domain.ChartToAction;
import co.tmunited.bluebook.repository.ChartToActionRepository;
import co.tmunited.bluebook.repository.search.ChartToActionSearchRepository;
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
 * Service Implementation for managing ChartToAction.
 */
@Service
@Transactional
public class ChartToActionServiceImpl implements ChartToActionService{

    private final Logger log = LoggerFactory.getLogger(ChartToActionServiceImpl.class);
    
    @Inject
    private ChartToActionRepository chartToActionRepository;

    @Inject
    private ChartToActionSearchRepository chartToActionSearchRepository;

    /**
     * Save a chartToAction.
     *
     * @param chartToAction the entity to save
     * @return the persisted entity
     */
    public ChartToAction save(ChartToAction chartToAction) {
        log.debug("Request to save ChartToAction : {}", chartToAction);
        ChartToAction result = chartToActionRepository.save(chartToAction);
        chartToActionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chartToActions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ChartToAction> findAll() {
        log.debug("Request to get all ChartToActions");
        List<ChartToAction> result = chartToActionRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one chartToAction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ChartToAction findOne(Long id) {
        log.debug("Request to get ChartToAction : {}", id);
        ChartToAction chartToAction = chartToActionRepository.findOne(id);
        return chartToAction;
    }

    /**
     *  Delete the  chartToAction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToAction : {}", id);
      ChartToAction chartToAction = chartToActionRepository.findOne(id);
      chartToAction.setDelStatus(true);
      ChartToAction result = chartToActionRepository.save(chartToAction);
      
      chartToActionSearchRepository.save(result);
    }

    /**
     * Search for the chartToAction corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToAction> search(String query) {
        log.debug("Request to search ChartToActions for query {}", query);
        return StreamSupport
            .stream(chartToActionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
