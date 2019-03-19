package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.XMLConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the XMLConfig entity.
 */
public interface XMLConfigSearchRepository extends ElasticsearchRepository<XMLConfig, Long> {
}
