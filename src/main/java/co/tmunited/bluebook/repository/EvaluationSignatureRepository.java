package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.EvaluationSignature;
import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EvaluationSignature entity.
 */
@SuppressWarnings("unused")
public interface EvaluationSignatureRepository extends JpaRepository<EvaluationSignature,Long> {

    List<EvaluationSignature> findAllByDelStatusIsFalse();

    List<EvaluationSignature> findAllByDelStatusIsFalseAndEvaluationId(Long id);

    @Query("SELECT DISTINCT NEW co.tmunited.bluebook.domain.vo.EvaluationSignatureVO(" +
        "evaluationSignature.id, " +
        "evaluationSignature.signature, " +
        "evaluationSignature.patientSignature " +
        ") FROM EvaluationSignature evaluationSignature " +
        "JOIN evaluationSignature.evaluation evaluation " +
        "WHERE " +
        "evaluationSignature.delStatus = False AND " +
        "evaluation.delStatus = FALSE AND " +
        "evaluationSignature.id = :id" )
    EvaluationSignatureVO findByEvaluation(@Param("id") Long id);
}

