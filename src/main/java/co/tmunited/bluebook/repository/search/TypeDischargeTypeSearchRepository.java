package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeDischargeType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeDischargeType entity.
 */
public interface TypeDischargeTypeSearchRepository extends ElasticsearchRepository<TypeDischargeType, Long> {
}
