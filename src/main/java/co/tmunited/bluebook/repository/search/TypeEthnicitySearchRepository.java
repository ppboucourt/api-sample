package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeEthnicity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeEthnicity entity.
 */
public interface TypeEthnicitySearchRepository extends ElasticsearchRepository<TypeEthnicity, Long> {
}
