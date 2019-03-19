package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientResultDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the PatientResultDet entity.
 */
@SuppressWarnings("unused")
public interface PatientResultDetRepository extends JpaRepository<PatientResultDet, Long> {

    List<PatientResultDet> findAllByDelStatusIsFalse();

    List<PatientResultDet> findAllByDelStatusIsFalseAndPatientResultId(Long id);

    /**
     * Dashboard critical result
     *
     * @param id        clinic
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(DISTINCT patientResultDet) FROM PatientResultDet patientResultDet " +
        "JOIN patientResultDet.patientResult patientResult " +
        "JOIN patientResult.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResultDet.createdDate between :startDate AND :endDate AND " +
        "(patientResultDet.status='LL' OR patientResultDet.status='HH')"
    )
    Long countCriticalResultDetByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    /**
     * Dashboard not perform test
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT COUNT(patientResultDet) FROM PatientResultDet patientResultDet " +
        "JOIN patientResultDet.patientResult result " +
        "JOIN result.order porder " +
        "JOIN porder.chart chart " +
        "JOIN chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "patientResultDet.createdDate between :startDate AND :endDate AND " +
        "patientResultDet.result = 'CANCELED'"
    )
    Long countNoPerformTestByClinic(
        @Param("id") Long id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("SELECT patientResultDet FROM PatientResultDet patientResultDet " +
        "JOIN patientResultDet.patientResult patientResult " +
        "WHERE " +
        "patientResultDet.delStatus = false AND " +
        "patientResult.id IN (:ids) AND " +
        "patientResult.delStatus = false ")
    Set<PatientResultDet> findAllByResultIds(@Param("ids") List<Long> ids);
}
