package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.EvaluationTemplate;
import co.tmunited.bluebook.domain.vo.FormVO;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the EvaluationTemplate entity.
 */
@SuppressWarnings("unused")
public interface EvaluationTemplateRepository extends JpaRepository<EvaluationTemplate,Long> {

    @Query("select distinct evaluationTemplate " +
        "from EvaluationTemplate evaluationTemplate " +
        "left join fetch evaluationTemplate.typeLevelCares " )
    List<EvaluationTemplate> findAllWithEagerRelationships();

    @Query("select evaluationTemplate " +
        "from EvaluationTemplate evaluationTemplate " +
        "left join fetch evaluationTemplate.typeLevelCares " +
        "where evaluationTemplate.id =:id " )
    EvaluationTemplate findOneWithEagerRelationships(@Param("id") Long id);

    List<EvaluationTemplate> findAllByDelStatusIsFalse();

    List<EvaluationTemplate> findAllByDelStatusIsFalseAndEnabledIsTrueAndTypePatientProcessIdAndFacilityId(Long ppId, Long facId);

    List<EvaluationTemplate> findAllByDelStatusIsFalseAndFacilityId(Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.FormVO(" +
        "evaluation.id, " +
        "evaluation.name, " +
        "evaluation.enabled, " +
        "evaluation.onlyOne, " +
        "evaluation.typePatientProcess.name " +
        ") FROM EvaluationTemplate evaluation " +
        "LEFT JOIN evaluation.typePatientProcess patientProcess " +
        "LEFT JOIN evaluation.typeLevelCares levelCare " +
        "LEFT JOIN evaluation.facility facility " +
        "WHERE " +
        "patientProcess.id = :ppId AND " +
        "facility.id = :facId AND " +
        "levelCare.id = :lcId AND " +
        "evaluation.delStatus = false " )
    List<FormVO> findAllByPatientProcessFacilityAndLevelCare(@Param("ppId") Long ppId, @Param("facId") Long facId, @Param("lcId") Long lcId);

    /**
     * Get the evaluationsTemplate by facility
     * @param id belonging to a facility
     *  @return List of evaluationsTemplate filtered by one facility
     *
     */
    @Query("SELECT evaluation FROM EvaluationTemplate evaluation " +
        "LEFT JOIN FETCH evaluation.typeLevelCares levelCare " +
        "JOIN FETCH evaluation.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "evaluation.delStatus = false ")
    List<EvaluationTemplate> findAllByFacilityWithLevelCare(@Param("id")Long id);
}

