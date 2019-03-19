package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeDosage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeDosage entity.
 */
public interface TypeDosageSearchRepository extends ElasticsearchRepository<TypeDosage, Long> {
}
