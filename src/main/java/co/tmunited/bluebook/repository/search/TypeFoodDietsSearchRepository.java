package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeFoodDiets;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeFoodDiets entity.
 */
public interface TypeFoodDietsSearchRepository extends ElasticsearchRepository<TypeFoodDiets, Long> {
}
