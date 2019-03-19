package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.OrderComponent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderComponent entity.
 */
public interface OrderComponentSearchRepository extends ElasticsearchRepository<OrderComponent, Long> {
}
