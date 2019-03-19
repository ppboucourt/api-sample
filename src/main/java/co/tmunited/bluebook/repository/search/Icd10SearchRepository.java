package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Icd10;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Icd10 entity.
 */
public interface Icd10SearchRepository extends ElasticsearchRepository<Icd10, Long> {
}
