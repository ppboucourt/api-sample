package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartCareTeam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartCareTeam entity.
 */
public interface ChartCareTeamSearchRepository extends ElasticsearchRepository<ChartCareTeam, Long> {
}
