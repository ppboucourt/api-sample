package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeMedicationRoutes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeMedicationRoutes entity.
 */
public interface TypeMedicationRoutesSearchRepository extends ElasticsearchRepository<TypeMedicationRoutes, Long> {
}
