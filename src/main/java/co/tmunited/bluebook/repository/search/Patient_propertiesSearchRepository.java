package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Patient_properties;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Patient_properties entity.
 */
public interface Patient_propertiesSearchRepository extends ElasticsearchRepository<Patient_properties, Long> {
}
