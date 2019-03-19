package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeTreatmentPlanStatuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeTreatmentPlanStatuses entity.
 */
public interface TypeTreatmentPlanStatusesSearchRepository extends ElasticsearchRepository<TypeTreatmentPlanStatuses, Long> {
}
