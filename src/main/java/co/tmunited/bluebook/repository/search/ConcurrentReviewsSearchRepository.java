package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ConcurrentReviews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ConcurrentReviews entity.
 */
public interface ConcurrentReviewsSearchRepository extends ElasticsearchRepository<ConcurrentReviews, Long> {
}
