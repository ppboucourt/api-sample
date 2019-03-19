package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ChartToIcd10Service;
import co.tmunited.bluebook.domain.ChartToIcd10;
import co.tmunited.bluebook.repository.ChartToIcd10Repository;
import co.tmunited.bluebook.repository.search.ChartToIcd10SearchRepository;
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
 * Service Implementation for managing ChartToIcd10.
 */
@Service
@Transactional
public class ChartToIcd10ServiceImpl implements ChartToIcd10Service{

    private final Logger log = LoggerFactory.getLogger(ChartToIcd10ServiceImpl.class);

    @Inject
    private ChartToIcd10Repository chartToIcd10Repository;

    @Inject
    private ChartToIcd10SearchRepository chartToIcd10SearchRepository;

    /**
     * Save a chartToIcd10.
     *
     * @param chartToIcd10 the entity to save
     * @return the persisted entity
     */
    public ChartToIcd10 save(ChartToIcd10 chartToIcd10) {
        log.debug("Request to save ChartToIcd10 : {}", chartToIcd10);
        ChartToIcd10 result = chartToIcd10Repository.save(chartToIcd10);
        chartToIcd10SearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chartToIcd10S.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToIcd10> findAll() {
        log.debug("Request to get all ChartToIcd10S");
        List<ChartToIcd10> result = chartToIcd10Repository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one chartToIcd10 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChartToIcd10 findOne(Long id) {
        log.debug("Request to get ChartToIcd10 : {}", id);
        ChartToIcd10 chartToIcd10 = chartToIcd10Repository.findOne(id);
        return chartToIcd10;
    }

    /**
     *  Delete the  chartToIcd10 by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToIcd10 : {}", id);
      ChartToIcd10 chartToIcd10 = chartToIcd10Repository.findOne(id);
      chartToIcd10.setDelStatus(true);
      ChartToIcd10 result = chartToIcd10Repository.save(chartToIcd10);

      chartToIcd10SearchRepository.save(result);
    }

    /**
     * Search for the chartToIcd10 corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToIcd10> search(String query) {
        log.debug("Request to search ChartToIcd10S for query {}", query);
        return StreamSupport
            .stream(chartToIcd10SearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the problemLists.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToIcd10> findAllByPatient(Long id) {
        log.debug("Request to get all ProblemLists");
//        List<ChartToIcd10> result = chartToIcd10Repository.findAllByChartIdAndDelStatusIsFalseOrderByIsActive(id);
        List<ChartToIcd10> result = chartToIcd10Repository.findAllByChartIdAndDelStatusIsFalse(id);

        return result;
    }

    @Transactional(readOnly = true)
    public List<ChartToIcd10> findAllActiveByPatient(Long id){
        log.debug("Request to get all ProblemLists");
//        List<ChartToIcd10> result = chartToIcd10Repository.findAllByChartIdAndDelStatusIsFalseAndIsActiveIsTrue(id);

        return null;
    }
}
