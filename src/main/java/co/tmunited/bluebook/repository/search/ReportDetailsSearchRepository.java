package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ReportDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ReportDetails entity.
 */
public interface ReportDetailsSearchRepository extends ElasticsearchRepository<ReportDetails, Long> {
}
