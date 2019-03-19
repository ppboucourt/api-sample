package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeFormRules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeFormRules entity.
 */
public interface TypeFormRulesSearchRepository extends ElasticsearchRepository<TypeFormRules, Long> {
}
