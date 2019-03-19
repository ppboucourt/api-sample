package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientMedication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientMedication entity.
 */
public interface PatientMedicationSearchRepository extends ElasticsearchRepository<PatientMedication, Long> {
}
