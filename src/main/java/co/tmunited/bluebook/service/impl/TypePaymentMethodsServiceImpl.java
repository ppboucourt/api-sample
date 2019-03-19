package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePaymentMethodsService;
import co.tmunited.bluebook.domain.TypePaymentMethods;
import co.tmunited.bluebook.repository.TypePaymentMethodsRepository;
import co.tmunited.bluebook.repository.search.TypePaymentMethodsSearchRepository;
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
 * Service Implementation for managing TypePaymentMethods.
 */
@Service
@Transactional
public class TypePaymentMethodsServiceImpl implements TypePaymentMethodsService{

    private final Logger log = LoggerFactory.getLogger(TypePaymentMethodsServiceImpl.class);
    
    @Inject
    private TypePaymentMethodsRepository typePaymentMethodsRepository;

    @Inject
    private TypePaymentMethodsSearchRepository typePaymentMethodsSearchRepository;

    /**
     * Save a typePaymentMethods.
     *
     * @param typePaymentMethods the entity to save
     * @return the persisted entity
     */
    public TypePaymentMethods save(TypePaymentMethods typePaymentMethods) {
        log.debug("Request to save TypePaymentMethods : {}", typePaymentMethods);
        TypePaymentMethods result = typePaymentMethodsRepository.save(typePaymentMethods);
        typePaymentMethodsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePaymentMethods.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePaymentMethods> findAll() {
        log.debug("Request to get all TypePaymentMethods");
        List<TypePaymentMethods> result = typePaymentMethodsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePaymentMethods by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePaymentMethods findOne(Long id) {
        log.debug("Request to get TypePaymentMethods : {}", id);
        TypePaymentMethods typePaymentMethods = typePaymentMethodsRepository.findOne(id);
        return typePaymentMethods;
    }

    /**
     *  Delete the  typePaymentMethods by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePaymentMethods : {}", id);
      TypePaymentMethods typePaymentMethods = typePaymentMethodsRepository.findOne(id);
      typePaymentMethods.setDelStatus(true);
      TypePaymentMethods result = typePaymentMethodsRepository.save(typePaymentMethods);
      
      typePaymentMethodsSearchRepository.save(result);
    }

    /**
     * Search for the typePaymentMethods corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePaymentMethods> search(String query) {
        log.debug("Request to search TypePaymentMethods for query {}", query);
        return StreamSupport
            .stream(typePaymentMethodsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
