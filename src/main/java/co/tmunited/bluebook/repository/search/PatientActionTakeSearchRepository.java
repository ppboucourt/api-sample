package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientActionTake;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientActionTake entity.
 */
public interface PatientActionTakeSearchRepository extends ElasticsearchRepository<PatientActionTake, Long> {
}
