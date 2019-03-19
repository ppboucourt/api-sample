package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ServiceProvider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ServiceProvider entity.
 */
public interface ServiceProviderSearchRepository extends ElasticsearchRepository<ServiceProvider, Long> {
}
