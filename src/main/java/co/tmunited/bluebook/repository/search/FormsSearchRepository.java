package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Forms;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Forms entity.
 */
public interface FormsSearchRepository extends ElasticsearchRepository<Forms, Long> {
}
