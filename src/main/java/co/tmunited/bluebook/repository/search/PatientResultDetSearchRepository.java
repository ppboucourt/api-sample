package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientResultDet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientResultDet entity.
 */
public interface PatientResultDetSearchRepository extends ElasticsearchRepository<PatientResultDet, Long> {
}
