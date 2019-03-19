package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePatientContactTypesService;
import co.tmunited.bluebook.domain.TypePatientContactTypes;
import co.tmunited.bluebook.repository.TypePatientContactTypesRepository;
import co.tmunited.bluebook.repository.search.TypePatientContactTypesSearchRepository;
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
 * Service Implementation for managing TypePatientContactTypes.
 */
@Service
@Transactional
public class TypePatientContactTypesServiceImpl implements TypePatientContactTypesService{

    private final Logger log = LoggerFactory.getLogger(TypePatientContactTypesServiceImpl.class);
    
    @Inject
    private TypePatientContactTypesRepository typePatientContactTypesRepository;

    @Inject
    private TypePatientContactTypesSearchRepository typePatientContactTypesSearchRepository;

    /**
     * Save a typePatientContactTypes.
     *
     * @param typePatientContactTypes the entity to save
     * @return the persisted entity
     */
    public TypePatientContactTypes save(TypePatientContactTypes typePatientContactTypes) {
        log.debug("Request to save TypePatientContactTypes : {}", typePatientContactTypes);
        TypePatientContactTypes result = typePatientContactTypesRepository.save(typePatientContactTypes);
        typePatientContactTypesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePatientContactTypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePatientContactTypes> findAll() {
        log.debug("Request to get all TypePatientContactTypes");
        List<TypePatientContactTypes> result = typePatientContactTypesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePatientContactTypes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePatientContactTypes findOne(Long id) {
        log.debug("Request to get TypePatientContactTypes : {}", id);
        TypePatientContactTypes typePatientContactTypes = typePatientContactTypesRepository.findOne(id);
        return typePatientContactTypes;
    }

    /**
     *  Delete the  typePatientContactTypes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePatientContactTypes : {}", id);
      TypePatientContactTypes typePatientContactTypes = typePatientContactTypesRepository.findOne(id);
      typePatientContactTypes.setDelStatus(true);
      TypePatientContactTypes result = typePatientContactTypesRepository.save(typePatientContactTypes);
      
      typePatientContactTypesSearchRepository.save(result);
    }

    /**
     * Search for the typePatientContactTypes corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientContactTypes> search(String query) {
        log.debug("Request to search TypePatientContactTypes for query {}", query);
        return StreamSupport
            .stream(typePatientContactTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
