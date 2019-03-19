package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.LabRequisitionsComponents;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LabRequisitionsComponents entity.
 */
public interface LabRequisitionsComponentsSearchRepository extends ElasticsearchRepository<LabRequisitionsComponents, Long> {
}
