package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.VitalsService;
import co.tmunited.bluebook.domain.Vitals;
import co.tmunited.bluebook.repository.VitalsRepository;
import co.tmunited.bluebook.repository.search.VitalsSearchRepository;
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
 * Service Implementation for managing Vitals.
 */
@Service
@Transactional
public class VitalsServiceImpl implements VitalsService{

    private final Logger log = LoggerFactory.getLogger(VitalsServiceImpl.class);

    @Inject
    private VitalsRepository vitalsRepository;

    @Inject
    private VitalsSearchRepository vitalsSearchRepository;

    /**
     * Save a vitals.
     *
     * @param vitals the entity to save
     * @return the persisted entity
     */
    public Vitals save(Vitals vitals) {
        log.debug("Request to save Vitals : {}", vitals);
        Vitals result = vitalsRepository.save(vitals);
        vitalsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the vitals.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Vitals> findAll() {
        log.debug("Request to get all Vitals");
        List<Vitals> result = vitalsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one vitals by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Vitals findOne(Long id) {
        log.debug("Request to get Vitals : {}", id);
        Vitals vitals = vitalsRepository.findOne(id);
        return vitals;
    }

    /**
     *  Delete the  vitals by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vitals : {}", id);
      Vitals vitals = vitalsRepository.findOne(id);
      vitals.setDelStatus(true);
      Vitals result = vitalsRepository.save(vitals);

      vitalsSearchRepository.save(result);
    }

    /**
     * Search for the vitals corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Vitals> search(String query) {
        log.debug("Request to search Vitals for query {}", query);
        return StreamSupport
            .stream(vitalsSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search for the vitals corresponding to the query.
     *
     *  @param id the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Vitals> findByChart(Long id) {
        log.debug("Request to search Vitals for query {}", id);
        List<Vitals> result = vitalsRepository.findAllByDelStatusIsFalseAndChartId(id);
        return result;
    }

}
