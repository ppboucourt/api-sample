package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.EvaluationItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EvaluationItems entity.
 */
public interface EvaluationItemsSearchRepository extends ElasticsearchRepository<EvaluationItems, Long> {
}
