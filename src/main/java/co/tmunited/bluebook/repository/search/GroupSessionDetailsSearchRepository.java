package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.GroupSessionDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupSessionDetails entity.
 */
public interface GroupSessionDetailsSearchRepository extends ElasticsearchRepository<GroupSessionDetails, Long> {
}
