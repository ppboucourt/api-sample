package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientOrder entity.
 */
public interface PatientOrderSearchRepository extends ElasticsearchRepository<PatientOrder, Long> {
}
