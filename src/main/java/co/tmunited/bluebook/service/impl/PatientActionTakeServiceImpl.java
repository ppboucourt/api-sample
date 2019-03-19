package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PatientActionTakeService;
import co.tmunited.bluebook.domain.PatientActionTake;
import co.tmunited.bluebook.repository.PatientActionTakeRepository;
import co.tmunited.bluebook.repository.search.PatientActionTakeSearchRepository;
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
 * Service Implementation for managing PatientActionTake.
 */
@Service
@Transactional
public class PatientActionTakeServiceImpl implements PatientActionTakeService{

    private final Logger log = LoggerFactory.getLogger(PatientActionTakeServiceImpl.class);
    
    @Inject
    private PatientActionTakeRepository patientActionTakeRepository;

    @Inject
    private PatientActionTakeSearchRepository patientActionTakeSearchRepository;

    /**
     * Save a patientActionTake.
     *
     * @param patientActionTake the entity to save
     * @return the persisted entity
     */
    public PatientActionTake save(PatientActionTake patientActionTake) {
        log.debug("Request to save PatientActionTake : {}", patientActionTake);
        PatientActionTake result = patientActionTakeRepository.save(patientActionTake);
        patientActionTakeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientActionTakes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PatientActionTake> findAll() {
        log.debug("Request to get all PatientActionTakes");
        List<PatientActionTake> result = patientActionTakeRepository.findAll();

        return result;
    }

    /**
     *  Get one patientActionTake by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PatientActionTake findOne(Long id) {
        log.debug("Request to get PatientActionTake : {}", id);
        PatientActionTake patientActionTake = patientActionTakeRepository.findOne(id);
        return patientActionTake;
    }

    /**
     *  Delete the  patientActionTake by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientActionTake : {}", id);
        patientActionTakeRepository.delete(id);
        patientActionTakeSearchRepository.delete(id);
    }

    /**
     * Search for the patientActionTake corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientActionTake> search(String query) {
        log.debug("Request to search PatientActionTakes for query {}", query);
        return StreamSupport
            .stream(patientActionTakeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
