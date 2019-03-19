package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePreAdmissionStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePreAdmissionStatus entity.
 */
public interface TypePreAdmissionStatusSearchRepository extends ElasticsearchRepository<TypePreAdmissionStatus, Long> {
}
