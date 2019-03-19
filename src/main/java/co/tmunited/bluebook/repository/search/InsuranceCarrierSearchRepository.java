package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.InsuranceCarrier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsuranceCarrier entity.
 */
public interface InsuranceCarrierSearchRepository extends ElasticsearchRepository<InsuranceCarrier, Long> {
}
