package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.PatientResultDet;
import co.tmunited.bluebook.repository.PatientResultDetRepository;
import co.tmunited.bluebook.repository.search.PatientResultDetSearchRepository;
import co.tmunited.bluebook.service.PatientResultDetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * Service Implementation for managing PatientResultDet.
 */
@Service
@Transactional
public class PatientResultDetServiceImpl implements PatientResultDetService{

    private final Logger log = LoggerFactory.getLogger(PatientResultDetServiceImpl.class);

    @Inject
    private PatientResultDetRepository patientResultDetRepository;

    @Inject
    private PatientResultDetSearchRepository patientResultDetSearchRepository;

    /**
     * Save a patientResultDet.
     *
     * @param patientResultDet the entity to save
     * @return the persisted entity
     */
    public PatientResultDet save(PatientResultDet patientResultDet) {
        log.debug("Request to save PatientResultDet : {}", patientResultDet);
        PatientResultDet result = patientResultDetRepository.save(patientResultDet);
        patientResultDetSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientResultDets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResultDet> findAll(Long id) {
        log.debug("Request to get all PatientResultDets");
        List<PatientResultDet> result = patientResultDetRepository.findAllByDelStatusIsFalseAndPatientResultId(id);

        return result;
    }

    /**
     *  Get one patientResultDet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PatientResultDet findOne(Long id) {
        log.debug("Request to get PatientResultDet : {}", id);
        PatientResultDet patientResultDet = patientResultDetRepository.findOne(id);
        return patientResultDet;
    }

    /**
     *  Delete the  patientResultDet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientResultDet : {}", id);
      PatientResultDet patientResultDet = patientResultDetRepository.findOne(id);
      patientResultDet.setDelStatus(true);
      PatientResultDet result = patientResultDetRepository.save(patientResultDet);

      patientResultDetSearchRepository.save(result);
    }

    /**
     * Search for the patientResultDet corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResultDet> search(String query) {
        log.debug("Request to search PatientResultDets for query {}", query);
        return StreamSupport
            .stream(patientResultDetSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .sorted(Comparator.comparing(PatientResultDet::getCreatedDate))
            .collect(Collectors.toList());
    }
}
