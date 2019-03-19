package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.CountryState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CountryState entity.
 */
public interface CountryStateSearchRepository extends ElasticsearchRepository<CountryState, Long> {
}
