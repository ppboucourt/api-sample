package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientAction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientAction entity.
 */
public interface PatientActionSearchRepository extends ElasticsearchRepository<PatientAction, Long> {
}
