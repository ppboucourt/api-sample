package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TreatmentHistoryService;
import co.tmunited.bluebook.domain.TreatmentHistory;
import co.tmunited.bluebook.repository.TreatmentHistoryRepository;
import co.tmunited.bluebook.repository.search.TreatmentHistorySearchRepository;
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
 * Service Implementation for managing TreatmentHistory.
 */
@Service
@Transactional
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService{

    private final Logger log = LoggerFactory.getLogger(TreatmentHistoryServiceImpl.class);

    @Inject
    private TreatmentHistoryRepository treatmentHistoryRepository;

    @Inject
    private TreatmentHistorySearchRepository treatmentHistorySearchRepository;

    /**
     * Save a treatmentHistory.
     *
     * @param treatmentHistory the entity to save
     * @return the persisted entity
     */
    public TreatmentHistory save(TreatmentHistory treatmentHistory) {
        log.debug("Request to save TreatmentHistory : {}", treatmentHistory);
        TreatmentHistory result = treatmentHistoryRepository.save(treatmentHistory);
        treatmentHistorySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the treatmentHistories.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TreatmentHistory> findAll() {
        log.debug("Request to get all TreatmentHistories");
        List<TreatmentHistory> result = treatmentHistoryRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one treatmentHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TreatmentHistory findOne(Long id) {
        log.debug("Request to get TreatmentHistory : {}", id);
        TreatmentHistory treatmentHistory = treatmentHistoryRepository.findOne(id);
        return treatmentHistory;
    }

    /**
     *  Delete the  treatmentHistory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentHistory : {}", id);
      TreatmentHistory treatmentHistory = treatmentHistoryRepository.findOne(id);
      treatmentHistory.setDelStatus(true);
      TreatmentHistory result = treatmentHistoryRepository.save(treatmentHistory);

      treatmentHistorySearchRepository.save(result);
    }

    /**
     * Search for the treatmentHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TreatmentHistory> search(String query) {
        log.debug("Request to search TreatmentHistories for query {}", query);
        return StreamSupport
            .stream(treatmentHistorySearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
