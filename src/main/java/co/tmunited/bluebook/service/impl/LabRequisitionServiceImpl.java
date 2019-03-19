package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.LabRequisitionService;
import co.tmunited.bluebook.domain.LabRequisition;
import co.tmunited.bluebook.repository.LabRequisitionRepository;
import co.tmunited.bluebook.repository.search.LabRequisitionSearchRepository;
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
 * Service Implementation for managing LabRequisition.
 */
@Service
@Transactional
public class LabRequisitionServiceImpl implements LabRequisitionService{

    private final Logger log = LoggerFactory.getLogger(LabRequisitionServiceImpl.class);
    
    @Inject
    private LabRequisitionRepository labRequisitionRepository;

    @Inject
    private LabRequisitionSearchRepository labRequisitionSearchRepository;

    /**
     * Save a labRequisition.
     *
     * @param labRequisition the entity to save
     * @return the persisted entity
     */
    public LabRequisition save(LabRequisition labRequisition) {
        log.debug("Request to save LabRequisition : {}", labRequisition);
        LabRequisition result = labRequisitionRepository.save(labRequisition);
        labRequisitionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the labRequisitions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LabRequisition> findAll() {
        log.debug("Request to get all LabRequisitions");
        List<LabRequisition> result = labRequisitionRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one labRequisition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LabRequisition findOne(Long id) {
        log.debug("Request to get LabRequisition : {}", id);
        LabRequisition labRequisition = labRequisitionRepository.findOne(id);
        return labRequisition;
    }

    /**
     *  Delete the  labRequisition by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LabRequisition : {}", id);
      LabRequisition labRequisition = labRequisitionRepository.findOne(id);
      labRequisition.setDelStatus(true);
      LabRequisition result = labRequisitionRepository.save(labRequisition);
      
      labRequisitionSearchRepository.save(result);
    }

    /**
     * Search for the labRequisition corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabRequisition> search(String query) {
        log.debug("Request to search LabRequisitions for query {}", query);
        return StreamSupport
            .stream(labRequisitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
