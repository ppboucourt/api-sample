package co.tmunited.bluebook.repository.search;

import co.tmunited.bluebook.domain.Bed;
import co.tmunited.bluebook.domain.Picture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Picture entity.
 */
public interface PictureSearchRepository extends ElasticsearchRepository<Picture, Long> {
}
