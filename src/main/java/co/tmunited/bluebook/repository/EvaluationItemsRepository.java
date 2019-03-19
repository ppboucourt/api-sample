package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.EvaluationItems;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EvaluationItems entity.
 */
@SuppressWarnings("unused")
public interface EvaluationItemsRepository extends JpaRepository<EvaluationItems,Long> {

    List<EvaluationItems> findAllByDelStatusIsFalse();
}

