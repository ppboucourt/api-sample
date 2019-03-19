package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Routes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Routes entity.
 */
public interface RoutesSearchRepository extends ElasticsearchRepository<Routes, Long> {
}
