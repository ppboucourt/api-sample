package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientResult;
import co.tmunited.bluebook.domain.vo.PatientResultVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientResult entity.
 */
@SuppressWarnings("unused")
public interface PatientResultRepository extends JpaRepository<PatientResult, Long> {

    List<PatientResult> findAllByDelStatusIsFalse();

    @Query("SELECT patientresult FROM PatientResult patientresult " +
        "JOIN patientresult.order patientorder " +
        "JOIN patientorder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE facility.id =:id")
    List<PatientResult> findAllByClinic(@Param("id") Long id);

    @Query("SELECT patientresult FROM PatientResult patientresult " +
        "JOIN patientresult.order patientorder " +
        "JOIN patientorder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE facility.id =:id AND patientresult.createdDate between :starDate AND :endDate")
    List<PatientResult> findAllByClinic(@Param("id") Long id, @Param("starDate") ZonedDateTime starDate, @Param("endDate") ZonedDateTime endDate);

    //    @Query("SELECT DISTINCT patientresult FROM PatientResult patientresult " +
//        "JOIN FETCH patientresult.order patientorder " +
//        "LEFT JOIN FETCH patientresult.resultDets resultDets " +
//        "JOIN FETCH patientorder.chart chart " +
//        "WHERE chart.id =:id")
// Set<PatientResultDet> patientResultDets, String status, boolean abnormal
    @Query("SELECT DISTINCT new co.tmunited.bluebook.domain.vo.PatientResultVO(" +
        "patientresult.id, " +
        "patientresult.accessionNumber, " +
        "patientresult.collectionDate, " +
        "patientresult.patientName, " +
//        "resultDets, " +
        "patientresult.status, " +
        "patientresult.abnormal " +
        ") FROM PatientResult patientresult " +
        "JOIN patientresult.order patientorder " +
//        "LEFT JOIN patientresult.resultDets resultDets " +
        "JOIN patientorder.chart chart " +
        "WHERE chart.id =:id ")
    List<PatientResultVO> findAllByPatientId(@Param("id") Long id);

    @Query("SELECT patientresult FROM PatientResult patientresult " +
        "JOIN patientresult.order patientorder " +
        "JOIN patientorder.chart chart " +
        "WHERE chart.id =:id AND patientresult.createdDate between :starDate AND :endDate")
    List<PatientResult> findAllByPatientIdAndCreateDateBetween(@Param("id") Long id, @Param("starDate") ZonedDateTime starDate, @Param("endDate") ZonedDateTime endDate);

    @Query("SELECT count(*) FROM PatientResult patientresult " +
        "JOIN patientresult.order patientorder " +
        "JOIN patientorder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE facility.id =:id AND patientresult.createdDate between :starDate AND :endDate AND patientresult.abnormal = :abnormal")
    Long countAllResultByClinic(@Param("id") Long id, @Param("starDate") ZonedDateTime starDate, @Param("endDate") ZonedDateTime endDate, @Param("abnormal") boolean abnormal);

    PatientResult findOneByAccessionNumber(@Param("barcode") String barcode);

    /**
     * Dashboard count unassigned result
     *
     * @param account
     * @return
     */
    @Query("SELECT COUNT(patientResult) FROM PatientResult patientResult " +
        "WHERE " +
        "patientResult.account = :account AND " +
        "patientResult.order IS NULL")
    Long countUnassignedResultByClinic(@Param("account") String account);


    /**
     * Dashboard get unassigned order
     *
     * @param account
     * @return
     */
    List<PatientResult> findAllByAccountAndOrderIdIsNullAndDelStatusIsFalse(String account);

    /**
     * Dashboard count result by Clinic
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(patientResult) FROM PatientResult patientResult " +
        "JOIN patientResult.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResult.createdDate between :startDate AND :endDate AND " +
        "patientResult.status = :status"
    )
    Long countResultByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("status") String status);
//        @Param("id") Long id, @Param("status") String status);

    /**
     * Dashboard count not reviewed result
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(DISTINCT porder) FROM PatientOrderItem patientOrderItem " +
        "JOIN patientOrderItem.patientOrderTest test " +
        "JOIN test.patientOrder porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientOrderItem.collectedDate between :startDate AND :endDate " +
        "AND patientOrderItem.groupNumberId NOT IN (" +
        "SELECT patientResult.accessionNumber FROM PatientResult patientResult " +
        "WHERE patientResult.accessionNumber IS NOT NULL)"
    )
    Long notReviewResult(@Param("id") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Dashboard expand result by status
     *
     * @param id
     * @param startDate
     * @param endDate
     * @param status
     * @return
     */
    @Query("SELECT patientResult FROM PatientResult patientResult " +
        "JOIN patientResult.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResult.createdDate between :startDate AND :endDate AND " +
        "patientResult.status = :status " +
        "ORDER BY patientResult.createdDate"
    )
    List<PatientResult> getMonthlyResultByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("status") String status);

    /**
     * Dashboard critical result
     *
     * @param id        facility
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT DISTINCT patientResult FROM PatientResultDet patientResultDet " +
        "JOIN patientResultDet.patientResult patientResult " +
        "JOIN patientResult.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResultDet.createdDate between :startDate AND :endDate AND " +
        "(patientResultDet.status='HH' OR patientResultDet.status='LL')"
    )
    List<PatientResult> getCriticalResultByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    /**
     * Dashboard not perform test
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT DISTINCT result FROM PatientResultDet patientResultDet " +
        "JOIN patientResultDet.patientResult result " +
        "JOIN result.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResultDet.createdDate between :startDate AND :endDate AND " +
        "patientResultDet.result = 'CANCELED'"
    )
    List<PatientResult> getNonPerformTestByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
