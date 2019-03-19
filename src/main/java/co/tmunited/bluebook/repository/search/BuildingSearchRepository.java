package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Building;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Building entity.
 */
public interface BuildingSearchRepository extends ElasticsearchRepository<Building, Long> {
}
