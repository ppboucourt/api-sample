package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.EvaluationTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EvaluationTemplate entity.
 */
public interface EvaluationTemplateSearchRepository extends ElasticsearchRepository<EvaluationTemplate, Long> {
}
