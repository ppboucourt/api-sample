package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientOrderItem;
import co.tmunited.bluebook.domain.vo.PatientOrderItemVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientOrderItem entity.
 */
@SuppressWarnings("unused")
public interface PatientOrderItemRepository extends JpaRepository<PatientOrderItem, Long> {

    List<PatientOrderItem> findAllByDelStatusIsFalse();

    /**
     * SELECT
     * "public".patient_order_item."id",
     * "public".patient_order_item.icd_10_codes
     * FROM
     * "public".patient_order_item
     * INNER JOIN "public".patient_order_test ON "public".patient_order_item.patient_order_test_id = "public".patient_order_test."id"
     * INNER JOIN "public".patient_order ON "public".patient_order_test.patient_order_id = "public".patient_order."id"
     * WHERE
     * "public".patient_order.order_status_id = 4 AND
     * "public".patient_order_item.collected AND
     * "public".patient_order_item.sent = false
     *
     * @return
     */
    @Query("SELECT DISTINCT patientOrderItem FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest test " +
        "JOIN test.patientOrder orderTest " +
        "WHERE " +
        "orderTest.ordStatus = 'SCHEDULED' AND " +
        "patientOrderItem.collected = true AND " +
        "patientOrderItem.sent = false"
    )
    List<PatientOrderItem> findAllOrderItems();

//    @Query("SELECT DISTINCT patientOrderItem FROM PatientOrderItem patientOrderItem " +
//        "JOIN patientOrderItem.patientOrderTest test " +
//        "JOIN test.patientOrder patientOrder " +
//        "JOIN patientOrder.chart chart " +
//        "JOIN patientOrder.signedBy signedBy " +
//        "WHERE " +
//        "chart.id = :id AND " +
//        "patientOrder.ordStatus = 'SCHEDULED' AND " +
//        "patientOrderItem.collected = true"
//    )

    @Query("SELECT DISTINCT new co.tmunited.bluebook.domain.vo.PatientOrderItemVO(" +
        "patientOrderItem.id, " +
        "patientOrderItem.groupNumberId, " +
        "patientOrderItem.sent, " +
        "chart.id, " +
        "test, " +
        "labCompendium.code, " +
        "CONCAT(signedBy.firstName, ' ', signedBy.lastName), " +
        "signedBy.npiNumber, " +
        "patientOrderItem.collectedDate " +
        ") " +
        "FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest test " +
        "JOIN test.labCompendium labCompendium " +
        "JOIN test.patientOrder patientOrder " +
        "JOIN patientOrder.chart chart " +
        "JOIN patientOrder.signedBy signedBy " +
        "WHERE " +
        "chart.id = :id AND " +
        "patientOrder.ordStatus = 'SCHEDULED' AND " +
        "patientOrderItem.collected = true"
    )
    List<PatientOrderItemVO> findAllOrderItemsCollectedByChart(@Param("id") Long id);

//    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
//        "chart.id, " +
//        "chart.patient.firstName, " +
//        "chart.patient.lastName, " +
//        "chart.mrNo, " +
//        "chart.bed.name, " +
//        "chart.typePatientPrograms.name, " +
//        "chart.typePaymentMethods.name, " +
//        "chart.facility.name, " +
//        "chart.pictureRef.picture, " +
//        "chart.pictureRef.pictureContentType " +
//        ") FROM Chart chart " +
//        "JOIN chart.facility facility " +
//        "LEFT JOIN  chart.bed bed " +
//        "LEFT JOIN  chart.typePatientPrograms typePatientPrograms " +
//        "LEFT JOIN  chart.typePaymentMethods typePaymentMethods " +
//        "LEFT JOIN  chart.pictureRef picture " +
//        "WHERE " +
//        "facility.id = :facilityId AND " +
//        "chart.delStatus = False  AND " +
//        "chart.waitingRoom = False AND " +
//        "chart.dischargeDate >= :date")

    @Query("SELECT DISTINCT patientOrderItem " +
        "FROM PatientOrderItem patientOrderItem " +
        "JOIN FETCH patientOrderItem.patientOrderTest patientOrderTest " +
        "JOIN FETCH patientOrderTest.patientOrder patientOrder " +
        "JOIN FETCH patientOrder.chart chart " +
        "JOIN FETCH chart.facility facility " +
        "JOIN FETCH facility.corporation corporation " +
        "WHERE patientOrder.ordStatus = 'SCHEDULED' AND " + //tipo on time
        "patientOrderItem.collected = true AND " +
        "patientOrderItem.sent = false AND " +
        "corporation.delStatus = false AND " +
        "facility.delStatus = false AND " +
        "chart.delStatus = false " +
        "ORDER BY patientOrderItem.groupNumberId"
    )
    List<PatientOrderItem> getPatientOrderItemsToProcess();

    /**
     * Dashboard printed barcode
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(patientOrderItem) FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest test " +
        "JOIN test.patientOrder porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientOrderItem.scheduleDate between :startDate AND :endDate AND " +
        "patientOrderItem.collected = true"
    )
    Long countPrintedBarcode(
        @Param("id") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Dashboard unprinted barcode
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(patientOrderItem) FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest test " +
        "JOIN test.patientOrder porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientOrderItem.scheduleDate between :startDate AND :endDate AND " +
        "patientOrderItem.collected = false"
    )
    Long countUnprintedBarcode(
        @Param("id") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

