package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientToShift;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientToShift entity.
 */
public interface PatientToShiftSearchRepository extends ElasticsearchRepository<PatientToShift, Long> {
}
