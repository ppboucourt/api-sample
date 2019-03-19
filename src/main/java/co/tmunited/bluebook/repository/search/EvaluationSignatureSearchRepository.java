package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.EvaluationSignature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EvaluationSignature entity.
 */
public interface EvaluationSignatureSearchRepository extends ElasticsearchRepository<EvaluationSignature, Long> {
}
