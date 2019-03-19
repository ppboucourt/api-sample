package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToIcd10;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChartToIcd10 entity.
 */
@SuppressWarnings("unused")
public interface ChartToIcd10Repository extends JpaRepository<ChartToIcd10,Long> {

    List<ChartToIcd10> findAllByDelStatusIsFalse();

    List<ChartToIcd10> findAllByChartIdAndDelStatusIsFalse(Long id);
//    List<ChartToIcd10> findAllByChartIdAndDelStatusIsFalseOrderByIsActive(Long id);

//    List<ChartToIcd10> findAllByChartIdAndDelStatusIsFalseAndIsActiveIsTrue(Long id);
}

