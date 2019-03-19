package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeDosageService;
import co.tmunited.bluebook.domain.TypeDosage;
import co.tmunited.bluebook.repository.TypeDosageRepository;
import co.tmunited.bluebook.repository.search.TypeDosageSearchRepository;
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
 * Service Implementation for managing TypeDosage.
 */
@Service
@Transactional
public class TypeDosageServiceImpl implements TypeDosageService{

    private final Logger log = LoggerFactory.getLogger(TypeDosageServiceImpl.class);
    
    @Inject
    private TypeDosageRepository typeDosageRepository;

    @Inject
    private TypeDosageSearchRepository typeDosageSearchRepository;

    /**
     * Save a typeDosage.
     *
     * @param typeDosage the entity to save
     * @return the persisted entity
     */
    public TypeDosage save(TypeDosage typeDosage) {
        log.debug("Request to save TypeDosage : {}", typeDosage);
        TypeDosage result = typeDosageRepository.save(typeDosage);
        typeDosageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeDosages.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeDosage> findAll() {
        log.debug("Request to get all TypeDosages");
        List<TypeDosage> result = typeDosageRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeDosage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeDosage findOne(Long id) {
        log.debug("Request to get TypeDosage : {}", id);
        TypeDosage typeDosage = typeDosageRepository.findOne(id);
        return typeDosage;
    }

    /**
     *  Delete the  typeDosage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeDosage : {}", id);
      TypeDosage typeDosage = typeDosageRepository.findOne(id);
      typeDosage.setDelStatus(true);
      TypeDosage result = typeDosageRepository.save(typeDosage);
      
      typeDosageSearchRepository.save(result);
    }

    /**
     * Search for the typeDosage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeDosage> search(String query) {
        log.debug("Request to search TypeDosages for query {}", query);
        return StreamSupport
            .stream(typeDosageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
