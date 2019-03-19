package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.vo.ChartToLevelCareVO;
import co.tmunited.bluebook.service.ChartToLevelCareService;
import co.tmunited.bluebook.domain.ChartToLevelCare;
import co.tmunited.bluebook.repository.ChartToLevelCareRepository;
import co.tmunited.bluebook.repository.search.ChartToLevelCareSearchRepository;
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
 * Service Implementation for managing ChartToLevelCare.
 */
@Service
@Transactional
public class ChartToLevelCareServiceImpl implements ChartToLevelCareService{

    private final Logger log = LoggerFactory.getLogger(ChartToLevelCareServiceImpl.class);

    @Inject
    private ChartToLevelCareRepository chartToLevelCareRepository;

    @Inject
    private ChartToLevelCareSearchRepository chartToLevelCareSearchRepository;

    /**
     * Save a chartToLevelCare.
     *
     * @param chartToLevelCare the entity to save
     * @return the persisted entity
     */
    public ChartToLevelCare save(ChartToLevelCare chartToLevelCare) {
        log.debug("Request to save ChartToLevelCare : {}", chartToLevelCare);
        ChartToLevelCare result = chartToLevelCareRepository.save(chartToLevelCare);
        chartToLevelCareSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chartToLevelCares.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToLevelCare> findAll() {
        log.debug("Request to get all ChartToLevelCares");
        List<ChartToLevelCare> result = chartToLevelCareRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one chartToLevelCare by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChartToLevelCare findOne(Long id) {
        log.debug("Request to get ChartToLevelCare : {}", id);
        ChartToLevelCare chartToLevelCare = chartToLevelCareRepository.findOne(id);
        return chartToLevelCare;
    }

    /**
     *  Delete the  chartToLevelCare by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToLevelCare : {}", id);
      ChartToLevelCare chartToLevelCare = chartToLevelCareRepository.findOne(id);
      chartToLevelCare.setDelStatus(true);
      ChartToLevelCare result = chartToLevelCareRepository.save(chartToLevelCare);

      chartToLevelCareSearchRepository.save(result);
    }

    /**
     * Search for the chartToLevelCare corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToLevelCare> search(String query) {
        log.debug("Request to search ChartToLevelCares for query {}", query);
        return StreamSupport
            .stream(chartToLevelCareSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search all levelCare assigned to a chart
     *
     * @param id
     * @return
     */
    public List<ChartToLevelCareVO> findByChart(Long id) {
        List<ChartToLevelCareVO> result = chartToLevelCareRepository.findLevelCaresByChart(id);
        return result;
    }

    /**
     * Search all levelCare assigned to a chart
     *
     * @param id
     * @return
     */
    public ChartToLevelCare findLastByChart(Long id) {
        ChartToLevelCare result = chartToLevelCareRepository.findLastLevelCaresByChart(id);
        return result;
    }
}
