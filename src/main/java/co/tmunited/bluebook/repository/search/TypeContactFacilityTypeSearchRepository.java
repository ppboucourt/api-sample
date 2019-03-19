package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeContactFacilityType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeContactFacilityType entity.
 */
public interface TypeContactFacilityTypeSearchRepository extends ElasticsearchRepository<TypeContactFacilityType, Long> {
}
