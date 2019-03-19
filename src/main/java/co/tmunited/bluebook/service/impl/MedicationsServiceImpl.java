package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.MedicationsService;
import co.tmunited.bluebook.domain.Medications;
import co.tmunited.bluebook.repository.MedicationsRepository;
import co.tmunited.bluebook.repository.search.MedicationsSearchRepository;
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
 * Service Implementation for managing Medications.
 */
@Service
@Transactional
public class MedicationsServiceImpl implements MedicationsService{

    private final Logger log = LoggerFactory.getLogger(MedicationsServiceImpl.class);

    @Inject
    private MedicationsRepository medicationsRepository;

    @Inject
    private MedicationsSearchRepository medicationsSearchRepository;

    /**
     * Save a medications.
     *
     * @param medications the entity to save
     * @return the persisted entity
     */
    public Medications save(Medications medications) {
        log.debug("Request to save Medications : {}", medications);
        Medications result = medicationsRepository.save(medications);
        medicationsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the medications.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Medications> findAll() {
        log.debug("Request to get all Medications");
        List<Medications> result = medicationsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one medications by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Medications findOne(Long id) {
        log.debug("Request to get Medications : {}", id);
        Medications medications = medicationsRepository.findOne(id);
        return medications;
    }

    /**
     *  Delete the  medications by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Medications : {}", id);
      Medications medications = medicationsRepository.findOne(id);
      medications.setDelStatus(true);
      Medications result = medicationsRepository.save(medications);

      medicationsSearchRepository.save(result);
    }

    /**
     * Search for the medications corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Medications> search(String query) {
        log.debug("Request to search Medications for query {}", query);
        query = query + "*";
        return StreamSupport
            .stream(medicationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
