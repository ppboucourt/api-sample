package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.EvaluationContent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EvaluationContent entity.
 */
@SuppressWarnings("unused")
public interface EvaluationContentRepository extends JpaRepository<EvaluationContent,Long> {

    List<EvaluationContent> findAllByDelStatusIsFalse();
}

