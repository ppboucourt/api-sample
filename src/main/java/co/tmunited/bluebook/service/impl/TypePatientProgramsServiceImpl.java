package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePatientProgramsService;
import co.tmunited.bluebook.domain.TypePatientPrograms;
import co.tmunited.bluebook.repository.TypePatientProgramsRepository;
import co.tmunited.bluebook.repository.search.TypePatientProgramsSearchRepository;
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
 * Service Implementation for managing TypePatientPrograms.
 */
@Service
@Transactional
public class TypePatientProgramsServiceImpl implements TypePatientProgramsService{

    private final Logger log = LoggerFactory.getLogger(TypePatientProgramsServiceImpl.class);
    
    @Inject
    private TypePatientProgramsRepository typePatientProgramsRepository;

    @Inject
    private TypePatientProgramsSearchRepository typePatientProgramsSearchRepository;

    /**
     * Save a typePatientPrograms.
     *
     * @param typePatientPrograms the entity to save
     * @return the persisted entity
     */
    public TypePatientPrograms save(TypePatientPrograms typePatientPrograms) {
        log.debug("Request to save TypePatientPrograms : {}", typePatientPrograms);
        TypePatientPrograms result = typePatientProgramsRepository.save(typePatientPrograms);
        typePatientProgramsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePatientPrograms.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePatientPrograms> findAll() {
        log.debug("Request to get all TypePatientPrograms");
        List<TypePatientPrograms> result = typePatientProgramsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePatientPrograms by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePatientPrograms findOne(Long id) {
        log.debug("Request to get TypePatientPrograms : {}", id);
        TypePatientPrograms typePatientPrograms = typePatientProgramsRepository.findOne(id);
        return typePatientPrograms;
    }

    /**
     *  Delete the  typePatientPrograms by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePatientPrograms : {}", id);
      TypePatientPrograms typePatientPrograms = typePatientProgramsRepository.findOne(id);
      typePatientPrograms.setDelStatus(true);
      TypePatientPrograms result = typePatientProgramsRepository.save(typePatientPrograms);
      
      typePatientProgramsSearchRepository.save(result);
    }

    /**
     * Search for the typePatientPrograms corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePatientPrograms> search(String query) {
        log.debug("Request to search TypePatientPrograms for query {}", query);
        return StreamSupport
            .stream(typePatientProgramsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
