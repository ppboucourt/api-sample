package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Tube;
import co.tmunited.bluebook.repository.TubeRepository;
import co.tmunited.bluebook.repository.search.TubeSearchRepository;
import co.tmunited.bluebook.service.TubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Tube.
 */
@Service
@Transactional
public class TubeServiceImpl implements TubeService{

    private final Logger log = LoggerFactory.getLogger(TubeServiceImpl.class);

    @Inject
    private TubeRepository tubeRepository;

    @Inject
    private TubeSearchRepository tubeSearchRepository;

    /**
     * Save a tube.
     *
     * @param tube the entity to save
     * @return the persisted entity
     */
    public Tube save(Tube tube) {
        log.debug("Request to save Tube : {}", tube);
        Tube result = tubeRepository.save(tube);
        tubeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tubes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Tube> findAll() {
        log.debug("Request to get all Tubes");
        List<Tube> result = tubeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one tube by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tube findOne(Long id) {
        log.debug("Request to get Tube : {}", id);
        Tube tube = tubeRepository.findOne(id);
        return tube;
    }

    /**
     *  Delete the  tube by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tube : {}", id);
      Tube tube = tubeRepository.findOne(id);
      tube.setDelStatus(true);
      Tube result = tubeRepository.save(tube);

      tubeSearchRepository.save(result);
    }

    /**
     * Search for the tube corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Tube> search(String query) {
        log.debug("Request to search Tubes for query {}", query);
        return StreamSupport
            .stream(tubeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
