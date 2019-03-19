package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.GlucoseService;
import co.tmunited.bluebook.domain.Glucose;
import co.tmunited.bluebook.repository.GlucoseRepository;
import co.tmunited.bluebook.repository.search.GlucoseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Glucose.
 */
@Service
@Transactional
public class GlucoseServiceImpl implements GlucoseService{

    private final Logger log = LoggerFactory.getLogger(GlucoseServiceImpl.class);

    @Inject
    private GlucoseRepository glucoseRepository;

    @Inject
    private GlucoseSearchRepository glucoseSearchRepository;

    /**
     * Save a glucose.
     *
     * @param glucose the entity to save
     * @return the persisted entity
     */
    public Glucose save(Glucose glucose) {
        log.debug("Request to save Glucose : {}", glucose);
        Glucose result = glucoseRepository.save(glucose);
        glucoseSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the glucoses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Glucose> findAll() {
        log.debug("Request to get all Glucoses");
        List<Glucose> result = glucoseRepository.findAllWithEagerRelationshipsAndDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one glucose by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Glucose findOne(Long id) {
        log.debug("Request to get Glucose : {}", id);
        Glucose glucose = glucoseRepository.findOneWithEagerRelationshipsAndDelStatusIsFalse(id);
        return glucose;
    }

    /**
     *  Delete the  glucose by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Glucose : {}", id);
        Glucose glucose = glucoseRepository.findOne(id);
        glucose.setDelStatus(true);
        Glucose result = glucoseRepository.save(glucose);

        glucoseSearchRepository.save(result);
    }

    /**
     * Search for the glucose corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Glucose> search(String query) {
        log.debug("Request to search Glucoses for query {}", query);
        return StreamSupport
            .stream(glucoseSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<Glucose> findByChart(Long id) {
        log.debug("Request to search Glucoses by chartId {}", id);
        List<Glucose> result = glucoseRepository.findAllByDelStatusIsFalseAndChartId(id);

        return result;
    }
}
