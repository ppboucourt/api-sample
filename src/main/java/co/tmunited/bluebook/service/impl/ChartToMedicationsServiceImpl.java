package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ChartToMedicationsService;
import co.tmunited.bluebook.domain.ChartToMedications;
import co.tmunited.bluebook.repository.ChartToMedicationsRepository;
import co.tmunited.bluebook.repository.search.ChartToMedicationsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ChartToMedications.
 */
@Service
@Transactional
public class ChartToMedicationsServiceImpl implements ChartToMedicationsService{

    private final Logger log = LoggerFactory.getLogger(ChartToMedicationsServiceImpl.class);

    @Inject
    private ChartToMedicationsRepository chartToMedicationsRepository;

    @Inject
    private ChartToMedicationsSearchRepository chartToMedicationsSearchRepository;

    /**
     * Save a chartToMedications.
     *
     * @param chartToMedications the entity to save
     * @return the persisted entity
     */
    public ChartToMedications save(ChartToMedications chartToMedications) {
        log.debug("Request to save ChartToMedications : {}", chartToMedications);
        ChartToMedications result = chartToMedicationsRepository.save(chartToMedications);
        chartToMedicationsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chartToMedications.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToMedications> findAll() {
        log.debug("Request to get all ChartToMedications");
        List<ChartToMedications> result = chartToMedicationsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one chartToMedications by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChartToMedications findOne(Long id) {
        log.debug("Request to get ChartToMedications : {}", id);
        ChartToMedications chartToMedications = chartToMedicationsRepository.findOne(id);
        return chartToMedications;
    }

    /**
     *  Delete the  chartToMedications by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToMedications : {}", id);
      ChartToMedications chartToMedications = chartToMedicationsRepository.findOne(id);
      chartToMedications.setDelStatus(true);
      ChartToMedications result = chartToMedicationsRepository.save(chartToMedications);

      chartToMedicationsSearchRepository.save(result);
    }

    /**
     * Search for the chartToMedications corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToMedications> search(String query) {
        log.debug("Request to search ChartToMedications for query {}", query);
        return StreamSupport
            .stream(chartToMedicationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search for medications and chart who belong to a one facility
     *
     * @param id from the current facility
     * @return
     */
    public List<ChartToMedications> findAllMedicationsByChartsBelongToFacility(Long id){
        ZonedDateTime now = ZonedDateTime.now();
        List<ChartToMedications> result = chartToMedicationsRepository.findAllMedicationsByPatientInCurrentFacility(id);
        result.removeIf(x -> !x.getDatePrescription().toLocalDate().isEqual(now.toLocalDate()));
        return result;
    }

    /**
     * Search distinct for medications and chart who belong to a one facility
     *
     * @param id
     * @return
     */
    public List<ZonedDateTime> findAllMedicationsByPatientInCurrentFacilityDistinctPrescription(Long id){
        ZonedDateTime now = ZonedDateTime.now();
        List<ZonedDateTime> result = chartToMedicationsRepository.findAllMedicationsByPatientInCurrentFacilityDistinctPrescription(id);
        result.removeIf(x -> !x.toLocalDate().isEqual(now.toLocalDate()));
        return result;
    }
}
