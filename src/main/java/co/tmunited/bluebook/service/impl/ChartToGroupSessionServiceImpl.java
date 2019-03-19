package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ChartToGroupSessionService;
import co.tmunited.bluebook.domain.ChartToGroupSession;
import co.tmunited.bluebook.repository.ChartToGroupSessionRepository;
import co.tmunited.bluebook.repository.search.ChartToGroupSessionSearchRepository;
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
 * Service Implementation for managing ChartToGroupSession.
 */
@Service
@Transactional
public class ChartToGroupSessionServiceImpl implements ChartToGroupSessionService{

    private final Logger log = LoggerFactory.getLogger(ChartToGroupSessionServiceImpl.class);

    @Inject
    private ChartToGroupSessionRepository chartToGroupSessionRepository;

    @Inject
    private ChartToGroupSessionSearchRepository chartToGroupSessionSearchRepository;

    /**
     * Save a chartToGroupSession.
     *
     * @param chartToGroupSession the entity to save
     * @return the persisted entity
     */
    public ChartToGroupSession save(ChartToGroupSession chartToGroupSession) {
        log.debug("Request to save ChartToGroupSession : {}", chartToGroupSession);
        ChartToGroupSession result = chartToGroupSessionRepository.save(chartToGroupSession);
        chartToGroupSessionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chartToGroupSessions.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToGroupSession> findAll() {
        log.debug("Request to get all ChartToGroupSessions");
        List<ChartToGroupSession> result = chartToGroupSessionRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one chartToGroupSession by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChartToGroupSession findOne(Long id) {
        log.debug("Request to get ChartToGroupSession : {}", id);
        ChartToGroupSession chartToGroupSession = chartToGroupSessionRepository.findOne(id);
        return chartToGroupSession;
    }

    /**
     *  Delete the  chartToGroupSession by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToGroupSession : {}", id);
      ChartToGroupSession chartToGroupSession = chartToGroupSessionRepository.findOne(id);
      chartToGroupSession.setDelStatus(true);
      ChartToGroupSession result = chartToGroupSessionRepository.save(chartToGroupSession);

      chartToGroupSessionSearchRepository.save(result);
    }

    /**
     * Search for the chartToGroupSession corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToGroupSession> search(String query) {
        log.debug("Request to search ChartToGroupSessions for query {}", query);
        return StreamSupport
            .stream(chartToGroupSessionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
