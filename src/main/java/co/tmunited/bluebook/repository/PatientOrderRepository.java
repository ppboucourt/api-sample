package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientOrder;
import co.tmunited.bluebook.domain.enumeration.OrderStatus;
import co.tmunited.bluebook.domain.vo.PatientScheduleDataVO;
import co.tmunited.bluebook.domain.vo.PrintBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientOrder entity.
 */
@SuppressWarnings("unused")
public interface PatientOrderRepository extends JpaRepository<PatientOrder,Long> {

    List<PatientOrder> findAllByDelStatusIsFalse();

    @Query("SELECT DISTINCT patientOrder FROM PatientOrder patientOrder " +
        "JOIN FETCH patientOrder.chart chart " +
        "JOIN FETCH patientOrder.patientOrderTests test " +
        "JOIN FETCH test.patientOrderItems item " +
        "JOIN FETCH patientOrder.signedBy signedBy " +
        "WHERE " +
        "patientOrder.chart.id = :id AND " + //de que paciente
        "patientOrder.ordStatus <> 'GENERATED' AND " +
        "patientOrder.delStatus = False "
    )
    List<PatientOrder> findAllByChartId(@Param("id") Long id);
    //List<PatientOrder> findAllByChartId(Long id);

    Long countByChartIdAndDelStatusIsFalseAndOrdStatus(Long id, OrderStatus orderStatus);

    @Query("SELECT DISTINCT patientOrder FROM PatientOrder patientOrder " +
//        "JOIN patientOrder.patientOrderTests test " +
//        "JOIN test.patientOrderItems item " +
        "WHERE " +
        "patientOrder.chart.id = :id AND " +
        "patientOrder.ordStatus <> 'SCHEDULED' AND " +
        "patientOrder.ordStatus <> 'GENERATED'"
    )
    List<PatientOrder> findOneTimeOldByPatient(@Param("id") Long id);

    @Query("SELECT DISTINCT patientOrder FROM PatientOrder patientOrder " +
        "JOIN patientOrder.patientOrderTests test " +
        "JOIN test.patientOrderItems item " +
        "WHERE " +
        "patientOrder.chart.facility.id = :id AND " +
        "patientOrder.ordStatus <> 'CANCELED' AND " +
        "item.scheduleDate = :day"
//         + "AND patientOrder.signed = true"
    )
    List<PatientOrder> findAllTodayOrders(@Param("id") Long id, @Param("day") LocalDate day);


    @Query(value =
        "SELECT NEW co.tmunited.bluebook.domain.vo.PatientScheduleDataVO(" +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.groupNumberId," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName," +
            "patientOrder.id" +
            ") " +
            "FROM PatientOrder patientOrder " +
            "JOIN patientOrder.patientOrderTests test " +
            "JOIN test.patientOrderItems item " +
            "JOIN patientOrder.signedBy physician " +
            "WHERE " +
            "patientOrder.chart.facility.id = :id AND " +
            "(patientOrder.ordStatus <> 'SCHEDULED' AND item.collected = TRUE) AND " +
            "item.scheduleDate = :day " +
            "GROUP BY " +
            "patientOrder.id," +
            "item.groupNumberId," +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.patient.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName"
    )
    List<PatientScheduleDataVO> findAllPatientScheduleDataByFacilityCollectedVO(@Param("id") Long id, @Param("day") LocalDate day);

    @Query(value =
        "SELECT NEW co.tmunited.bluebook.domain.vo.PatientScheduleDataVO(" +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.groupNumberId," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName," +
            "patientOrder.id" +
            ") " +
            "FROM PatientOrder patientOrder " +
            "JOIN patientOrder.patientOrderTests test " +
            "JOIN test.patientOrderItems item " +
            "JOIN patientOrder.signedBy physician " +
            "WHERE " +
            "patientOrder.chart.facility.id = :id AND " +
            "(patientOrder.ordStatus = 'SCHEDULED' ) AND " +
            "item.scheduleDate = :day " +
            "GROUP BY " +
            "patientOrder.id," +
            "item.groupNumberId," +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.patient.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName"
    )
    List<PatientScheduleDataVO> findAllPatientScheduleDataByFacilityScheduledVO(@Param("id") Long id, @Param("day") LocalDate day);


    @Query(value =
        "SELECT NEW co.tmunited.bluebook.domain.vo.PatientScheduleDataVO(" +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.groupNumberId," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName," +
            "patientOrder.id" +
            ") " +
            "FROM PatientOrder patientOrder " +
            "JOIN patientOrder.patientOrderTests test " +
            "JOIN test.patientOrderItems item " +
            "JOIN patientOrder.signedBy physician " +
            "WHERE " +
            "patientOrder.chart.facility.id = :id AND " +
            "((patientOrder.ordStatus <> 'CANCELED' AND patientOrder.ordStatus <> 'GENERATED') OR (patientOrder.ordStatus = 'CANCELED' AND item.collected = TRUE)) AND " +
            "item.scheduleDate = :day " +
            "GROUP BY " +
            "patientOrder.id," +
            "item.groupNumberId," +
            "patientOrder.chart.patient.firstName," +
            "patientOrder.chart.patient.lastName," +
            "patientOrder.chart.patient.middleName," +
            "patientOrder.chart.patient.id," +
            "patientOrder.chart.patient.dateBirth," +
            "item.scheduleDate," +
            "item.collected," +
            "physician.firstName," +
            "physician.lastName"
    )
    List<PatientScheduleDataVO> findAllPatientScheduleDataByFacilityVO(@Param("id") Long id, @Param("day") LocalDate day);


    @Query("SELECT COUNT(DISTINCT patientOrder) FROM PatientOrder patientOrder " +
        "JOIN patientOrder.patientOrderTests test " +
        "JOIN test.patientOrderItems item " +
        "WHERE " +
        "patientOrder.chart.facility.id = :id AND " +
        "patientOrder.ordStatus <> 'CANCELED' AND " +
        "item.scheduleDate = :day AND " +
//        "patientOrder.signed = true AND " +
        "item.collected = false"
    )
    Long countOrdersNotCollectedByDate(@Param("id") Long id, @Param("day") LocalDate day);

    @Query("SELECT DISTINCT patientOrder FROM PatientOrder patientOrder " +
        "JOIN patientOrder.patientOrderTests test " +
        "JOIN test.patientOrderItems item " +
        "WHERE " +
        "item.groupNumberId = :barcode"
    )
    PatientOrder findOrderByBarcode(@Param("barcode") String barcode);

    /**
     * Dashboard all unsigned orders
     *
     * @param id
     * @return
     */
    @Query("SELECT COUNT(patientOrder) FROM PatientOrder patientOrder " +
        "JOIN patientOrder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
//        "patientOrder.ordStatus = 'SCHEDULED' AND " +
        "(patientOrder.signed = false OR patientOrder.signed IS NULL)"
    )
    Long countByUnsignedOrders(@Param("id") Long id);

    /**
     * Dashboard unsigned orders by physician
     *
     * @param id
     * @param physicianId
     * @return
     */
    @Query("SELECT COUNT(patientOrder) FROM PatientOrder patientOrder " +
//        "JOIN patientOrder.patientOrderTests test " +
//        "JOIN test.patientOrderItems item " +
        "JOIN patientOrder.chart chart " +
        "JOIN chart.facility facility " +
        "JOIN patientOrder.signedBy physician " +
        "WHERE " +
        "facility.id = :id AND " +
        "physician.id = :physician AND " +
//        "patientOrder.ordStatus = 'SCHEDULED' AND " +
        "(patientOrder.signed = false OR patientOrder.signed IS NULL)"
    )
    Long countByUnsignedOrdersAndPhysician(@Param("id") Long id, @Param("physician") Long physicianId);

    @Query("SELECT patientOrder FROM PatientOrder patientOrder " +
//        "JOIN patientOrder.patientOrderTests test " +
//        "JOIN test.patientOrderItems item " +
        "JOIN patientOrder.chart chart " +
        "JOIN chart.facility facility " +
        "JOIN patientOrder.signedBy physician " +
        "WHERE " +
        "facility.id = :id AND " +
        "physician.id = :physician AND " +
//        "patientOrder.ordStatus = 'SCHEDULED' AND " +
        "(patientOrder.signed = false OR patientOrder.signed IS NULL)"
    )
    List<PatientOrder> findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(@Param("id") Long id, @Param("physician") Long physicianId);

    @Query("SELECT patientOrder FROM PatientOrder patientOrder " +
//        "JOIN patientOrder.patientOrderTests test " +
//        "JOIN test.patientOrderItems item " +
        "JOIN patientOrder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientOrder.ordStatus <> 'GENERATED' AND " +
        "(patientOrder.signed = false OR patientOrder.signed IS NULL)"
    )
    List<PatientOrder> findAllBySignedIsFalseAndDelStatusIsFalse(@Param("id") Long id);

//long, java.time.LocalDate, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.time.ZonedDateTime, java.lang.String, java.lang.String
//Long orderId, LocalDate date, String tube, String lastName, String firstName, String middleInitial, LocalDate dob, String barcode, String account

    @Query(value = "SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.PrintBarcode" +
        "(patientOrder.id, items.scheduleDate, tubes.name, chart.patient.lastName, chart.patient.firstName, chart.patient.middleName, chart.patient.dateBirth, items.groupNumberId, facility.account) " +
        "FROM PatientOrder patientOrder " +
        "JOIN patientOrder.patientOrderTests tests " +
        "JOIN patientOrder.chart chart " +
        "JOIN tests.patientOrderItems items " +
        "JOIN tests.labCompendium compendium " +
        "JOIN compendium.tubes tubes " +
        "JOIN chart.facility facility " +
        "WHERE patientOrder.id IN :ids AND items.scheduleDate = :day AND items.collected = TRUE " +
        "ORDER BY patientOrder.id, tubes.name"
    )
    List<PrintBarcode> printBarcodesWithTubes(@Param("ids") List<Long> ids, @Param("day") LocalDate day);


    @Query(value = "SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.PrintBarcode" +
        "(patientOrder.id, items.scheduleDate, '', chart.patient.lastName, chart.patient.firstName, chart.patient.middleName, chart.patient.dateBirth, items.groupNumberId, facility.account) " +
        "FROM PatientOrder patientOrder " +
        "JOIN patientOrder.patientOrderTests tests " +
        "JOIN patientOrder.chart chart " +
        "JOIN tests.patientOrderItems items " +
        //"JOIN tests.labCompendium compendium " +
       // "JOIN compendium.tubes tubes " +
        "JOIN chart.facility facility " +
        "WHERE patientOrder.id IN :ids AND items.scheduleDate = :day AND items.collected = TRUE " +
        "ORDER BY patientOrder.id"
    )
    List<PrintBarcode> printBarcodesWithoutTubes(@Param("ids") List<Long> ids, @Param("day") LocalDate day);


    @Query("SELECT DISTINCT patientOrderItem.groupNumberId " +
        "FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest patientOrderTest " +
        "JOIN patientOrderTest.patientOrder porder " +
        "WHERE porder.id = :id AND " +
        "patientOrderItem.scheduleDate = :date"
    )
    String getBarcode(@Param("id") Long orderId, @Param("date") LocalDate date);

}
