package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.InsuranceService;
import co.tmunited.bluebook.domain.Insurance;
import co.tmunited.bluebook.repository.InsuranceRepository;
import co.tmunited.bluebook.repository.search.InsuranceSearchRepository;
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
 * Service Implementation for managing Insurance.
 */
@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService{

    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    @Inject
    private InsuranceRepository insuranceRepository;

    @Inject
    private InsuranceSearchRepository insuranceSearchRepository;

    /**
     * Save a insurance.
     *
     * @param insurance the entity to save
     * @return the persisted entity
     */
    public Insurance save(Insurance insurance) {
        log.debug("Request to save Insurance : {}", insurance);
        Insurance result = insuranceRepository.save(insurance);
        insuranceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the insurances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Insurance> findAll() {
        log.debug("Request to get all Insurances");
        List<Insurance> result = insuranceRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one insurance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Insurance findOne(Long id) {
        log.debug("Request to get Insurance : {}", id);
        Insurance insurance = insuranceRepository.findOne(id);
        return insurance;
    }

    /**
     *  Delete the  insurance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Insurance : {}", id);
      Insurance insurance = insuranceRepository.findOne(id);
      insurance.setDelStatus(true);
      Insurance result = insuranceRepository.save(insurance);

      insuranceSearchRepository.save(result);
    }

    /**
     * Search for the insurance corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Insurance> search(String query) {
        log.debug("Request to search Insurances for query {}", query);
        return StreamSupport
            .stream(insuranceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Insurance findByChartIdAndOrder(Long id, Integer value) {
        Insurance insurance = insuranceRepository.findByChartIdAndInsuranceOrder(id, value);
        return insurance;
    }
}
