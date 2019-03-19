package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.LabRequisitionsComponentsService;
import co.tmunited.bluebook.domain.LabRequisitionsComponents;
import co.tmunited.bluebook.repository.LabRequisitionsComponentsRepository;
import co.tmunited.bluebook.repository.search.LabRequisitionsComponentsSearchRepository;
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
 * Service Implementation for managing LabRequisitionsComponents.
 */
@Service
@Transactional
public class LabRequisitionsComponentsServiceImpl implements LabRequisitionsComponentsService{

    private final Logger log = LoggerFactory.getLogger(LabRequisitionsComponentsServiceImpl.class);
    
    @Inject
    private LabRequisitionsComponentsRepository labRequisitionsComponentsRepository;

    @Inject
    private LabRequisitionsComponentsSearchRepository labRequisitionsComponentsSearchRepository;

    /**
     * Save a labRequisitionsComponents.
     *
     * @param labRequisitionsComponents the entity to save
     * @return the persisted entity
     */
    public LabRequisitionsComponents save(LabRequisitionsComponents labRequisitionsComponents) {
        log.debug("Request to save LabRequisitionsComponents : {}", labRequisitionsComponents);
        LabRequisitionsComponents result = labRequisitionsComponentsRepository.save(labRequisitionsComponents);
        labRequisitionsComponentsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the labRequisitionsComponents.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LabRequisitionsComponents> findAll() {
        log.debug("Request to get all LabRequisitionsComponents");
        List<LabRequisitionsComponents> result = labRequisitionsComponentsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one labRequisitionsComponents by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LabRequisitionsComponents findOne(Long id) {
        log.debug("Request to get LabRequisitionsComponents : {}", id);
        LabRequisitionsComponents labRequisitionsComponents = labRequisitionsComponentsRepository.findOne(id);
        return labRequisitionsComponents;
    }

    /**
     *  Delete the  labRequisitionsComponents by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LabRequisitionsComponents : {}", id);
      LabRequisitionsComponents labRequisitionsComponents = labRequisitionsComponentsRepository.findOne(id);
      labRequisitionsComponents.setDelStatus(true);
      LabRequisitionsComponents result = labRequisitionsComponentsRepository.save(labRequisitionsComponents);
      
      labRequisitionsComponentsSearchRepository.save(result);
    }

    /**
     * Search for the labRequisitionsComponents corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabRequisitionsComponents> search(String query) {
        log.debug("Request to search LabRequisitionsComponents for query {}", query);
        return StreamSupport
            .stream(labRequisitionsComponentsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
