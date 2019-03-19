package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Icd10;
import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.vo.PatientOrderItemVO;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.PatientOrderItemRepository;
import co.tmunited.bluebook.repository.search.PatientOrderItemSearchRepository;
import co.tmunited.bluebook.service.PatientOrderItemService;
import co.tmunited.bluebook.service.PatientOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientOrderItem.
 */
@Service
@Transactional
public class PatientOrderItemServiceImpl implements PatientOrderItemService {

    private final Logger log = LoggerFactory.getLogger(PatientOrderItemServiceImpl.class);

    @Inject
    private PatientOrderItemRepository patientOrderItemRepository;

    @Inject
    private PatientOrderItemSearchRepository patientOrderItemSearchRepository;

    @Inject
    private PatientOrderService patientOrderService;

    @Inject
    private ChartRepository chartRepository;

    /**
     * Save a patientOrderItem.
     *
     * @param patientOrderItem the entity to save
     * @return the persisted entity
     */
    public PatientOrderItem save(PatientOrderItem patientOrderItem) {
        log.debug("Request to save PatientOrderItem : {}", patientOrderItem);
        PatientOrderItem result = patientOrderItemRepository.save(patientOrderItem);
        patientOrderItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientOrderItems.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderItem> findAll() {
        log.debug("Request to get all PatientOrderItems");
        List<PatientOrderItem> result = patientOrderItemRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one patientOrderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientOrderItem findOne(Long id) {
        log.debug("Request to get PatientOrderItem : {}", id);
        PatientOrderItem patientOrderItem = patientOrderItemRepository.findOne(id);
        return patientOrderItem;
    }

    /**
     * Delete the  patientOrderItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientOrderItem : {}", id);
        PatientOrderItem patientOrderItem = patientOrderItemRepository.findOne(id);
        patientOrderItem.setDelStatus(true);
        PatientOrderItem result = patientOrderItemRepository.save(patientOrderItem);

        patientOrderItemSearchRepository.save(result);
    }

    /**
     * Search for the patientOrderItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderItem> search(String query) {
        log.debug("Request to search PatientOrderItems for query {}", query);
        return StreamSupport
            .stream(patientOrderItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Get all the patientOrderTests.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderItem> findAllOrderItems() {
        log.debug("Request to get all PatientOrderItems");
        List<PatientOrderItem> result = patientOrderItemRepository.findAllOrderItems().stream().map(x -> {
            x.getPatientOrderTest().getPatientOrder().getId();

            return x;

        }).collect(Collectors.toList());

        return result;
    }

    /**
     * Get all the patientOrderTests.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrderItemVO> findAllOrderItemsCollectedByChart(Long chartId) {
        log.debug("Request to get all findAllOrderItemsCollectedByChart");
        List<PatientOrderItemVO> result = patientOrderItemRepository.findAllOrderItemsCollectedByChart(chartId);
        Chart chart = chartRepository.findOneWithMedications(chartId);
        result.stream().forEach(item -> {
            item.setIcd10s(chart.getIcd10S());
            item.setPatient(chart.getPatient());
            item.setPatientMedications(chart.getPatientMedications());
        });
        return result;
    }

    @Transactional
    public PatientOrderItem markAsSent(Long id) {
        PatientOrderItem patientOrderItem = patientOrderItemRepository.findOne(id);
        patientOrderItem.sent(true);
        patientOrderItem = patientOrderItemRepository.save(patientOrderItem);

        patientOrderService.checkOrderStatus(patientOrderItem.getPatientOrderTest().getPatientOrder());

        return patientOrderItem;
    }

    @Transactional(readOnly = true)
    public List<PatientOrderItem> getPatientOrderItemsToProcess() {
        log.debug("Request to get Order Items to process");

        List<PatientOrderItem> result = patientOrderItemRepository.getPatientOrderItemsToProcess();
        return result;
    }
}
