package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ConcurrentReviewsService;
import co.tmunited.bluebook.domain.ConcurrentReviews;
import co.tmunited.bluebook.repository.ConcurrentReviewsRepository;
import co.tmunited.bluebook.repository.search.ConcurrentReviewsSearchRepository;
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
 * Service Implementation for managing ConcurrentReviews.
 */
@Service
@Transactional
public class ConcurrentReviewsServiceImpl implements ConcurrentReviewsService{

    private final Logger log = LoggerFactory.getLogger(ConcurrentReviewsServiceImpl.class);

    @Inject
    private ConcurrentReviewsRepository concurrentReviewsRepository;

    @Inject
    private ConcurrentReviewsSearchRepository concurrentReviewsSearchRepository;

    /**
     * Save a concurrentReviews.
     *
     * @param concurrentReviews the entity to save
     * @return the persisted entity
     */
    public ConcurrentReviews save(ConcurrentReviews concurrentReviews) {
        log.debug("Request to save ConcurrentReviews : {}", concurrentReviews);
        ConcurrentReviews result = concurrentReviewsRepository.save(concurrentReviews);
        concurrentReviewsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the concurrentReviews.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConcurrentReviews> findAll() {
        log.debug("Request to get all ConcurrentReviews");
        List<ConcurrentReviews> result = concurrentReviewsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one concurrentReviews by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ConcurrentReviews findOne(Long id) {
        log.debug("Request to get ConcurrentReviews : {}", id);
        ConcurrentReviews concurrentReviews = concurrentReviewsRepository.findOne(id);
        return concurrentReviews;
    }

    /**
     *  Delete the  concurrentReviews by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConcurrentReviews : {}", id);
      ConcurrentReviews concurrentReviews = concurrentReviewsRepository.findOne(id);
      concurrentReviews.setDelStatus(true);
      ConcurrentReviews result = concurrentReviewsRepository.save(concurrentReviews);

      concurrentReviewsSearchRepository.save(result);
    }

    /**
     * Search for the concurrentReviews corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConcurrentReviews> search(String query) {
        log.debug("Request to search ConcurrentReviews for query {}", query);
        return StreamSupport
            .stream(concurrentReviewsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConcurrentReviews> findAllByDelStatusIsFalseAndChartConcurrentReviewsById(Long id){
        List<ConcurrentReviews> concurrentReviews = concurrentReviewsRepository.findAllByDelStatusIsFalseAndChartConcurrentReviewsId(id);
        return concurrentReviews;
    }
}
