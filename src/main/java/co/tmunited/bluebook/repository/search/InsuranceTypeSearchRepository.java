package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.InsuranceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsuranceType entity.
 */
public interface InsuranceTypeSearchRepository extends ElasticsearchRepository<InsuranceType, Long> {
}
