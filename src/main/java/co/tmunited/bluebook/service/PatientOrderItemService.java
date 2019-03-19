package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.vo.PatientOrderItemVO;

import java.util.List;

/**
 * Service Interface for managing PatientOrderItem.
 */
public interface PatientOrderItemService {

    /**
     * Save a patientOrderItem.
     *
     * @param patientOrderItem the entity to save
     * @return the persisted entity
     */
    PatientOrderItem save(PatientOrderItem patientOrderItem);

    /**
     *  Get all the patientOrderItems.
     *
     *  @return the list of entities
     */
    List<PatientOrderItem> findAll();

    /**
     *  Get the "id" patientOrderItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientOrderItem findOne(Long id);

    /**
     *  Delete the "id" patientOrderItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientOrderItem corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientOrderItem> search(String query);

    /**
     *  Get all the patientOrderItems.
     *
     *  @return the list of entities
     */
    List<PatientOrderItem> findAllOrderItems();

    /**
     *  Get all the patientOrderItems.
     *
     *  @return the list of entities
     */
    List<PatientOrderItemVO> findAllOrderItemsCollectedByChart(Long chartId);

    List<PatientOrderItem> getPatientOrderItemsToProcess();

    PatientOrderItem markAsSent(Long id);
}
