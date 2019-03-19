package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TreatmentHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TreatmentHistory entity.
 */
public interface TreatmentHistorySearchRepository extends ElasticsearchRepository<TreatmentHistory, Long> {
}
