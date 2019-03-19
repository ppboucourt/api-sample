package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientOrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientOrderItem entity.
 */
public interface PatientOrderItemSearchRepository extends ElasticsearchRepository<PatientOrderItem, Long> {
}
