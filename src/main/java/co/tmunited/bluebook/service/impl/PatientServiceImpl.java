package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PatientService;
import co.tmunited.bluebook.domain.Patient;
import co.tmunited.bluebook.repository.PatientRepository;
import co.tmunited.bluebook.repository.search.PatientSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService{

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private PatientSearchRepository patientSearchRepository;

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        Patient result = patientRepository.save(patient);
        patientSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patients.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        log.debug("Request to get all Patients");
        List<Patient> result = patientRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one patient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Patient findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        Patient patient = patientRepository.findOne(id);
        return patient;
    }

    /**
     *  Delete the  patient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
      Patient patient = patientRepository.findOne(id);
      patient.setDelStatus(true);
      Patient result = patientRepository.save(patient);

      patientSearchRepository.save(result);
    }

    /**
     * Search for the patient corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Patient> search(String query) {
        log.debug("Request to search Patients for query {}", query);
        List<Patient> result = StreamSupport
            .stream(patientSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }

    @Override
    public int getPatientAge(ZonedDateTime dob) {
        Calendar cal = new GregorianCalendar().from(dob);
        Calendar now = new GregorianCalendar();
        int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
            || (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))) {
            res--;
        }
        return res;
    }
}
