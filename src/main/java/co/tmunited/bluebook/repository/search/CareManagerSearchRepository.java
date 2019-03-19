package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.CareManager;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CareManager entity.
 */
public interface CareManagerSearchRepository extends ElasticsearchRepository<CareManager, Long> {
}
