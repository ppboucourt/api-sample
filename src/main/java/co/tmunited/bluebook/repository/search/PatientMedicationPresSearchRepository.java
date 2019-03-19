package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.PatientMedicationPres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PatientMedicationPres entity.
 */
public interface PatientMedicationPresSearchRepository extends ElasticsearchRepository<PatientMedicationPres, Long> {
}
