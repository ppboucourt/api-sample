package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToForm entity.
 */
public interface ChartToFormSearchRepository extends ElasticsearchRepository<ChartToForm, Long> {
}
