package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.UrgentIssues;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UrgentIssues entity.
 */
public interface UrgentIssuesSearchRepository extends ElasticsearchRepository<UrgentIssues, Long> {
}
