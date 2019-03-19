package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToGroupSession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToGroupSession entity.
 */
public interface ChartToGroupSessionSearchRepository extends ElasticsearchRepository<ChartToGroupSession, Long> {
}
