package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeLevelCare;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeLevelCare entity.
 */
public interface TypeLevelCareSearchRepository extends ElasticsearchRepository<TypeLevelCare, Long> {
}
