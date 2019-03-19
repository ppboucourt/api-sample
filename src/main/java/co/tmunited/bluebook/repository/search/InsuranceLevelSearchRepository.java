package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.InsuranceLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsuranceLevel entity.
 */
public interface InsuranceLevelSearchRepository extends ElasticsearchRepository<InsuranceLevel, Long> {
}
