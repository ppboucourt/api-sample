package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypePatientContactsRelationship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypePatientContactsRelationship entity.
 */
public interface TypePatientContactsRelationshipSearchRepository extends ElasticsearchRepository<TypePatientContactsRelationship, Long> {
}
