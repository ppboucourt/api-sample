package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Hospitalization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Hospitalization entity.
 */
public interface HospitalizationSearchRepository extends ElasticsearchRepository<Hospitalization, Long> {
}
