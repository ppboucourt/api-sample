package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Signature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Signature entity.
 */
public interface SignatureSearchRepository extends ElasticsearchRepository<Signature, Long> {
}
