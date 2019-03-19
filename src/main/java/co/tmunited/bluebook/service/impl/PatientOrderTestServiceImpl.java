package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.PatientOrderTest;
import co.tmunited.bluebook.repository.PatientOrderTestRepository;
import co.tmunited.bluebook.repository.search.PatientOrderTestSearchRepository;
import co.tmunited.bluebook.service.PatientOrderTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientOrderTest.
 */
@Service
@Transactional
public class PatientOrderTestServiceImpl implements PatientOrderTestService{

    private final Logger log = LoggerFactory.getLogger(PatientOrderTestServiceImpl.class);

    @Inject
    private PatientOrderTestRepository patientOrderTestRepository;

    @Inject
    private PatientOrderTestSearchRepository patientOrderTestSearchRepository;

    /**
     * Save a patientOrderTest.
     *
     * @param patientOrderTest the entity to save
     * @return the persisted entity
     */
    public PatientOrderTest save(PatientOrderTest patientOrderTest) {
        log.debug("Request to save PatientOrderTest : {}", patientOrderTest);
        PatientOrderTest result = patientOrderTestRepository.save(patientOrderTest);
        patientOrderTestSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the patientOrderTests.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderTest> findAll(Long id) {
        log.debug("Request to get all PatientOrderTests");
        List<PatientOrderTest> result = patientOrderTestRepository.findAllByDelStatusIsFalseAndPatientOrderId(id);

        return result;
    }

    /**
     *  Get one patientOrderTest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PatientOrderTest findOne(Long id) {
        log.debug("Request to get PatientOrderTest : {}", id);
        PatientOrderTest patientOrderTest = patientOrderTestRepository.findOne(id);
        return patientOrderTest;
    }

    /**
     *  Delete the  patientOrderTest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientOrderTest : {}", id);
        patientOrderTestRepository.delete(id);
        patientOrderTestSearchRepository.delete(id);
    }

    /**
     * Search for the patientOrderTest corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderTest> search(String query) {
        log.debug("Request to search PatientOrderTests for query {}", query);
        return StreamSupport
            .stream(patientOrderTestSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    public Set<PatientOrderItem> getPatientOrderTestItems(Long id){
        log.debug("Service request schedule getPatientOrderTestItems");

        PatientOrderTest patientOrderTest = patientOrderTestRepository.findOne(id);
        if(patientOrderTest != null && patientOrderTest.getPatientOrderItems() !=null && patientOrderTest.getPatientOrderItems().size() > 0){
            return patientOrderTest.getPatientOrderItems();
        }

        return new HashSet<PatientOrderItem>();
    }


    public PatientOrderTest getPatientOrderItemsForSchedules(Long id){
        PatientOrderTest patientOrderTest = patientOrderTestRepository.findOne(id);
        if(patientOrderTest.getPatientOrderItems() != null){
            patientOrderTest.getPatientOrderItems().size();
        }

        return patientOrderTest;
    }
}
