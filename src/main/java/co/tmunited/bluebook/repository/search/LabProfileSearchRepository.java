package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.LabProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LabProfile entity.
 */
public interface LabProfileSearchRepository extends ElasticsearchRepository<LabProfile, Long> {
}
