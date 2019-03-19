package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.AllergiesService;
import co.tmunited.bluebook.domain.Allergies;
import co.tmunited.bluebook.repository.AllergiesRepository;
import co.tmunited.bluebook.repository.search.AllergiesSearchRepository;
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
 * Service Implementation for managing Allergies.
 */
@Service
@Transactional
public class AllergiesServiceImpl implements AllergiesService{

    private final Logger log = LoggerFactory.getLogger(AllergiesServiceImpl.class);

    @Inject
    private AllergiesRepository allergiesRepository;

    @Inject
    private AllergiesSearchRepository allergiesSearchRepository;

    /**
     * Save a allergies.
     *
     * @param allergies the entity to save
     * @return the persisted entity
     */
    public Allergies save(Allergies allergies) {
        log.debug("Request to save Allergies : {}", allergies);
        Allergies result = allergiesRepository.save(allergies);
        allergiesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the allergies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Allergies> findAll() {
        log.debug("Request to get all Allergies");
        List<Allergies> result = allergiesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get all the allergies.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Allergies> findAllByChartId(Long id) {
        log.debug("Request to get all Allergies by chart ID");
        List<Allergies> result = allergiesRepository.findAllByChartId(id);

        return result;
    }

    /**
     *  Get one allergies by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Allergies findOne(Long id) {
        log.debug("Request to get Allergies : {}", id);
        Allergies allergies = allergiesRepository.findOne(id);
        return allergies;
    }

    /**
     *  Delete the  allergies by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Allergies : {}", id);
      Allergies allergies = allergiesRepository.findOne(id);
      allergies.setDelStatus(true);
      Allergies result = allergiesRepository.save(allergies);

      allergiesSearchRepository.save(result);
    }

    /**
     * Search for the allergies corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Allergies> search(String query) {
        log.debug("Request to search Allergies for query {}", query);
        return StreamSupport
            .stream(allergiesSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
