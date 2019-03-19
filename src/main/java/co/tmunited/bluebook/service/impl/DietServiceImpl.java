package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.DietService;
import co.tmunited.bluebook.domain.Diet;
import co.tmunited.bluebook.repository.DietRepository;
import co.tmunited.bluebook.repository.search.DietSearchRepository;
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
 * Service Implementation for managing Diet.
 */
@Service
@Transactional
public class DietServiceImpl implements DietService{

    private final Logger log = LoggerFactory.getLogger(DietServiceImpl.class);

    @Inject
    private DietRepository dietRepository;

    @Inject
    private DietSearchRepository dietSearchRepository;

    /**
     * Save a diet.
     *
     * @param diet the entity to save
     * @return the persisted entity
     */
    public Diet save(Diet diet) {
        log.debug("Request to save Diet : {}", diet);
        Diet result = dietRepository.save(diet);
        dietSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the diets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Diet> findAll() {
        log.debug("Request to get all Diets");
        List<Diet> result = dietRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one diet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Diet findOne(Long id) {
        log.debug("Request to get Diet : {}", id);
        Diet diet = dietRepository.findOne(id);
        return diet;
    }

    /**
     *  Delete the  diet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diet : {}", id);
      Diet diet = dietRepository.findOne(id);
      diet.setDelStatus(true);
      Diet result = dietRepository.save(diet);

      dietSearchRepository.save(result);
    }

    /**
     * Search for the diet corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Diet> search(String query) {
        log.debug("Request to search Diets for query {}", query);
        return StreamSupport
            .stream(dietSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
