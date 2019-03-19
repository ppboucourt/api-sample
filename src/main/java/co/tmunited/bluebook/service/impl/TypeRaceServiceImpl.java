package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeRaceService;
import co.tmunited.bluebook.domain.TypeRace;
import co.tmunited.bluebook.repository.TypeRaceRepository;
import co.tmunited.bluebook.repository.search.TypeRaceSearchRepository;
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
 * Service Implementation for managing TypeRace.
 */
@Service
@Transactional
public class TypeRaceServiceImpl implements TypeRaceService{

    private final Logger log = LoggerFactory.getLogger(TypeRaceServiceImpl.class);
    
    @Inject
    private TypeRaceRepository typeRaceRepository;

    @Inject
    private TypeRaceSearchRepository typeRaceSearchRepository;

    /**
     * Save a typeRace.
     *
     * @param typeRace the entity to save
     * @return the persisted entity
     */
    public TypeRace save(TypeRace typeRace) {
        log.debug("Request to save TypeRace : {}", typeRace);
        TypeRace result = typeRaceRepository.save(typeRace);
        typeRaceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeRaces.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeRace> findAll() {
        log.debug("Request to get all TypeRaces");
        List<TypeRace> result = typeRaceRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeRace by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeRace findOne(Long id) {
        log.debug("Request to get TypeRace : {}", id);
        TypeRace typeRace = typeRaceRepository.findOne(id);
        return typeRace;
    }

    /**
     *  Delete the  typeRace by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeRace : {}", id);
      TypeRace typeRace = typeRaceRepository.findOne(id);
      typeRace.setDelStatus(true);
      TypeRace result = typeRaceRepository.save(typeRace);
      
      typeRaceSearchRepository.save(result);
    }

    /**
     * Search for the typeRace corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeRace> search(String query) {
        log.debug("Request to search TypeRaces for query {}", query);
        return StreamSupport
            .stream(typeRaceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
