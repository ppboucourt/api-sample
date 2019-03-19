package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Fields;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Fields entity.
 */
public interface FieldsSearchRepository extends ElasticsearchRepository<Fields, Long> {
}
