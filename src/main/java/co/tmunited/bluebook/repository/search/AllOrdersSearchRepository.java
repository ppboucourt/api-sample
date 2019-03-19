package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.AllOrders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AllOrders entity.
 */
public interface AllOrdersSearchRepository extends ElasticsearchRepository<AllOrders, Long> {
}
