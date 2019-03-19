package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeRace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeRace entity.
 */
public interface TypeRaceSearchRepository extends ElasticsearchRepository<TypeRace, Long> {
}
