package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Patient;
import co.tmunited.bluebook.domain.vo.ChartVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA repository for the Chart entity.
 */
@SuppressWarnings("unused")
public interface ChartRepository extends JpaRepository<Chart, Long> {

    @Query("select distinct chart from Chart chart " +
        "left join fetch chart.shifts " +
        "left join fetch chart.icd10S " +
        "left join fetch chart.drugs " +
        "left join fetch chart.pictureRef ")
    List<Chart> findAllWithEagerRelationships();

    @Query("select chart from Chart chart " +
        "left join fetch chart.bed " +
        "left join fetch chart.shifts " +
        "left join fetch chart.icd10S " +
        "left join fetch chart.drugs " +
        // "left join fetch chart.groupSessionDetailsCharts " +
        //"left join fetch chart.chartToForms " +
        "left join fetch chart.typeLevelCare " +
        "left join fetch chart.pictureRef " +
        "where chart.id =:id")
    Chart findOneWithEagerRelationships(@Param("id") Long id);

    /**
     * Get all cahrt with del_status in false
     *
     * @return List of Charts
     */
    List<Chart> findAllByDelStatusIsFalse();

    /**
     * Get the chart for the current facility
     *
     * @param facilityId
     * @return List of contactsFacility
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityId(Long facilityId);

    /**
     * Get the chart filtered by facility and without bed
     *
     * @param facilityId
     * @param date       - Current Date
     * @return List of contactsFacility
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityIdAndBedIdIsNullAndWaitingRoomIsFalseAndDischargeDateGreaterThan(Long facilityId, ZonedDateTime date);

    /**
     * Get the chart filtered by facility in Current Patients
     *
     * @param facilityId
     * @return List of contactsFacility
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThan(Long facilityId, ZonedDateTime date);

    /**
     * Get the chart filtered by facility in and is waiting for a Room
     *
     * @param facilityId
     * @return List of chart are in waiting room
     * @value implicit  waiting_room = true
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsTrue(Long facilityId);

    /**
     * @param facilityId current facility
     * @param date       dischargeDate
     * @return List of chart are Archive
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateLessThan(Long facilityId, ZonedDateTime date);

    /**
     * Get the list of chart's by patient
     *
     * @param facilityId
     * @param patientId
     * @return List of chart
     */
    List<Chart> findAllByDelStatusIsFalseAndFacilityIdAndPatientIdOrderByIdDesc(Long facilityId, Long patientId);

    /**
     * Get the amount of chart that exist until the current year
     *
     * @param id    facilityId
     * @param start first day of the year(XXXX-01-01)
     * @param end   last day of the year(XXXX-12-31)
     * @return
     */
    Long countByFacilityIdAndCreatedDateBetween(Long id, ZonedDateTime start, ZonedDateTime end);

    /**
     * Get the list of current patient in the Corporation
     *
     * @param date
     * @return
     */
    List<Chart> findAllByDelStatusIsFalseAndWaitingRoomIsFalseAndDischargeDateGreaterThan(ZonedDateTime date);


    @Query("select chart from Chart chart Where (facility_id =:fId) and (waiting_room =false) and (del_status = false)  " +
        "and (now() BETWEEN  admission_date  and discharge_date) or " +
        "( (date_part('year', admission_date)=DATE_PART('year', now())) and " +
        "(date_part('month', admission_date)=DATE_PART('month', now()))" +
        ") or ( (date_part('year', discharge_date)=DATE_PART('year', now())) and" +
        "(date_part('month', discharge_date)=DATE_PART('month', now())) ))")
    List<Chart> findAllChartByFacilityId(@Param("fId") Long fId);

    @Query("select chart from Chart chart Where (facility_id =:fId) ")
    List<Chart> findAllAllChartByFacilityId(@Param("fId") Long fId);


    @Query(value = "select chart from Chart chart Where (chart.facility.id =:id) and (chart.delStatus = false)  and chart.dischargeDate Between :startDate and :endDate")
    List<Chart> findAllChartByFacilityIdAndDischargeDateBetween(@Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);


    @Query(value = "select chart from Chart chart Where (chart.facility.id =:id) and (chart.delStatus = false)  and chart.admissionDate Between :startDate and :endDate")
    List<Chart> findAllChartByFacilityIdAndAdmissionDateBetween(@Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);


    //@Query(value = "select chart from Chart chart Where (chart.facility.id =:id) and (chart.delStatus = false)  and chart.admissionDate Between :startDate and :endDate" )
    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "' ', " +
        "' ', " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType, " +
        "chart.admissionDate, " +
        "chart.dischargeDate " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        // "LEFT JOIN chart.bed bed " +
        // "LEFT JOIN chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN  chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.dischargeDate Between :startDate and :endDate")
    List<ChartVO> findAllChartByFacilityIdAndDischargeBetweenVO(@Param("facilityId") Long facilityId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);


    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "' ', " +
        "' ', " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType, " +
        "chart.admissionDate, " +
        "chart.dischargeDate " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        // "LEFT JOIN chart.bed bed " +
        // "LEFT JOIN chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN  chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.admissionDate Between :startDate and :endDate")
    List<ChartVO> findAllChartByFacilityIdAndAdmissionDateBetweenVO(@Param("facilityId") Long facilityId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);


    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "chart.bed.name, " +
        "chart.typePatientPrograms.name, " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        "LEFT JOIN chart.bed bed " +
        "LEFT JOIN chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN   chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = True " +
        "ORDER BY chart.patient.firstName ASC ")
        //Waiting room
    List<ChartVO> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsTrueVO(@Param("facilityId") Long facilityId);


    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "chart.bed.name, " +
        "chart.typePatientPrograms.name, " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        "LEFT JOIN  chart.bed bed " +
        "LEFT JOIN  chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN  chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN  chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date " +
        "ORDER BY chart.patient.firstName ASC ")
        //Current patient
    List<ChartVO> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThanVO(@Param("facilityId") Long facilityId, @Param("date") ZonedDateTime date);

    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "chart.bed.name, " +
        "chart.typePatientPrograms.name, " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        "LEFT JOIN  chart.bed bed " +
        "LEFT JOIN  chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN  chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN  chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate <= :date " +
        "ORDER BY chart.patient.firstName ASC ")
        //Archive charts
    List<ChartVO> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateLessThanVO(@Param("facilityId") Long facilityId, @Param("date") ZonedDateTime date);

    //    ZonedDateTime admissionDate,
//    ZonedDateTime dischargeDate
    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "chart.bed.name, " +
        "chart.typePatientPrograms.name, " +
        "chart.typePaymentMethods.name, " +
        "chart.facility.name, " +
        "chart.pictureRef.picture, " +
        "chart.pictureRef.pictureContentType, " +
        "chart.admissionDate, " +
        "chart.dischargeDate, " +
        "chart.waitingRoom " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        "LEFT JOIN  chart.bed bed " +
        "LEFT JOIN  chart.typePatientPrograms typePatientPrograms " +
        "LEFT JOIN  chart.typePaymentMethods typePaymentMethods " +
        "LEFT JOIN  chart.pictureRef picture " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date " +
        "ORDER BY chart.patient.firstName ASC "
    )
//chart.patient.firstName,
    Page<ChartVO> findAllCharts(@Param("facilityId") Long facilityId, @Param("date") ZonedDateTime date, Pageable page);


    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.mrNo, " +
        "chart.bed.name, " +
        "'', " +
        "'', " +
        "chart.facility.name, " +
        "'', " +
        "'', " +
        "chart.phone " +
        ") FROM Chart chart " +
        "JOIN chart.facility facility " +
        "LEFT JOIN  chart.bed bed " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date " +
        "ORDER BY chart.patient.firstName ASC ")
    List<ChartVO> findAllByDelStatusIsFalseAndFacilityIdAndWaitingRoomIsFalseAndDischargeDateGreaterThanVOForGroupSession(@Param("facilityId") Long facilityId, @Param("date") ZonedDateTime date);


    @Query("SELECT chart " +
        " FROM Chart chart " +
        "JOIN chart.chartToForms chartToForms " +
        "WHERE " +
        "(chartToForms.id = :id)  AND " +
        "chart.delStatus = false AND chartToForms.delStatus = false")
    Chart findByChartToForm(@Param("id") Long id);


    @Query("SELECT patient FROM Chart chart " +
        "JOIN chart.patient patient " +
        "WHERE chart.id =:id")
    Patient findPatientByChart(@Param("id") Long id);

    @Query("SELECT chart FROM Chart chart " +
        "JOIN chart.patient patient " +
        "JOIN chart.facility facility " +
        "LEFT JOIN chart.bed bed " +
        "LEFT JOIN fetch chart.pictureRef " +
        "WHERE facility.id =:id AND chart.delStatus = false AND " +
        "(bed.chartId is null) AND " +
        "chart.waitingRoom = false AND chart.dischargeDate >:date")
    List<Chart> findChartsWithoutBed(@Param("id") Long id, @Param("date") ZonedDateTime date);


    @Query("select chart from Chart chart " +
        "LEFT JOIN fetch chart.icd10S icd10S " +
        "JOIN fetch chart.patient patient " +
        "left join fetch chart.patientMedications patientMedications " +
        "left join fetch patientMedications.patientMedicationPress " +
//        "left join fetch patientMedications.shifts " +
        "where chart.id =:id")
    Chart findOneWithMedications(@Param("id") Long id);

    @Query("select chart from Chart chart " +
        "LEFT JOIN fetch chart.typePaymentMethods typePaymentMethods " +
        "join fetch chart.facility facility " +
        "where facility.id =:id AND " +
        "chart.delStatus = false AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date ")
    List<Chart> findAllWithPaymentTypes(@Param("id") Long id, @Param("date") ZonedDateTime date);

    @Query("select distinct chart from Chart chart " +
        "LEFT JOIN fetch chart.concurrentReviews concurrentReviews " +
        "join fetch chart.facility facility " +
        "where facility.id =:id AND " +
        "chart.delStatus = false AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date ")
    List<Chart> findAllWithConcurrentReviews(@Param("id") Long id, @Param("date") ZonedDateTime date);


    @Query("SELECT chart FROM Chart chart " +
        "LEFT JOIN FETCH chart.facility facility " +
        "JOIN FETCH chart.patient patient " +
        "LEFT JOIN FETCH chart.insurances insurances " +
        "LEFT JOIN FETCH chart.typeMaritalStatus typeMaritalStatus " +
        "WHERE chart.id =:id ")
    Chart findOneForSchedule(@Param("id") Long id);

}
