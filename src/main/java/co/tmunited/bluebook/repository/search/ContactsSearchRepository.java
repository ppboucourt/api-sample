package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Contacts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Contacts entity.
 */
public interface ContactsSearchRepository extends ElasticsearchRepository<Contacts, Long> {
}
