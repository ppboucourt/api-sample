package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePersonService;
import co.tmunited.bluebook.domain.TypePerson;
import co.tmunited.bluebook.repository.TypePersonRepository;
import co.tmunited.bluebook.repository.search.TypePersonSearchRepository;
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
 * Service Implementation for managing TypePerson.
 */
@Service
@Transactional
public class TypePersonServiceImpl implements TypePersonService{

    private final Logger log = LoggerFactory.getLogger(TypePersonServiceImpl.class);
    
    @Inject
    private TypePersonRepository typePersonRepository;

    @Inject
    private TypePersonSearchRepository typePersonSearchRepository;

    /**
     * Save a typePerson.
     *
     * @param typePerson the entity to save
     * @return the persisted entity
     */
    public TypePerson save(TypePerson typePerson) {
        log.debug("Request to save TypePerson : {}", typePerson);
        TypePerson result = typePersonRepository.save(typePerson);
        typePersonSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePeople.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePerson> findAll() {
        log.debug("Request to get all TypePeople");
        List<TypePerson> result = typePersonRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePerson by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePerson findOne(Long id) {
        log.debug("Request to get TypePerson : {}", id);
        TypePerson typePerson = typePersonRepository.findOne(id);
        return typePerson;
    }

    /**
     *  Delete the  typePerson by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePerson : {}", id);
      TypePerson typePerson = typePersonRepository.findOne(id);
      typePerson.setDelStatus(true);
      TypePerson result = typePersonRepository.save(typePerson);
      
      typePersonSearchRepository.save(result);
    }

    /**
     * Search for the typePerson corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePerson> search(String query) {
        log.debug("Request to search TypePeople for query {}", query);
        return StreamSupport
            .stream(typePersonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
