package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.Patient_propertiesService;
import co.tmunited.bluebook.domain.Patient_properties;
import co.tmunited.bluebook.repository.Patient_propertiesRepository;
import co.tmunited.bluebook.repository.search.Patient_propertiesSearchRepository;
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
 * Service Implementation for managing Patient_properties.
 */
@Service
@Transactional
public class Patient_propertiesServiceImpl implements Patient_propertiesService{

    private final Logger log = LoggerFactory.getLogger(Patient_propertiesServiceImpl.class);
    
    @Inject
    private Patient_propertiesRepository patient_propertiesRepository;

    @Inject
    private Patient_propertiesSearchRepository patient_propertiesSearchRepository;

    /**
     * Save a patient_properties.
     *
     * @param patient_properties the entity to save
     * @return the persisted entity
     */
    public Patient_properties save(Patient_properties patient_properties) {
        log.debug("Request to save Patient_properties : {}", patient_properties);
        Patient_properties result = patient_propertiesRepository.save(patient_properties);
        patient_propertiesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patient_properties.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Patient_properties> findAll() {
        log.debug("Request to get all Patient_properties");
        List<Patient_properties> result = patient_propertiesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one patient_properties by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Patient_properties findOne(Long id) {
        log.debug("Request to get Patient_properties : {}", id);
        Patient_properties patient_properties = patient_propertiesRepository.findOne(id);
        return patient_properties;
    }

    /**
     *  Delete the  patient_properties by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient_properties : {}", id);
      Patient_properties patient_properties = patient_propertiesRepository.findOne(id);
      patient_properties.setDelStatus(true);
      Patient_properties result = patient_propertiesRepository.save(patient_properties);
      
      patient_propertiesSearchRepository.save(result);
    }

    /**
     * Search for the patient_properties corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Patient_properties> search(String query) {
        log.debug("Request to search Patient_properties for query {}", query);
        return StreamSupport
            .stream(patient_propertiesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
