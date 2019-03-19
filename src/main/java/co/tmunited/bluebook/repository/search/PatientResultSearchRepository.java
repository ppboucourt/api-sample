package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientResult entity.
 */
public interface PatientResultSearchRepository extends ElasticsearchRepository<PatientResult, Long> {
}
