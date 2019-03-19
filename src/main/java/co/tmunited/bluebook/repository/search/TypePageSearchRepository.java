package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePage entity.
 */
public interface TypePageSearchRepository extends ElasticsearchRepository<TypePage, Long> {
}