package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ReportDetailsService;
import co.tmunited.bluebook.domain.ReportDetails;
import co.tmunited.bluebook.repository.ReportDetailsRepository;
import co.tmunited.bluebook.repository.search.ReportDetailsSearchRepository;
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
 * Service Implementation for managing ReportDetails.
 */
@Service
@Transactional
public class ReportDetailsServiceImpl implements ReportDetailsService{

    private final Logger log = LoggerFactory.getLogger(ReportDetailsServiceImpl.class);
    
    @Inject
    private ReportDetailsRepository reportDetailsRepository;

    @Inject
    private ReportDetailsSearchRepository reportDetailsSearchRepository;

    /**
     * Save a reportDetails.
     *
     * @param reportDetails the entity to save
     * @return the persisted entity
     */
    public ReportDetails save(ReportDetails reportDetails) {
        log.debug("Request to save ReportDetails : {}", reportDetails);
        ReportDetails result = reportDetailsRepository.save(reportDetails);
        reportDetailsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the reportDetails.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ReportDetails> findAll() {
        log.debug("Request to get all ReportDetails");
        List<ReportDetails> result = reportDetailsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one reportDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReportDetails findOne(Long id) {
        log.debug("Request to get ReportDetails : {}", id);
        ReportDetails reportDetails = reportDetailsRepository.findOne(id);
        return reportDetails;
    }

    /**
     *  Delete the  reportDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportDetails : {}", id);
      ReportDetails reportDetails = reportDetailsRepository.findOne(id);
      reportDetails.setDelStatus(true);
      ReportDetails result = reportDetailsRepository.save(reportDetails);
      
      reportDetailsSearchRepository.save(result);
    }

    /**
     * Search for the reportDetails corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReportDetails> search(String query) {
        log.debug("Request to search ReportDetails for query {}", query);
        return StreamSupport
            .stream(reportDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
