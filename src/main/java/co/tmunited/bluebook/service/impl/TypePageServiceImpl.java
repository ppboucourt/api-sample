package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypePageService;
import co.tmunited.bluebook.domain.TypePage;
import co.tmunited.bluebook.repository.TypePageRepository;
import co.tmunited.bluebook.repository.search.TypePageSearchRepository;
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
 * Service Implementation for managing TypePage.
 */
@Service
@Transactional
public class TypePageServiceImpl implements TypePageService{

    private final Logger log = LoggerFactory.getLogger(TypePageServiceImpl.class);
    
    @Inject
    private TypePageRepository typePageRepository;

    @Inject
    private TypePageSearchRepository typePageSearchRepository;

    /**
     * Save a typePage.
     *
     * @param typePage the entity to save
     * @return the persisted entity
     */
    public TypePage save(TypePage typePage) {
        log.debug("Request to save TypePage : {}", typePage);
        TypePage result = typePageRepository.save(typePage);
        typePageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typePages.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypePage> findAll() {
        log.debug("Request to get all TypePages");
        List<TypePage> result = typePageRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typePage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypePage findOne(Long id) {
        log.debug("Request to get TypePage : {}", id);
        TypePage typePage = typePageRepository.findOne(id);
        return typePage;
    }

    /**
     *  Delete the  typePage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypePage : {}", id);
      TypePage typePage = typePageRepository.findOne(id);
      typePage.setDelStatus(true);
      TypePage result = typePageRepository.save(typePage);
      
      typePageSearchRepository.save(result);
    }

    /**
     * Search for the typePage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypePage> search(String query) {
        log.debug("Request to search TypePages for query {}", query);
        return StreamSupport
            .stream(typePageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
