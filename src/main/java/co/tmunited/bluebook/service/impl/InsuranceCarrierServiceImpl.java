package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.repository.search.Icd10SearchRepository;
import co.tmunited.bluebook.service.InsuranceCarrierService;
import co.tmunited.bluebook.domain.InsuranceCarrier;
import co.tmunited.bluebook.repository.InsuranceCarrierRepository;
import co.tmunited.bluebook.repository.search.InsuranceCarrierSearchRepository;
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
 * Service Implementation for managing InsuranceCarrier.
 */
@Service
@Transactional
public class InsuranceCarrierServiceImpl implements InsuranceCarrierService{

    private final Logger log = LoggerFactory.getLogger(InsuranceCarrierServiceImpl.class);

    @Inject
    private InsuranceCarrierRepository insuranceCarrierRepository;

    @Inject
    private InsuranceCarrierSearchRepository insuranceCarrierSearchRepository;

    @Inject
    private Icd10SearchRepository icd10SearchRepository;

    /**
     * Save a insuranceCarrier.
     *
     * @param insuranceCarrier the entity to save
     * @return the persisted entity
     */
    public InsuranceCarrier save(InsuranceCarrier insuranceCarrier) {
        log.debug("Request to save InsuranceCarrier : {}", insuranceCarrier);
        InsuranceCarrier result = insuranceCarrierRepository.save(insuranceCarrier);
        insuranceCarrierSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the insuranceCarriers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsuranceCarrier> findAll() {
        log.debug("Request to get all InsuranceCarriers");
        List<InsuranceCarrier> result = insuranceCarrierRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one insuranceCarrier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public InsuranceCarrier findOne(Long id) {
        log.debug("Request to get InsuranceCarrier : {}", id);
        InsuranceCarrier insuranceCarrier = insuranceCarrierRepository.findOne(id);
        return insuranceCarrier;
    }

    /**
     *  Delete the  insuranceCarrier by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsuranceCarrier : {}", id);
      InsuranceCarrier insuranceCarrier = insuranceCarrierRepository.findOne(id);
      insuranceCarrier.setDelStatus(true);
      InsuranceCarrier result = insuranceCarrierRepository.save(insuranceCarrier);

      insuranceCarrierSearchRepository.save(result);
    }

    /**
     * Search for the insuranceCarrier corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsuranceCarrier> search(String query) {
        log.debug("Request to search InsuranceCarriers for query {}", query);
        List<InsuranceCarrier> result = StreamSupport
            .stream(insuranceCarrierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());

        return result;
    }
}
