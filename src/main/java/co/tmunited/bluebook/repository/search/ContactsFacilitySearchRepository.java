package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ContactsFacility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ContactsFacility entity.
 */
public interface ContactsFacilitySearchRepository extends ElasticsearchRepository<ContactsFacility, Long> {
}
