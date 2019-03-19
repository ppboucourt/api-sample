package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.GlucoseIntervention;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GlucoseIntervention entity.
 */
public interface GlucoseInterventionSearchRepository extends ElasticsearchRepository<GlucoseIntervention, Long> {
}
