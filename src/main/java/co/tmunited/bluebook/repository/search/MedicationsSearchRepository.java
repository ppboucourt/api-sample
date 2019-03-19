package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Medications;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Medications entity.
 */
public interface MedicationsSearchRepository extends ElasticsearchRepository<Medications, Long> {
}
