package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Payer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Payer entity.
 */
public interface PayerSearchRepository extends ElasticsearchRepository<Payer, Long> {
}
