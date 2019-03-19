package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePaymentMethods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePaymentMethods entity.
 */
public interface TypePaymentMethodsSearchRepository extends ElasticsearchRepository<TypePaymentMethods, Long> {
}
