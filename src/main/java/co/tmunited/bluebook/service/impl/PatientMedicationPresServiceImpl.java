package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PatientMedicationPresService;
import co.tmunited.bluebook.domain.PatientMedicationPres;
import co.tmunited.bluebook.repository.PatientMedicationPresRepository;
import co.tmunited.bluebook.repository.search.PatientMedicationPresSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PatientMedicationPres.
 */
@Service
@Transactional
public class PatientMedicationPresServiceImpl implements PatientMedicationPresService {

    private final Logger log = LoggerFactory.getLogger(PatientMedicationPresServiceImpl.class);

    @Inject
    private PatientMedicationPresRepository patientMedicationPresRepository;

    @Inject
    private PatientMedicationPresSearchRepository patientMedicationPresSearchRepository;

    /**
     * Save a patientMedicationPres.
     *
     * @param patientMedicationPres the entity to save
     * @return the persisted entity
     */
    public PatientMedicationPres save(PatientMedicationPres patientMedicationPres) {
        log.debug("Request to save PatientMedicationPres : {}", patientMedicationPres);
        PatientMedicationPres result = patientMedicationPresRepository.save(patientMedicationPres);
        patientMedicationPresSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientMedicationPres.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedicationPres> findAll(Long id) {
        log.debug("Request to get all PatientMedicationPres");
        List<PatientMedicationPres> result = patientMedicationPresRepository.findAllByDelStatusIsFalseAndPatientMedicationId(id);

        return result;
    }

    /**
     * Get all the patientMedicationPres in a date.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientMedicationPres> findAllMedicationByChartAndDate(Long chartId) {
        log.debug("Request to get all PatientMedicationPres by date");
        List<PatientMedicationPres> result = patientMedicationPresRepository.findAllMedicationByChartAndDate(chartId);

        return result;
    }

    /**
     * Get one patientMedicationPres by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientMedicationPres findOne(Long id) {
        log.debug("Request to get PatientMedicationPres : {}", id);
        PatientMedicationPres patientMedicationPres = patientMedicationPresRepository.findOne(id);
        return patientMedicationPres;
    }

    /**
     * Delete the  patientMedicationPres by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientMedicationPres : {}", id);
        patientMedicationPresRepository.delete(id);
        patientMedicationPresSearchRepository.delete(id);
    }

    /**
     * Search for the patientMedicationPres corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedicationPres> search(String query) {
        log.debug("Request to search PatientMedicationPres for query {}", query);
        return StreamSupport
            .stream(patientMedicationPresSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
