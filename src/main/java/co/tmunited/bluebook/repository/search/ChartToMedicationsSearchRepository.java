package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.ChartToMedications;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ChartToMedications entity.
 */
public interface ChartToMedicationsSearchRepository extends ElasticsearchRepository<ChartToMedications, Long> {
}
