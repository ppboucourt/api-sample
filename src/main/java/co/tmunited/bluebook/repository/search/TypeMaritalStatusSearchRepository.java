package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeMaritalStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeMaritalStatus entity.
 */
public interface TypeMaritalStatusSearchRepository extends ElasticsearchRepository<TypeMaritalStatus, Long> {
}
