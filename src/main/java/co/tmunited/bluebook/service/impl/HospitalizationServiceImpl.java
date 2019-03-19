package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.HospitalizationService;
import co.tmunited.bluebook.domain.Hospitalization;
import co.tmunited.bluebook.repository.HospitalizationRepository;
import co.tmunited.bluebook.repository.search.HospitalizationSearchRepository;
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
 * Service Implementation for managing Hospitalization.
 */
@Service
@Transactional
public class HospitalizationServiceImpl implements HospitalizationService{

    private final Logger log = LoggerFactory.getLogger(HospitalizationServiceImpl.class);

    @Inject
    private HospitalizationRepository hospitalizationRepository;

    @Inject
    private HospitalizationSearchRepository hospitalizationSearchRepository;

    /**
     * Save a hospitalization.
     *
     * @param hospitalization the entity to save
     * @return the persisted entity
     */
    public Hospitalization save(Hospitalization hospitalization) {
        log.debug("Request to save Hospitalization : {}", hospitalization);
        Hospitalization result = hospitalizationRepository.save(hospitalization);
        hospitalizationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the hospitalizations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Hospitalization> findAll() {
        log.debug("Request to get all Hospitalizations");
        List<Hospitalization> result = hospitalizationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one hospitalization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Hospitalization findOne(Long id) {
        log.debug("Request to get Hospitalization : {}", id);
        Hospitalization hospitalization = hospitalizationRepository.findOne(id);
        return hospitalization;
    }

    /**
     *  Delete the  hospitalization by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Hospitalization : {}", id);
      Hospitalization hospitalization = hospitalizationRepository.findOne(id);
      hospitalization.setDelStatus(true);
      Hospitalization result = hospitalizationRepository.save(hospitalization);

      hospitalizationSearchRepository.save(result);
    }

    /**
     * Search for the hospitalization corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Hospitalization> search(String query) {
        log.debug("Request to search Hospitalizations for query {}", query);
        return StreamSupport
            .stream(hospitalizationSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
