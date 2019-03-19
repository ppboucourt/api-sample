package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeMaritalStatusService;
import co.tmunited.bluebook.domain.TypeMaritalStatus;
import co.tmunited.bluebook.repository.TypeMaritalStatusRepository;
import co.tmunited.bluebook.repository.search.TypeMaritalStatusSearchRepository;
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
 * Service Implementation for managing TypeMaritalStatus.
 */
@Service
@Transactional
public class TypeMaritalStatusServiceImpl implements TypeMaritalStatusService{

    private final Logger log = LoggerFactory.getLogger(TypeMaritalStatusServiceImpl.class);
    
    @Inject
    private TypeMaritalStatusRepository typeMaritalStatusRepository;

    @Inject
    private TypeMaritalStatusSearchRepository typeMaritalStatusSearchRepository;

    /**
     * Save a typeMaritalStatus.
     *
     * @param typeMaritalStatus the entity to save
     * @return the persisted entity
     */
    public TypeMaritalStatus save(TypeMaritalStatus typeMaritalStatus) {
        log.debug("Request to save TypeMaritalStatus : {}", typeMaritalStatus);
        TypeMaritalStatus result = typeMaritalStatusRepository.save(typeMaritalStatus);
        typeMaritalStatusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeMaritalStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeMaritalStatus> findAll() {
        log.debug("Request to get all TypeMaritalStatuses");
        List<TypeMaritalStatus> result = typeMaritalStatusRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeMaritalStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeMaritalStatus findOne(Long id) {
        log.debug("Request to get TypeMaritalStatus : {}", id);
        TypeMaritalStatus typeMaritalStatus = typeMaritalStatusRepository.findOne(id);
        return typeMaritalStatus;
    }

    /**
     *  Delete the  typeMaritalStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeMaritalStatus : {}", id);
      TypeMaritalStatus typeMaritalStatus = typeMaritalStatusRepository.findOne(id);
      typeMaritalStatus.setDelStatus(true);
      TypeMaritalStatus result = typeMaritalStatusRepository.save(typeMaritalStatus);
      
      typeMaritalStatusSearchRepository.save(result);
    }

    /**
     * Search for the typeMaritalStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeMaritalStatus> search(String query) {
        log.debug("Request to search TypeMaritalStatuses for query {}", query);
        return StreamSupport
            .stream(typeMaritalStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
