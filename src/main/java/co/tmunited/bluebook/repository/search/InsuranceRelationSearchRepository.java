package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.InsuranceRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsuranceRelation entity.
 */
public interface InsuranceRelationSearchRepository extends ElasticsearchRepository<InsuranceRelation, Long> {
}
