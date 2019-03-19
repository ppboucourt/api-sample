package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeEmployeeService;
import co.tmunited.bluebook.domain.TypeEmployee;
import co.tmunited.bluebook.repository.TypeEmployeeRepository;
import co.tmunited.bluebook.repository.search.TypeEmployeeSearchRepository;
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
 * Service Implementation for managing TypeEmployee.
 */
@Service
@Transactional
public class TypeEmployeeServiceImpl implements TypeEmployeeService{

    private final Logger log = LoggerFactory.getLogger(TypeEmployeeServiceImpl.class);

    @Inject
    private TypeEmployeeRepository typeEmployeeRepository;

    @Inject
    private TypeEmployeeSearchRepository typeEmployeeSearchRepository;

    /**
     * Save a typeEmployee.
     *
     * @param typeEmployee the entity to save
     * @return the persisted entity
     */
    public TypeEmployee save(TypeEmployee typeEmployee) {
        log.debug("Request to save TypeEmployee : {}", typeEmployee);
        TypeEmployee result = typeEmployeeRepository.save(typeEmployee);
        typeEmployeeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeEmployees.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeEmployee> findAll() {
        log.debug("Request to get all TypeEmployees");
        List<TypeEmployee> result = typeEmployeeRepository.findAllWithEagerRelationshipsAndDelStatusIsFalse();

        /*for(TypeEmployee e: result){
            e.getAuthorities().size();
        }*/

        return result;
    }

    /**
     *  Get one typeEmployee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TypeEmployee findOne(Long id) {
        log.debug("Request to get TypeEmployee : {}", id);
        TypeEmployee typeEmployee = typeEmployeeRepository.findOne(id);
        return typeEmployee;
    }

    /**
     *  Delete the  typeEmployee by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEmployee : {}", id);
      TypeEmployee typeEmployee = typeEmployeeRepository.findOne(id);
      typeEmployee.setDelStatus(true);
      TypeEmployee result = typeEmployeeRepository.save(typeEmployee);

      typeEmployeeSearchRepository.save(result);
    }

    /**
     * Search for the typeEmployee corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeEmployee> search(String query) {
        log.debug("Request to search TypeEmployees for query {}", query);
        return StreamSupport
            .stream(typeEmployeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
