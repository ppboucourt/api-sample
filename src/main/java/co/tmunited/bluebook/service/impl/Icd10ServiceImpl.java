package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.Icd10Service;
import co.tmunited.bluebook.domain.Icd10;
import co.tmunited.bluebook.repository.Icd10Repository;
import co.tmunited.bluebook.repository.search.Icd10SearchRepository;
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
 * Service Implementation for managing Icd10.
 */
@Service
@Transactional
public class Icd10ServiceImpl implements Icd10Service{

    private final Logger log = LoggerFactory.getLogger(Icd10ServiceImpl.class);

    @Inject
    private Icd10Repository icd10Repository;

    @Inject
    private Icd10SearchRepository icd10SearchRepository;

    /**
     * Save a icd10.
     *
     * @param icd10 the entity to save
     * @return the persisted entity
     */
    public Icd10 save(Icd10 icd10) {
        log.debug("Request to save Icd10 : {}", icd10);
        Icd10 result = icd10Repository.save(icd10);
        icd10SearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the icd10S.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Icd10> findAll() {
        log.debug("Request to get all Icd10S");
        List<Icd10> result = icd10Repository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one icd10 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Icd10 findOne(Long id) {
        log.debug("Request to get Icd10 : {}", id);
        Icd10 icd10 = icd10Repository.findOne(id);
        return icd10;
    }

    /**
     *  Delete the  icd10 by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Icd10 : {}", id);
      Icd10 icd10 = icd10Repository.findOne(id);
      icd10.setDelStatus(true);
      Icd10 result = icd10Repository.save(icd10);

      icd10SearchRepository.save(result);
    }

    /**
     * Search for the icd10 corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Icd10> search(String query) {
        log.debug("Request to search Icd10S for query {}", query);
        query = query + "*";
        List<Icd10> result = StreamSupport
            .stream(icd10SearchRepository.search(queryStringQuery(query)).spliterator(), false).limit(100)
            .collect(Collectors.toList());

        return result;
    }
}
