package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Actions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Actions entity.
 */
public interface ActionsSearchRepository extends ElasticsearchRepository<Actions, Long> {
}
