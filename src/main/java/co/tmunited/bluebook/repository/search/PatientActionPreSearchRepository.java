package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientActionPre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientActionPre entity.
 */
public interface PatientActionPreSearchRepository extends ElasticsearchRepository<PatientActionPre, Long> {
}
