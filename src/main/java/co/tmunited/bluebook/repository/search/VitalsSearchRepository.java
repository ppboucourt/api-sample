package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Vitals;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Vitals entity.
 */
public interface VitalsSearchRepository extends ElasticsearchRepository<Vitals, Long> {
}
