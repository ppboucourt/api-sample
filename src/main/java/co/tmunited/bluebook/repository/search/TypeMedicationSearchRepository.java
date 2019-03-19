package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeMedication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeMedication entity.
 */
public interface TypeMedicationSearchRepository extends ElasticsearchRepository<TypeMedication, Long> {
}
