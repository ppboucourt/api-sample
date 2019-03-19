package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePatientContactsRelationshipService;
import co.tmunited.bluebook.domain.TypePatientContactsRelationship;
import co.tmunited.bluebook.repository.TypePatientContactsRelationshipRepository;
import co.tmunited.bluebook.repository.search.TypePatientContactsRelationshipSearchRepository;
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
 * Service Implementation for managing TypePatientContactsRelationship.
 */
@Service
@Transactional
public class TypePatientContactsRelationshipServiceImpl implements TypePatientContactsRelationshipService{

    private final Logger log = LoggerFactory.getLogger(TypePatientContactsRelationshipServiceImpl.class);
    
    @Inject
    private TypePatientContactsRelationshipRepository typePatientContactsRelationshipRepository;

    @Inject
    private TypePatientContactsRelationshipSearchRepository typePatientContactsRelationshipSearchRepository;

    /**
     * Save a typePatientContactsRelationship.
     *
     * @param typePatientContactsRelationship the entity to save
     * @return the persisted entity
     */
    public TypePatientContactsRelationship save(TypePatientContactsRelationship typePatientContactsRelationship) {
        log.debug("Request to save TypePatientContactsRelationship : {}", typePatientContactsRelationship);
        TypePatientContactsRelationship result = typePatientContactsRelationshipRepository.save(typePatientContactsRelationship);
        typePatientContactsRelationshipSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePatientContactsRelationships.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePatientContactsRelationship> findAll() {
        log.debug("Request to get all TypePatientContactsRelationships");
        List<TypePatientContactsRelationship> result = typePatientContactsRelationshipRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePatientContactsRelationship by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePatientContactsRelationship findOne(Long id) {
        log.debug("Request to get TypePatientContactsRelationship : {}", id);
        TypePatientContactsRelationship typePatientContactsRelationship = typePatientContactsRelationshipRepository.findOne(id);
        return typePatientContactsRelationship;
    }

    /**
     *  Delete the  typePatientContactsRelationship by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePatientContactsRelationship : {}", id);
      TypePatientContactsRelationship typePatientContactsRelationship = typePatientContactsRelationshipRepository.findOne(id);
      typePatientContactsRelationship.setDelStatus(true);
      TypePatientContactsRelationship result = typePatientContactsRelationshipRepository.save(typePatientContactsRelationship);
      
      typePatientContactsRelationshipSearchRepository.save(result);
    }

    /**
     * Search for the typePatientContactsRelationship corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientContactsRelationship> search(String query) {
        log.debug("Request to search TypePatientContactsRelationships for query {}", query);
        return StreamSupport
            .stream(typePatientContactsRelationshipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
