package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToIcd10;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToIcd10 entity.
 */
public interface ChartToIcd10SearchRepository extends ElasticsearchRepository<ChartToIcd10, Long> {
}
