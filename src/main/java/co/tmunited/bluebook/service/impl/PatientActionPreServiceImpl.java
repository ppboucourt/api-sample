package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PatientActionPreService;
import co.tmunited.bluebook.domain.PatientActionPre;
import co.tmunited.bluebook.repository.PatientActionPreRepository;
import co.tmunited.bluebook.repository.search.PatientActionPreSearchRepository;
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
 * Service Implementation for managing PatientActionPre.
 */
@Service
@Transactional
public class PatientActionPreServiceImpl implements PatientActionPreService{

    private final Logger log = LoggerFactory.getLogger(PatientActionPreServiceImpl.class);

    @Inject
    private PatientActionPreRepository patientActionPreRepository;

    @Inject
    private PatientActionPreSearchRepository patientActionPreSearchRepository;

    /**
     * Save a patientActionPre.
     *
     * @param patientActionPre the entity to save
     * @return the persisted entity
     */
    public PatientActionPre save(PatientActionPre patientActionPre) {
        log.debug("Request to save PatientActionPre : {}", patientActionPre);
        PatientActionPre result = patientActionPreRepository.save(patientActionPre);
        patientActionPreSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientActionPres.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientActionPre> findAll(Long id) {
        log.debug("Request to get all PatientActionPres");
        List<PatientActionPre> result = patientActionPreRepository.findAllByDelStatusIsFalseAndPatientActionId(id);

        return result;
    }

    /**
     *  Get one patientActionPre by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PatientActionPre findOne(Long id) {
        log.debug("Request to get PatientActionPre : {}", id);
        PatientActionPre patientActionPre = patientActionPreRepository.findOne(id);
        return patientActionPre;
    }

    /**
     *  Delete the  patientActionPre by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientActionPre : {}", id);
        patientActionPreRepository.delete(id);
        patientActionPreSearchRepository.delete(id);
    }

    /**
     * Search for the patientActionPre corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientActionPre> search(String query) {
        log.debug("Request to search PatientActionPres for query {}", query);
        return StreamSupport
            .stream(patientActionPreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
