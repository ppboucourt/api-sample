package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TablesService;
import co.tmunited.bluebook.domain.Tables;
import co.tmunited.bluebook.repository.TablesRepository;
import co.tmunited.bluebook.repository.search.TablesSearchRepository;
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
 * Service Implementation for managing Tables.
 */
@Service
@Transactional
public class TablesServiceImpl implements TablesService{

    private final Logger log = LoggerFactory.getLogger(TablesServiceImpl.class);
    
    @Inject
    private TablesRepository tablesRepository;

    @Inject
    private TablesSearchRepository tablesSearchRepository;

    /**
     * Save a tables.
     *
     * @param tables the entity to save
     * @return the persisted entity
     */
    public Tables save(Tables tables) {
        log.debug("Request to save Tables : {}", tables);
        Tables result = tablesRepository.save(tables);
        tablesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tables.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Tables> findAll() {
        log.debug("Request to get all Tables");
        List<Tables> result = tablesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one tables by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tables findOne(Long id) {
        log.debug("Request to get Tables : {}", id);
        Tables tables = tablesRepository.findOne(id);
        return tables;
    }

    /**
     *  Delete the  tables by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tables : {}", id);
      Tables tables = tablesRepository.findOne(id);
      tables.setDelStatus(true);
      Tables result = tablesRepository.save(tables);
      
      tablesSearchRepository.save(result);
    }

    /**
     * Search for the tables corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Tables> search(String query) {
        log.debug("Request to search Tables for query {}", query);
        return StreamSupport
            .stream(tablesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
