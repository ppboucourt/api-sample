package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToLevelCare;

import co.tmunited.bluebook.domain.vo.ChartToLevelCareVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the ChartToLevelCare entity.
 */
@SuppressWarnings("unused")
public interface ChartToLevelCareRepository extends JpaRepository<ChartToLevelCare, Long> {

    List<ChartToLevelCare> findAllByDelStatusIsFalse();

    ChartToLevelCare findByChartIdAndStartDateIsNull(Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartToLevelCareVO(" +
        "chartToLevelCare.id, " +
        "chartToLevelCare.chart.id, " +
        "chartToLevelCare.typeLevelCare.name, " +
        "chartToLevelCare.daysLoc " +
        ") FROM ChartToLevelCare chartToLevelCare " +
        "LEFT JOIN chartToLevelCare.chart chart " +
        "LEFT JOIN chartToLevelCare.typeLevelCare typeLevelCare " +
        "WHERE " +
        "chart.id = :id AND " +
        "chartToLevelCare.delStatus = False ")
    List<ChartToLevelCareVO> findLevelCaresByChart(@Param("id") Long id);

    @Query("SELECT chartToLevelCare FROM ChartToLevelCare chartToLevelCare " +
        "LEFT JOIN chartToLevelCare.typeLevelCare typeLevelCare " +
        "WHERE " +
        "chartToLevelCare.chartId = :id AND " +
        "chartToLevelCare.delStatus = False AND " +
        "chartToLevelCare.createdDate = (SELECT MAX(createdDate) FROM ChartToLevelCare WHERE chartId = :id) ")
    ChartToLevelCare findLastLevelCaresByChart(@Param("id") Long id);


    @Query("SELECT chartToLevelCare FROM ChartToLevelCare chartToLevelCare " +
        "LEFT JOIN FETCH chartToLevelCare.typeLevelCare typeLevelCare " +
        "LEFT JOIN FETCH chartToLevelCare.chart chart " +
        "LEFT JOIN FETCH chart.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "chartToLevelCare.delStatus = False AND " +
        "typeLevelCare.delStatus = False AND " +
//        "typeLevelCare.status = 'ACT' AND " +
        "chartToLevelCare.endDate IS NULL AND " +
        "chart.delStatus = False  AND " +
        "chart.waitingRoom = False AND " +
        "chart.dischargeDate >= :date "
    )
    List<ChartToLevelCare> findAllLevelCaresByFacility(@Param("id") Long id, @Param("date") ZonedDateTime date);

}

