package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientMedicationTake;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientMedicationTake entity.
 */
public interface PatientMedicationTakeSearchRepository extends ElasticsearchRepository<PatientMedicationTake, Long> {
}
