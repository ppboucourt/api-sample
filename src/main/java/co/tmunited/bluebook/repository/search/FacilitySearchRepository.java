package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Facility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Facility entity.
 */
public interface FacilitySearchRepository extends ElasticsearchRepository<Facility, Long> {
}
