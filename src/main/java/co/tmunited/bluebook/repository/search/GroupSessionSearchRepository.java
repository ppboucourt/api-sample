package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.GroupSession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupSession entity.
 */
public interface GroupSessionSearchRepository extends ElasticsearchRepository<GroupSession, Long> {
}
