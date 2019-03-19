package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Reports;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Reports entity.
 */
public interface ReportsSearchRepository extends ElasticsearchRepository<Reports, Long> {
}
