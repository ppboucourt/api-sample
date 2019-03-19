package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Glucose;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Glucose entity.
 */
public interface GlucoseSearchRepository extends ElasticsearchRepository<Glucose, Long> {
}
