package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.EvaluationContent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EvaluationContent entity.
 */
public interface EvaluationContentSearchRepository extends ElasticsearchRepository<EvaluationContent, Long> {
}
