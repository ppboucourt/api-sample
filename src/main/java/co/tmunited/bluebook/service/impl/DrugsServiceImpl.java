package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.DrugsService;
import co.tmunited.bluebook.domain.Drugs;
import co.tmunited.bluebook.repository.DrugsRepository;
import co.tmunited.bluebook.repository.search.DrugsSearchRepository;
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
 * Service Implementation for managing Drugs.
 */
@Service
@Transactional
public class DrugsServiceImpl implements DrugsService{

    private final Logger log = LoggerFactory.getLogger(DrugsServiceImpl.class);

    @Inject
    private DrugsRepository drugsRepository;

    @Inject
    private DrugsSearchRepository drugsSearchRepository;

    /**
     * Save a drugs.
     *
     * @param drugs the entity to save
     * @return the persisted entity
     */
    public Drugs save(Drugs drugs) {
        log.debug("Request to save Drugs : {}", drugs);
        Drugs result = drugsRepository.save(drugs);
        drugsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the drugs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Drugs> findAll() {
        log.debug("Request to get all Drugs");
        List<Drugs> result = drugsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one drugs by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Drugs findOne(Long id) {
        log.debug("Request to get Drugs : {}", id);
        Drugs drugs = drugsRepository.findOne(id);
        return drugs;
    }

    /**
     *  Delete the  drugs by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Drugs : {}", id);
      Drugs drugs = drugsRepository.findOne(id);
      drugs.setDelStatus(true);
      Drugs result = drugsRepository.save(drugs);

      drugsSearchRepository.save(result);
    }

    /**
     * Search for the drugs corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Drugs> search(String query) {
        log.debug("Request to search Drugs for query {}", query);
        List<Drugs> result = StreamSupport
            .stream(drugsSearchRepository.search(fuzzyQuery("name", query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }
}
