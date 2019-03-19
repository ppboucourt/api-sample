package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.CountryStateService;
import co.tmunited.bluebook.domain.CountryState;
import co.tmunited.bluebook.repository.CountryStateRepository;
import co.tmunited.bluebook.repository.search.CountryStateSearchRepository;
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
 * Service Implementation for managing CountryState.
 */
@Service
@Transactional
public class CountryStateServiceImpl implements CountryStateService{

    private final Logger log = LoggerFactory.getLogger(CountryStateServiceImpl.class);
    
    @Inject
    private CountryStateRepository countryStateRepository;

    @Inject
    private CountryStateSearchRepository countryStateSearchRepository;

    /**
     * Save a countryState.
     *
     * @param countryState the entity to save
     * @return the persisted entity
     */
    public CountryState save(CountryState countryState) {
        log.debug("Request to save CountryState : {}", countryState);
        CountryState result = countryStateRepository.save(countryState);
        countryStateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the countryStates.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CountryState> findAll() {
        log.debug("Request to get all CountryStates");
        List<CountryState> result = countryStateRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one countryState by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CountryState findOne(Long id) {
        log.debug("Request to get CountryState : {}", id);
        CountryState countryState = countryStateRepository.findOne(id);
        return countryState;
    }

    /**
     *  Delete the  countryState by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CountryState : {}", id);
      CountryState countryState = countryStateRepository.findOne(id);
      countryState.setDelStatus(true);
      CountryState result = countryStateRepository.save(countryState);
      
      countryStateSearchRepository.save(result);
    }

    /**
     * Search for the countryState corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CountryState> search(String query) {
        log.debug("Request to search CountryStates for query {}", query);
        return StreamSupport
            .stream(countryStateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
