package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Shifts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Shifts entity.
 */
public interface ShiftsSearchRepository extends ElasticsearchRepository<Shifts, Long> {
}
