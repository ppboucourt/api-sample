package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeEvaluation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeEvaluation entity.
 */
public interface TypeEvaluationSearchRepository extends ElasticsearchRepository<TypeEvaluation, Long> {
}
