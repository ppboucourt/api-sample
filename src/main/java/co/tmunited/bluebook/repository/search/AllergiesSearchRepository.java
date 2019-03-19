package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Allergies;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Allergies entity.
 */
public interface AllergiesSearchRepository extends ElasticsearchRepository<Allergies, Long> {
}
