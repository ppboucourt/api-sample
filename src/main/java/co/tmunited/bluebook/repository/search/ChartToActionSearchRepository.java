package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToAction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToAction entity.
 */
public interface ChartToActionSearchRepository extends ElasticsearchRepository<ChartToAction, Long> {
}
