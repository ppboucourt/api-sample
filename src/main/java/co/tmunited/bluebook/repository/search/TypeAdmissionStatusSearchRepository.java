package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeAdmissionStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeAdmissionStatus entity.
 */
public interface TypeAdmissionStatusSearchRepository extends ElasticsearchRepository<TypeAdmissionStatus, Long> {
}
