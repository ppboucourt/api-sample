package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Vitals;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vitals entity.
 */
@SuppressWarnings("unused")
public interface VitalsRepository extends JpaRepository<Vitals,Long> {

    List<Vitals> findAllByDelStatusIsFalse();

    List<Vitals> findAllByDelStatusIsFalseAndChartId(Long id);
}

