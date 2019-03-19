package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.GlucoseInterventionService;
import co.tmunited.bluebook.domain.GlucoseIntervention;
import co.tmunited.bluebook.repository.GlucoseInterventionRepository;
import co.tmunited.bluebook.repository.search.GlucoseInterventionSearchRepository;
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
 * Service Implementation for managing GlucoseIntervention.
 */
@Service
@Transactional
public class GlucoseInterventionServiceImpl implements GlucoseInterventionService{

    private final Logger log = LoggerFactory.getLogger(GlucoseInterventionServiceImpl.class);
    
    @Inject
    private GlucoseInterventionRepository glucoseInterventionRepository;

    @Inject
    private GlucoseInterventionSearchRepository glucoseInterventionSearchRepository;

    /**
     * Save a glucoseIntervention.
     *
     * @param glucoseIntervention the entity to save
     * @return the persisted entity
     */
    public GlucoseIntervention save(GlucoseIntervention glucoseIntervention) {
        log.debug("Request to save GlucoseIntervention : {}", glucoseIntervention);
        GlucoseIntervention result = glucoseInterventionRepository.save(glucoseIntervention);
        glucoseInterventionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the glucoseInterventions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GlucoseIntervention> findAll() {
        log.debug("Request to get all GlucoseInterventions");
        List<GlucoseIntervention> result = glucoseInterventionRepository.findAll();

        return result;
    }

    /**
     *  Get one glucoseIntervention by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GlucoseIntervention findOne(Long id) {
        log.debug("Request to get GlucoseIntervention : {}", id);
        GlucoseIntervention glucoseIntervention = glucoseInterventionRepository.findOne(id);
        return glucoseIntervention;
    }

    /**
     *  Delete the  glucoseIntervention by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlucoseIntervention : {}", id);
        glucoseInterventionRepository.delete(id);
        glucoseInterventionSearchRepository.delete(id);
    }

    /**
     * Search for the glucoseIntervention corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GlucoseIntervention> search(String query) {
        log.debug("Request to search GlucoseInterventions for query {}", query);
        return StreamSupport
            .stream(glucoseInterventionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
