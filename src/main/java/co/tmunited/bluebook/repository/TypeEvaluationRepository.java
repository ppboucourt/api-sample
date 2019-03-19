package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeEvaluation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeEvaluation entity.
 */
@SuppressWarnings("unused")
public interface TypeEvaluationRepository extends JpaRepository<TypeEvaluation,Long> {

    List<TypeEvaluation> findAllByDelStatusIsFalse();
}

