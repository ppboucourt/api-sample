package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Tube;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Tube entity.
 */
public interface TubeSearchRepository extends ElasticsearchRepository<Tube, Long> {
}
