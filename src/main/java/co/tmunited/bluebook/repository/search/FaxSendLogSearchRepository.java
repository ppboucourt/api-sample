package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.FaxSendLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FaxSendLog entity.
 */
public interface FaxSendLogSearchRepository extends ElasticsearchRepository<FaxSendLog, Long> {
}
