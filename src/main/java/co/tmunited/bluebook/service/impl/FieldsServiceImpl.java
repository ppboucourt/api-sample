package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.FieldsService;
import co.tmunited.bluebook.domain.Fields;
import co.tmunited.bluebook.repository.FieldsRepository;
import co.tmunited.bluebook.repository.search.FieldsSearchRepository;
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
 * Service Implementation for managing Fields.
 */
@Service
@Transactional
public class FieldsServiceImpl implements FieldsService{

    private final Logger log = LoggerFactory.getLogger(FieldsServiceImpl.class);
    
    @Inject
    private FieldsRepository fieldsRepository;

    @Inject
    private FieldsSearchRepository fieldsSearchRepository;

    /**
     * Save a fields.
     *
     * @param fields the entity to save
     * @return the persisted entity
     */
    public Fields save(Fields fields) {
        log.debug("Request to save Fields : {}", fields);
        Fields result = fieldsRepository.save(fields);
        fieldsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the fields.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Fields> findAll() {
        log.debug("Request to get all Fields");
        List<Fields> result = fieldsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one fields by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Fields findOne(Long id) {
        log.debug("Request to get Fields : {}", id);
        Fields fields = fieldsRepository.findOne(id);
        return fields;
    }

    /**
     *  Delete the  fields by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fields : {}", id);
      Fields fields = fieldsRepository.findOne(id);
      fields.setDelStatus(true);
      Fields result = fieldsRepository.save(fields);
      
      fieldsSearchRepository.save(result);
    }

    /**
     * Search for the fields corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Fields> search(String query) {
        log.debug("Request to search Fields for query {}", query);
        return StreamSupport
            .stream(fieldsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
