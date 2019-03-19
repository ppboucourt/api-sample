package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Orders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Orders entity.
 */
public interface OrdersSearchRepository extends ElasticsearchRepository<Orders, Long> {
}
