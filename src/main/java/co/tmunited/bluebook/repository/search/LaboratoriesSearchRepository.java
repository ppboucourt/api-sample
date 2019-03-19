package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Laboratories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Laboratories entity.
 */
public interface LaboratoriesSearchRepository extends ElasticsearchRepository<Laboratories, Long> {
}
