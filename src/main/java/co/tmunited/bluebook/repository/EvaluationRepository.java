package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Evaluation;

import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {

    List<Evaluation> findAllByDelStatusIsFalse();

    /**
     * Get a list of Evaluation filtered by patientProcess and Facility
     *
     * @param id
     * @return
     */
    List<Evaluation> findAllByDelStatusIsFalseAndTypePatientProcess(Long id);

    /**
     * Find the Evaluations for a Patient(Chart) and by a Patient Process
     *
     * @param chId
     * @param ppId
     * @return
     */
    List<Evaluation> findAllByDelStatusIsFalseAndChartIdAndTypePatientProcessId(Long chId, Long ppId);

    List<Evaluation> findAllByDelStatusIsFalseAndChartId(Long chartId);

    List<Evaluation> findAllByDelStatusIsFalseAndChartFacilityIdAndStatusNot(Long fId, EvaluationStatus evaluationStatus);

}

