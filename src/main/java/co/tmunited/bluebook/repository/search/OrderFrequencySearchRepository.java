package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.OrderFrequency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderFrequency entity.
 */
public interface OrderFrequencySearchRepository extends ElasticsearchRepository<OrderFrequency, Long> {
}
