package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Bed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Bed entity.
 */
public interface BedSearchRepository extends ElasticsearchRepository<Bed, Long> {
}
