package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Weight entity.
 */
@SuppressWarnings("unused")
public interface WeightRepository extends JpaRepository<Weight,Long> {

    List<Weight> findAllByDelStatusIsFalse();

    List<Weight> findAllByDelStatusIsFalseAndChartId(Long id);
}

