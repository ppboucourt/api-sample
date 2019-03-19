package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToAction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChartToAction entity.
 */
@SuppressWarnings("unused")
public interface ChartToActionRepository extends JpaRepository<ChartToAction,Long> {

    List<ChartToAction> findAllByDelStatusIsFalse();
}

