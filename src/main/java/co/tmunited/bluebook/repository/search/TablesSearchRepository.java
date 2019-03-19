package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Tables;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tables entity.
 */
public interface TablesSearchRepository extends ElasticsearchRepository<Tables, Long> {
}
