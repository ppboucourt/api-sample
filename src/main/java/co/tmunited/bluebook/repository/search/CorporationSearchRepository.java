package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Corporation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Corporation entity.
 */
public interface CorporationSearchRepository extends ElasticsearchRepository<Corporation, Long> {
}
