package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Allergies;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Allergies entity.
 */
@SuppressWarnings("unused")
public interface AllergiesRepository extends JpaRepository<Allergies,Long> {

    List<Allergies> findAllByDelStatusIsFalse();

    List<Allergies> findAllByChartIdAndStatusIsFalse(Long chartId);

    @Query("SELECT allergie FROM Allergies allergie " +
        "JOIN FETCH allergie.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "allergie.delStatus = false ")
    List<Allergies> findAllByChartId(@Param("id") Long id);
}

