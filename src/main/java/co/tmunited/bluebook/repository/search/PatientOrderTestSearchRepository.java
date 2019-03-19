package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientOrderTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientOrderTest entity.
 */
public interface PatientOrderTestSearchRepository extends ElasticsearchRepository<PatientOrderTest, Long> {
}
