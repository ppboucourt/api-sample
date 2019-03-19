package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePerson entity.
 */
public interface TypePersonSearchRepository extends ElasticsearchRepository<TypePerson, Long> {
}
