package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.UrgentIssuesService;
import co.tmunited.bluebook.domain.UrgentIssues;
import co.tmunited.bluebook.repository.UrgentIssuesRepository;
import co.tmunited.bluebook.repository.search.UrgentIssuesSearchRepository;
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
 * Service Implementation for managing UrgentIssues.
 */
@Service
@Transactional
public class UrgentIssuesServiceImpl implements UrgentIssuesService{

    private final Logger log = LoggerFactory.getLogger(UrgentIssuesServiceImpl.class);

    @Inject
    private UrgentIssuesRepository urgentIssuesRepository;

    @Inject
    private UrgentIssuesSearchRepository urgentIssuesSearchRepository;

    /**
     * Save a urgentIssues.
     *
     * @param urgentIssues the entity to save
     * @return the persisted entity
     */
    public UrgentIssues save(UrgentIssues urgentIssues) {
        log.debug("Request to save UrgentIssues : {}", urgentIssues);
        UrgentIssues result = urgentIssuesRepository.save(urgentIssues);
        urgentIssuesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the urgentIssues.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UrgentIssues> findAll() {
        log.debug("Request to get all UrgentIssues");
        List<UrgentIssues> result = urgentIssuesRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one urgentIssues by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UrgentIssues findOne(Long id) {
        log.debug("Request to get UrgentIssues : {}", id);
        UrgentIssues urgentIssues = urgentIssuesRepository.findOneWithEagerRelationships(id);
        return urgentIssues;
    }

    /**
     *  Delete the  urgentIssues by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UrgentIssues : {}", id);
        UrgentIssues urgentIssues = urgentIssuesRepository.findOne(id);
        urgentIssues.setDelStatus(true);
        UrgentIssues result = urgentIssuesRepository.save(urgentIssues);

        urgentIssuesSearchRepository.save(result);
    }

    /**
     * Search for the urgentIssues corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UrgentIssues> search(String query) {
        log.debug("Request to search UrgentIssues for query {}", query);
        return StreamSupport
            .stream(urgentIssuesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<UrgentIssues> findAllByChart(Long id) {
        log.debug("Request to get a UrgentIssues by chart : {}", id);
        List<UrgentIssues> result = urgentIssuesRepository.findAllByDelStatusIsFalseAndChartId(id);
//        result.stream().map(x -> {
//           x.getEmployees().size();
//           return x;
//        });
        return result;
    }

    @Override
    public List<UrgentIssues> findAllByChartAndEmployee(Long chartId, Long empId) {
        log.debug("Request to get a UrgentIssues by chart and employee : {}", chartId + ' ' + empId);
        List<UrgentIssues> result = urgentIssuesRepository.findAllByDelStatusIsFalseAndChartIdAndEmployeeId(chartId, empId);
        return result;
    }
}
