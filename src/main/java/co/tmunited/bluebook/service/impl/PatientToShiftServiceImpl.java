package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PatientToShiftService;
import co.tmunited.bluebook.domain.PatientToShift;
import co.tmunited.bluebook.repository.PatientToShiftRepository;
import co.tmunited.bluebook.repository.search.PatientToShiftSearchRepository;
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
 * Service Implementation for managing PatientToShift.
 */
@Service
@Transactional
public class PatientToShiftServiceImpl implements PatientToShiftService{

    private final Logger log = LoggerFactory.getLogger(PatientToShiftServiceImpl.class);
    
    @Inject
    private PatientToShiftRepository patientToShiftRepository;

    @Inject
    private PatientToShiftSearchRepository patientToShiftSearchRepository;

    /**
     * Save a patientToShift.
     *
     * @param patientToShift the entity to save
     * @return the persisted entity
     */
    public PatientToShift save(PatientToShift patientToShift) {
        log.debug("Request to save PatientToShift : {}", patientToShift);
        PatientToShift result = patientToShiftRepository.save(patientToShift);
        patientToShiftSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientToShifts.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PatientToShift> findAll() {
        log.debug("Request to get all PatientToShifts");
        List<PatientToShift> result = patientToShiftRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one patientToShift by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PatientToShift findOne(Long id) {
        log.debug("Request to get PatientToShift : {}", id);
        PatientToShift patientToShift = patientToShiftRepository.findOne(id);
        return patientToShift;
    }

    /**
     *  Delete the  patientToShift by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientToShift : {}", id);
      PatientToShift patientToShift = patientToShiftRepository.findOne(id);
      patientToShift.setDelStatus(true);
      PatientToShift result = patientToShiftRepository.save(patientToShift);
      
      patientToShiftSearchRepository.save(result);
    }

    /**
     * Search for the patientToShift corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientToShift> search(String query) {
        log.debug("Request to search PatientToShifts for query {}", query);
        return StreamSupport
            .stream(patientToShiftSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
