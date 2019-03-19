package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePatientPrograms;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePatientPrograms entity.
 */
public interface TypePatientProgramsSearchRepository extends ElasticsearchRepository<TypePatientPrograms, Long> {
}
