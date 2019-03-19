package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePatientContactTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePatientContactTypes entity.
 */
public interface TypePatientContactTypesSearchRepository extends ElasticsearchRepository<TypePatientContactTypes, Long> {
}
