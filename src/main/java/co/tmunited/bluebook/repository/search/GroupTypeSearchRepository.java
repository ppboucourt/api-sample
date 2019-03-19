package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.GroupType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupType entity.
 */
public interface GroupTypeSearchRepository extends ElasticsearchRepository<GroupType, Long> {
}
