package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToGroupSession;
import co.tmunited.bluebook.domain.GroupSessionDetailsChart;

import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupSessionDetailsChart entity.
 */
@SuppressWarnings("unused")
public interface GroupSessionDetailsChartRepository extends JpaRepository<GroupSessionDetailsChart,Long> {

    List<GroupSessionDetailsChart> findAllByDelStatusIsFalse();

    @Query("SELECT groupSessionDetailsChart FROM GroupSessionDetailsChart groupSessionDetailsChart " +
        "JOIN groupSessionDetailsChart.groupSessionDetails groupSessionDetails " +
        "WHERE " +
        "groupSessionDetails.id = :gsId AND " +
        "groupSessionDetails.delStatus = false ")
    List<GroupSessionDetailsChart> findAllGroupSessionDetailsByPatientInCurrentFacility(@Param("gsId") Long gsId);

/*Long id, String notes, Long chartId, String firstName, String lastName, String bed, String mrNo, String phone*/
    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO(" +
        "gsdChart.id, " +
        "gsdChart.notes, " +
        "chart.id, " +
        "chart.patient.firstName, " +
        "chart.patient.lastName, " +
        "chart.bed.name, " +
        "chart.mrNo, " +
        "chart.phone " +
        ") FROM GroupSessionDetailsChart gsdChart, GroupSessionDetails  groupSessionDetails, Chart chart " +
        "JOIN gsdChart.groupSessionDetails groupSessionDetails " +
        "JOIN gsdChart.chart chart " +
        "left JOIN chart.bed bed " +
        "WHERE " +
        "gsdChart.delStatus=False AND " +
        "chart.delStatus = False  AND " +
        "groupSessionDetails.id = :id ")
    List<GroupSessionDetailsChartVO> findAllByDelStatusIsFalseAndGroupSessionDetailsId(@Param("id") Long id);

    List<GroupSessionDetailsChart> findAllGroupSessionDetailsChartByChartId(@Param("chartId") Long chartId);


    /*Long id, String notes, Long chartId, String name, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String employeeName*/
    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO(" +
        "gsdChart.id, " +
        "gsdChart.notes, " +
        "chart.id, " +
        "gSession.name, " +
        "groupSessionDetails.start, " +
        "groupSessionDetails.finalized, " +
        "CONCAT(emp.firstName, ' ', emp.lastName), " +
        "groupSessionDetails.topic, " +
        "ledSign.ip, " +
        "ledSign.createdDate, " +
        "revSign.ip, " +
        "reviewBy.createdDate " +
        ") FROM GroupSessionDetailsChart gsdChart, GroupSessionDetails groupSessionDetails, Chart chart " +
        "JOIN gsdChart.groupSessionDetails groupSessionDetails " +
        "LEFT JOIN groupSessionDetails.groupSession gSession " +
        "LEFT JOIN groupSessionDetails.leaderEmployee emp " +
        "LEFT JOIN groupSessionDetails.leaderSignature ledSign " +
        "LEFT JOIN groupSessionDetails.reviserSignature revSign " +
        "LEFT JOIN groupSessionDetails.reviewBy reviewBy " +
        "JOIN gsdChart.chart chart " +
        "WHERE " +
        "gsdChart.delStatus=False AND " +
        "chart.delStatus = False  AND " +
        "chart.id = :id ")
    List<GroupSessionDetailsChartVO> findAllByDelStatusIsFalseAndChartId(@Param("id") Long id);
}

