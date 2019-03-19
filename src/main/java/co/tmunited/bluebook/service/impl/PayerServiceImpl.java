package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.PayerService;
import co.tmunited.bluebook.domain.Payer;
import co.tmunited.bluebook.repository.PayerRepository;
import co.tmunited.bluebook.repository.search.PayerSearchRepository;
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
 * Service Implementation for managing Payer.
 */
@Service
@Transactional
public class PayerServiceImpl implements PayerService{

    private final Logger log = LoggerFactory.getLogger(PayerServiceImpl.class);
    
    @Inject
    private PayerRepository payerRepository;

    @Inject
    private PayerSearchRepository payerSearchRepository;

    /**
     * Save a payer.
     *
     * @param payer the entity to save
     * @return the persisted entity
     */
    public Payer save(Payer payer) {
        log.debug("Request to save Payer : {}", payer);
        Payer result = payerRepository.save(payer);
        payerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the payers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Payer> findAll() {
        log.debug("Request to get all Payers");
        List<Payer> result = payerRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one payer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Payer findOne(Long id) {
        log.debug("Request to get Payer : {}", id);
        Payer payer = payerRepository.findOne(id);
        return payer;
    }

    /**
     *  Delete the  payer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Payer : {}", id);
      Payer payer = payerRepository.findOne(id);
      payer.setDelStatus(true);
      Payer result = payerRepository.save(payer);
      
      payerSearchRepository.save(result);
    }

    /**
     * Search for the payer corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Payer> search(String query) {
        log.debug("Request to search Payers for query {}", query);
        return StreamSupport
            .stream(payerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
