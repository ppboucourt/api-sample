package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ConcurrentReviews;

import java.util.List;

/**
 * Service Interface for managing ConcurrentReviews.
 */
public interface ConcurrentReviewsService {

    /**
     * Save a concurrentReviews.
     *
     * @param concurrentReviews the entity to save
     * @return the persisted entity
     */
    ConcurrentReviews save(ConcurrentReviews concurrentReviews);

    /**
     *  Get all the concurrentReviews.
     *
     *  @return the list of entities
     */
    List<ConcurrentReviews> findAll();

    /**
     *  Get the "id" concurrentReviews.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConcurrentReviews findOne(Long id);

    /**
     *  Delete the "id" concurrentReviews.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the concurrentReviews corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ConcurrentReviews> search(String query);



    List<ConcurrentReviews> findAllByDelStatusIsFalseAndChartConcurrentReviewsById(Long id);
}
