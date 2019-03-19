package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Drugs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Drugs entity.
 */
public interface DrugsSearchRepository extends ElasticsearchRepository<Drugs, Long> {
}
