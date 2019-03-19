package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeAdmissionStatusService;
import co.tmunited.bluebook.domain.TypeAdmissionStatus;
import co.tmunited.bluebook.repository.TypeAdmissionStatusRepository;
import co.tmunited.bluebook.repository.search.TypeAdmissionStatusSearchRepository;
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
 * Service Implementation for managing TypeAdmissionStatus.
 */
@Service
@Transactional
public class TypeAdmissionStatusServiceImpl implements TypeAdmissionStatusService{

    private final Logger log = LoggerFactory.getLogger(TypeAdmissionStatusServiceImpl.class);
    
    @Inject
    private TypeAdmissionStatusRepository typeAdmissionStatusRepository;

    @Inject
    private TypeAdmissionStatusSearchRepository typeAdmissionStatusSearchRepository;

    /**
     * Save a typeAdmissionStatus.
     *
     * @param typeAdmissionStatus the entity to save
     * @return the persisted entity
     */
    public TypeAdmissionStatus save(TypeAdmissionStatus typeAdmissionStatus) {
        log.debug("Request to save TypeAdmissionStatus : {}", typeAdmissionStatus);
        TypeAdmissionStatus result = typeAdmissionStatusRepository.save(typeAdmissionStatus);
        typeAdmissionStatusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeAdmissionStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeAdmissionStatus> findAll() {
        log.debug("Request to get all TypeAdmissionStatuses");
        List<TypeAdmissionStatus> result = typeAdmissionStatusRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeAdmissionStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeAdmissionStatus findOne(Long id) {
        log.debug("Request to get TypeAdmissionStatus : {}", id);
        TypeAdmissionStatus typeAdmissionStatus = typeAdmissionStatusRepository.findOne(id);
        return typeAdmissionStatus;
    }

    /**
     *  Delete the  typeAdmissionStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAdmissionStatus : {}", id);
      TypeAdmissionStatus typeAdmissionStatus = typeAdmissionStatusRepository.findOne(id);
      typeAdmissionStatus.setDelStatus(true);
      TypeAdmissionStatus result = typeAdmissionStatusRepository.save(typeAdmissionStatus);
      
      typeAdmissionStatusSearchRepository.save(result);
    }

    /**
     * Search for the typeAdmissionStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeAdmissionStatus> search(String query) {
        log.debug("Request to search TypeAdmissionStatuses for query {}", query);
        return StreamSupport
            .stream(typeAdmissionStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
