package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Diet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Diet entity.
 */
public interface DietSearchRepository extends ElasticsearchRepository<Diet, Long> {
}
