package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ReportsService;
import co.tmunited.bluebook.domain.Reports;
import co.tmunited.bluebook.repository.ReportsRepository;
import co.tmunited.bluebook.repository.search.ReportsSearchRepository;
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
 * Service Implementation for managing Reports.
 */
@Service
@Transactional
public class ReportsServiceImpl implements ReportsService{

    private final Logger log = LoggerFactory.getLogger(ReportsServiceImpl.class);
    
    @Inject
    private ReportsRepository reportsRepository;

    @Inject
    private ReportsSearchRepository reportsSearchRepository;

    /**
     * Save a reports.
     *
     * @param reports the entity to save
     * @return the persisted entity
     */
    public Reports save(Reports reports) {
        log.debug("Request to save Reports : {}", reports);
        Reports result = reportsRepository.save(reports);
        reportsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the reports.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Reports> findAll() {
        log.debug("Request to get all Reports");
        List<Reports> result = reportsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one reports by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Reports findOne(Long id) {
        log.debug("Request to get Reports : {}", id);
        Reports reports = reportsRepository.findOne(id);
        return reports;
    }

    /**
     *  Delete the  reports by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reports : {}", id);
      Reports reports = reportsRepository.findOne(id);
      reports.setDelStatus(true);
      Reports result = reportsRepository.save(reports);
      
      reportsSearchRepository.save(result);
    }

    /**
     * Search for the reports corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Reports> search(String query) {
        log.debug("Request to search Reports for query {}", query);
        return StreamSupport
            .stream(reportsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
