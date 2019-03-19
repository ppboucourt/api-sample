package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePatientProcess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePatientProcess entity.
 */
public interface TypePatientProcessSearchRepository extends ElasticsearchRepository<TypePatientProcess, Long> {
}
