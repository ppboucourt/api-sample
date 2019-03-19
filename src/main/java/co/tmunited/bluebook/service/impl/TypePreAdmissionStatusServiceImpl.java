package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePreAdmissionStatusService;
import co.tmunited.bluebook.domain.TypePreAdmissionStatus;
import co.tmunited.bluebook.repository.TypePreAdmissionStatusRepository;
import co.tmunited.bluebook.repository.search.TypePreAdmissionStatusSearchRepository;
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
 * Service Implementation for managing TypePreAdmissionStatus.
 */
@Service
@Transactional
public class TypePreAdmissionStatusServiceImpl implements TypePreAdmissionStatusService{

    private final Logger log = LoggerFactory.getLogger(TypePreAdmissionStatusServiceImpl.class);
    
    @Inject
    private TypePreAdmissionStatusRepository typePreAdmissionStatusRepository;

    @Inject
    private TypePreAdmissionStatusSearchRepository typePreAdmissionStatusSearchRepository;

    /**
     * Save a typePreAdmissionStatus.
     *
     * @param typePreAdmissionStatus the entity to save
     * @return the persisted entity
     */
    public TypePreAdmissionStatus save(TypePreAdmissionStatus typePreAdmissionStatus) {
        log.debug("Request to save TypePreAdmissionStatus : {}", typePreAdmissionStatus);
        TypePreAdmissionStatus result = typePreAdmissionStatusRepository.save(typePreAdmissionStatus);
        typePreAdmissionStatusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePreAdmissionStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePreAdmissionStatus> findAll() {
        log.debug("Request to get all TypePreAdmissionStatuses");
        List<TypePreAdmissionStatus> result = typePreAdmissionStatusRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePreAdmissionStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePreAdmissionStatus findOne(Long id) {
        log.debug("Request to get TypePreAdmissionStatus : {}", id);
        TypePreAdmissionStatus typePreAdmissionStatus = typePreAdmissionStatusRepository.findOne(id);
        return typePreAdmissionStatus;
    }

    /**
     *  Delete the  typePreAdmissionStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePreAdmissionStatus : {}", id);
      TypePreAdmissionStatus typePreAdmissionStatus = typePreAdmissionStatusRepository.findOne(id);
      typePreAdmissionStatus.setDelStatus(true);
      TypePreAdmissionStatus result = typePreAdmissionStatusRepository.save(typePreAdmissionStatus);
      
      typePreAdmissionStatusSearchRepository.save(result);
    }

    /**
     * Search for the typePreAdmissionStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePreAdmissionStatus> search(String query) {
        log.debug("Request to search TypePreAdmissionStatuses for query {}", query);
        return StreamSupport
            .stream(typePreAdmissionStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
