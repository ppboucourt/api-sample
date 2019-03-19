package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.TypeTreatmentPlanStatusesService;
import co.tmunited.bluebook.domain.TypeTreatmentPlanStatuses;
import co.tmunited.bluebook.repository.TypeTreatmentPlanStatusesRepository;
import co.tmunited.bluebook.repository.search.TypeTreatmentPlanStatusesSearchRepository;
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
 * Service Implementation for managing TypeTreatmentPlanStatuses.
 */
@Service
@Transactional
public class TypeTreatmentPlanStatusesServiceImpl implements TypeTreatmentPlanStatusesService{

    private final Logger log = LoggerFactory.getLogger(TypeTreatmentPlanStatusesServiceImpl.class);
    
    @Inject
    private TypeTreatmentPlanStatusesRepository typeTreatmentPlanStatusesRepository;

    @Inject
    private TypeTreatmentPlanStatusesSearchRepository typeTreatmentPlanStatusesSearchRepository;

    /**
     * Save a typeTreatmentPlanStatuses.
     *
     * @param typeTreatmentPlanStatuses the entity to save
     * @return the persisted entity
     */
    public TypeTreatmentPlanStatuses save(TypeTreatmentPlanStatuses typeTreatmentPlanStatuses) {
        log.debug("Request to save TypeTreatmentPlanStatuses : {}", typeTreatmentPlanStatuses);
        TypeTreatmentPlanStatuses result = typeTreatmentPlanStatusesRepository.save(typeTreatmentPlanStatuses);
        typeTreatmentPlanStatusesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeTreatmentPlanStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TypeTreatmentPlanStatuses> findAll() {
        log.debug("Request to get all TypeTreatmentPlanStatuses");
        List<TypeTreatmentPlanStatuses> result = typeTreatmentPlanStatusesRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one typeTreatmentPlanStatuses by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TypeTreatmentPlanStatuses findOne(Long id) {
        log.debug("Request to get TypeTreatmentPlanStatuses : {}", id);
        TypeTreatmentPlanStatuses typeTreatmentPlanStatuses = typeTreatmentPlanStatusesRepository.findOne(id);
        return typeTreatmentPlanStatuses;
    }

    /**
     *  Delete the  typeTreatmentPlanStatuses by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeTreatmentPlanStatuses : {}", id);
      TypeTreatmentPlanStatuses typeTreatmentPlanStatuses = typeTreatmentPlanStatusesRepository.findOne(id);
      typeTreatmentPlanStatuses.setDelStatus(true);
      TypeTreatmentPlanStatuses result = typeTreatmentPlanStatusesRepository.save(typeTreatmentPlanStatuses);
      
      typeTreatmentPlanStatusesSearchRepository.save(result);
    }

    /**
     * Search for the typeTreatmentPlanStatuses corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TypeTreatmentPlanStatuses> search(String query) {
        log.debug("Request to search TypeTreatmentPlanStatuses for query {}", query);
        return StreamSupport
            .stream(typeTreatmentPlanStatusesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
