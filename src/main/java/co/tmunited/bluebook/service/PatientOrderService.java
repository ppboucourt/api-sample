package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.PatientOrder;
import co.tmunited.bluebook.domain.PatientOrderTest;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientScheduleDataVO;
import co.tmunited.bluebook.domain.vo.PrintBarcode;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing PatientOrder.
 */
public interface PatientOrderService {

    /**
     * Save a patientOrder.
     *
     * @param patientOrder the entity to save
     * @return the persisted entity
     */
    PatientOrder save(PatientOrder patientOrder);

    /**
     *  Get all the patientOrders.
     *
     *  @return the list of entities
     */
    List<PatientOrder> findAll();

    /**
     *  Get the "id" patientOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientOrder findOne(Long id);

    /**
     *  Delete the "id" patientOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientOrder corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientOrder> search(String query);

    /**
     *  Get all the one time orders .
     *
     *  @param id the patient id
     *
     *  @return the list of entities
     */
    List<PatientOrder> findAllByChart(Long id);

    PatientOrder cancelPatientOrder(Long id);

    PatientOrder finishPatientOrder(Long id);

    /**
     * Get today order for a clinic
     *
     * @param id clinic id
     *
     * @return today orders
     */
    List<PatientOrder> findAllTodayOrders(Long id);

    List<PatientOrder> findAllOrdersSchedulesByDate(Long id, LocalDate date);

    void collect(CollectedBody collectedBody);

    PatientOrder schedulePatientOrder(PatientOrder patientOrder);

    void checkOrderStatus(PatientOrder order);

    Long countOrdersNotCollectedByDate(Long id, LocalDate date);

    /**
     * Find Order by barcode
     * @param barcode barcode nummber
     * @return PatientOrder
     */
    PatientOrder findOrderByBarcode(String barcode);

    /**
     * Get unsigned order from user with role PHYSICIAN
     *
     * @return
     */
    List<PatientOrder> getUnsignedOrders(Long id);

    List<PatientOrder> getAllUnsignedOrders(Long id);

    /**
     * Sign physician orders
     *
     * @param collectedBody
     */
    void signOrders(CollectedBody collectedBody);


    /**
     *  Get the "id" patientOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientOrder findOneWithAll(Long id);

    List<PatientScheduleDataVO> findAllPatientScheduleDataByFacility(Long id, LocalDate date);

    void changeDrawDay(CollectedBody collectedBody);

    Employee getSignedBy(Long id);

    Set<PatientOrderTest> getPatientOrderTests(Long id);

    List<PrintBarcode> getBarcodes(CollectedBody collectedBody);

    CollectedBody getBarcode(Long id, LocalDate date);
}
