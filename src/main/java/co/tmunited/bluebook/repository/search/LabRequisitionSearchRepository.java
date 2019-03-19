package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.LabRequisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LabRequisition entity.
 */
public interface LabRequisitionSearchRepository extends ElasticsearchRepository<LabRequisition, Long> {
}
