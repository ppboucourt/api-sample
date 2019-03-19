package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ConcurrentReviews;

import co.tmunited.bluebook.domain.vo.ChartVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the ConcurrentReviews entity.
 */
@SuppressWarnings("unused")
public interface ConcurrentReviewsRepository extends JpaRepository<ConcurrentReviews,Long> {

    List<ConcurrentReviews> findAllByDelStatusIsFalse();
    List<ConcurrentReviews> findAllByDelStatusIsFalseAndChartConcurrentReviewsId(Long id);

    @Query(value = "select creview from ConcurrentReviews creview Where (creview.chartConcurrentReviews.facility.id =:id) and (creview.delStatus = false)  and creview.nextReviewDate Between :startDate and :endDate" )
    List<ConcurrentReviews> findAllChartByFacilityIdAndAdmissionDateBetween(@Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);



    @Query("SELECT distinct NEW co.tmunited.bluebook.domain.vo.ChartVO(" +
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
        "LEFT JOIN  chart.concurrentReviews concurrentReviews " +
        "WHERE " +
        "facility.id = :facilityId AND " +
        "chart.delStatus = False  AND " +
        "concurrentReviews.delStatus = False  AND " +
        "concurrentReviews.nextReviewDate Between :startDate and :endDate ")
    List<ChartVO> findAllChartByFacilityIdAndAdmissionDateBetweenVO(@Param("facilityId") Long facilityId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
