package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.LabCompendium;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LabCompendium entity.
 */
public interface LabCompendiumSearchRepository extends ElasticsearchRepository<LabCompendium, Long> {
}
