package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePatientPropertyCondition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePatientPropertyCondition entity.
 */
public interface TypePatientPropertyConditionSearchRepository extends ElasticsearchRepository<TypePatientPropertyCondition, Long> {
}
