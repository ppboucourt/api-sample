package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeFoodDietsService;
import co.tmunited.bluebook.domain.TypeFoodDiets;
import co.tmunited.bluebook.repository.TypeFoodDietsRepository;
import co.tmunited.bluebook.repository.search.TypeFoodDietsSearchRepository;
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
 * Service Implementation for managing TypeFoodDiets.
 */
@Service
@Transactional
public class TypeFoodDietsServiceImpl implements TypeFoodDietsService{

    private final Logger log = LoggerFactory.getLogger(TypeFoodDietsServiceImpl.class);
    
    @Inject
    private TypeFoodDietsRepository typeFoodDietsRepository;

    @Inject
    private TypeFoodDietsSearchRepository typeFoodDietsSearchRepository;

    /**
     * Save a typeFoodDiets.
     *
     * @param typeFoodDiets the entity to save
     * @return the persisted entity
     */
    public TypeFoodDiets save(TypeFoodDiets typeFoodDiets) {
        log.debug("Request to save TypeFoodDiets : {}", typeFoodDiets);
        TypeFoodDiets result = typeFoodDietsRepository.save(typeFoodDiets);
        typeFoodDietsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeFoodDiets.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeFoodDiets> findAll() {
        log.debug("Request to get all TypeFoodDiets");
        List<TypeFoodDiets> result = typeFoodDietsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeFoodDiets by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeFoodDiets findOne(Long id) {
        log.debug("Request to get TypeFoodDiets : {}", id);
        TypeFoodDiets typeFoodDiets = typeFoodDietsRepository.findOne(id);
        return typeFoodDiets;
    }

    /**
     *  Delete the  typeFoodDiets by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeFoodDiets : {}", id);
      TypeFoodDiets typeFoodDiets = typeFoodDietsRepository.findOne(id);
      typeFoodDiets.setDelStatus(true);
      TypeFoodDiets result = typeFoodDietsRepository.save(typeFoodDiets);
      
      typeFoodDietsSearchRepository.save(result);
    }

    /**
     * Search for the typeFoodDiets corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeFoodDiets> search(String query) {
        log.debug("Request to search TypeFoodDiets for query {}", query);
        return StreamSupport
            .stream(typeFoodDietsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
