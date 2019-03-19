package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.UrgentIssues;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UrgentIssues entity.
 */
@SuppressWarnings("unused")
public interface UrgentIssuesRepository extends JpaRepository<UrgentIssues,Long> {

    @Query("select distinct urgentIssues from UrgentIssues urgentIssues left join fetch urgentIssues.employees")
    List<UrgentIssues> findAllWithEagerRelationships();

    @Query("select urgentIssues from UrgentIssues urgentIssues left join fetch urgentIssues.employees where urgentIssues.id =:id")
    UrgentIssues findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct urgentIssues from UrgentIssues urgentIssues left join fetch urgentIssues.employees where urgentIssues.chart.id =:id")
    List<UrgentIssues> findAllByDelStatusIsFalseAndChartId(@Param("id") Long id);

    @Query("select distinct urgentIssues from UrgentIssues urgentIssues " +
        " left join fetch urgentIssues.employees employee " +
        " where urgentIssues.chart.id =:chartId and employee.id = :empId")
    List<UrgentIssues> findAllByDelStatusIsFalseAndChartIdAndEmployeeId(@Param("chartId") Long chartId, @Param("empId") Long empId);

}
