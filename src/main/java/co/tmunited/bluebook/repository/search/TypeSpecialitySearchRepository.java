package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.TypeSpeciality;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TypeSpeciality entity.
 */
public interface TypeSpecialitySearchRepository extends ElasticsearchRepository<TypeSpeciality, Long> {
}
