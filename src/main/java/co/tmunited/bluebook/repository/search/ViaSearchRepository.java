package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Via;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Via entity.
 */
public interface ViaSearchRepository extends ElasticsearchRepository<Via, Long> {
}
