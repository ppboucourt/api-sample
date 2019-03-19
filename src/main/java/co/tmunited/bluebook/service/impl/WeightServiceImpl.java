package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.WeightService;
import co.tmunited.bluebook.domain.Weight;
import co.tmunited.bluebook.repository.WeightRepository;
import co.tmunited.bluebook.repository.search.WeightSearchRepository;
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
 * Service Implementation for managing Weight.
 */
@Service
@Transactional
public class WeightServiceImpl implements WeightService{

    private final Logger log = LoggerFactory.getLogger(WeightServiceImpl.class);

    @Inject
    private WeightRepository weightRepository;

    @Inject
    private WeightSearchRepository weightSearchRepository;

    /**
     * Save a weight.
     *
     * @param weight the entity to save
     * @return the persisted entity
     */
    public Weight save(Weight weight) {
        log.debug("Request to save Weight : {}", weight);
        Weight result = weightRepository.save(weight);
        weightSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the weights.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Weight> findAll() {
        log.debug("Request to get all Weights");
        List<Weight> result = weightRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one weight by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Weight findOne(Long id) {
        log.debug("Request to get Weight : {}", id);
        Weight weight = weightRepository.findOne(id);
        return weight;
    }

    /**
     *  Delete the  weight by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Weight : {}", id);
      Weight weight = weightRepository.findOne(id);
      weight.setDelStatus(true);
      Weight result = weightRepository.save(weight);

      weightSearchRepository.save(result);
    }

    /**
     * Search for the weight corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Weight> search(String query) {
        log.debug("Request to search Weights for query {}", query);
        return StreamSupport
            .stream(weightSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<Weight> findByChart(Long id) {
        log.debug("Request to search Weights by chartId {}", id);
        List<Weight> result = weightRepository.findAllByDelStatusIsFalseAndChartId(id);

        return result;
    }
}
