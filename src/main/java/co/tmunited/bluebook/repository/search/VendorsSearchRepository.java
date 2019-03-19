package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Vendors;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Vendors entity.
 */
public interface VendorsSearchRepository extends ElasticsearchRepository<Vendors, Long> {
}
