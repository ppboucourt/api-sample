package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeEmployee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeEmployee entity.
 */
public interface TypeEmployeeSearchRepository extends ElasticsearchRepository<TypeEmployee, Long> {
}
