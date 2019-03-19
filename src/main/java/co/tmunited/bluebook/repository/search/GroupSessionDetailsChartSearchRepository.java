package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GroupSessionDetailsChart entity.
 */
public interface GroupSessionDetailsChartSearchRepository extends ElasticsearchRepository<GroupSessionDetailsChart, Long> {
}
