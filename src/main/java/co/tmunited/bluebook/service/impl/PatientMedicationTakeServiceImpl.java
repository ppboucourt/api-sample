package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Routes;
import co.tmunited.bluebook.service.PatientMedicationTakeService;
import co.tmunited.bluebook.domain.PatientMedicationTake;
import co.tmunited.bluebook.repository.PatientMedicationTakeRepository;
import co.tmunited.bluebook.repository.search.PatientMedicationTakeSearchRepository;
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
 * Service Implementation for managing PatientMedicationTake.
 */
@Service
@Transactional
public class PatientMedicationTakeServiceImpl implements PatientMedicationTakeService{

    private final Logger log = LoggerFactory.getLogger(PatientMedicationTakeServiceImpl.class);

    @Inject
    private PatientMedicationTakeRepository patientMedicationTakeRepository;

    @Inject
    private PatientMedicationTakeSearchRepository patientMedicationTakeSearchRepository;

    /**
     * Save a patientMedicationTake.
     *
     * @param patientMedicationTake the entity to save
     * @return the persisted entity
     */
    public PatientMedicationTake save(PatientMedicationTake patientMedicationTake) {
        log.debug("Request to save PatientMedicationTake : {}", patientMedicationTake);
        PatientMedicationTake result = patientMedicationTakeRepository.save(patientMedicationTake);
        patientMedicationTakeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientMedicationTakes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedicationTake> findAll() {
        log.debug("Request to get all PatientMedicationTakes");
        List<PatientMedicationTake> result = patientMedicationTakeRepository.findAll();

        return result;
    }

    /**
     *  Get one patientMedicationTake by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PatientMedicationTake findOne(Long id) {
        log.debug("Request to get PatientMedicationTake : {}", id);
        PatientMedicationTake patientMedicationTake = patientMedicationTakeRepository.findOne(id);


        patientMedicationTake.setMedication(patientMedicationTake.getPatientMedicationPres().getMedications().getMedication());
        patientMedicationTake.setDose(patientMedicationTake.getPatientMedicationPres().getDose());

        Routes route = patientMedicationTake.getPatientMedicationPres().getPatientMedication().getRoute();
        patientMedicationTake.setRoute(route  !=null ? route.getName(): "");


        return patientMedicationTake;
    }

    /**
     *  Delete the  patientMedicationTake by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientMedicationTake : {}", id);
        patientMedicationTakeRepository.delete(id);
        patientMedicationTakeSearchRepository.delete(id);
    }

    /**
     * Search for the patientMedicationTake corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientMedicationTake> search(String query) {
        log.debug("Request to search PatientMedicationTakes for query {}", query);
        return StreamSupport
            .stream(patientMedicationTakeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
