package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Chart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Chart entity.
 */
public interface ChartSearchRepository extends ElasticsearchRepository<Chart, Long> {
}
