package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToLevelCare;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToLevelCare entity.
 */
public interface ChartToLevelCareSearchRepository extends ElasticsearchRepository<ChartToLevelCare, Long> {
}
