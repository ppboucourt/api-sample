package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Order_type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Order_type entity.
 */
public interface Order_typeSearchRepository extends ElasticsearchRepository<Order_type, Long> {
}
