package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartCareTeam;
import co.tmunited.bluebook.domain.vo.ChartCareTeamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ChartCareTeam entity.
 */
@SuppressWarnings("unused")
public interface ChartCareTeamRepository extends JpaRepository<ChartCareTeam,Long> {

    List<ChartCareTeam> findAllByDelStatusIsFalse();

    List<ChartCareTeam> findByChartIdAndDelStatusIsFalse(Long id);

    List<ChartCareTeam> findAllByEmployeeId(Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.ChartCareTeamVO(" +
        "chartCareTeam.id, " +
        "chart.id, " +
        "chartCareTeam.typeSpeciality, " +
        "employee " +
        ") FROM ChartCareTeam chartCareTeam " +
        "LEFT JOIN chartCareTeam.employee employee " +
        "LEFT JOIN chartCareTeam.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "chartCareTeam.delStatus = false")
    List<ChartCareTeamVO> findAllVOByChartId(@Param("id") Long id);
}

