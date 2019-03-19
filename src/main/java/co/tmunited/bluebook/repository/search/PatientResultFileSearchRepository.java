package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientResultFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientResultFile entity.
 */
public interface PatientResultFileSearchRepository extends ElasticsearchRepository<PatientResultFile, Long> {
}
