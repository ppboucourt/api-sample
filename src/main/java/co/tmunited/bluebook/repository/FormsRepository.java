package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Forms;

import co.tmunited.bluebook.domain.vo.FormVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Forms entity.
 */
@SuppressWarnings("unused")
public interface FormsRepository extends JpaRepository<Forms,Long> {

    @Query("select distinct forms " +
        "from Forms forms " +
        "left join fetch forms.typeLevelCares ")
    List<Forms> findAllWithEagerRelationships();

    @Query("select forms " +
        "from Forms forms " +
        "left join fetch forms.typeLevelCares " +
        "where forms.id =:id ")
    Forms findOneWithEagerRelationships(@Param("id") Long id);

    List<Forms> findAllByDelStatusIsFalse();

    /**
     * Get the forms by facility
     * @param id belonging to a facility
     *  @return List of forms filtered by one facility
     *
     */

    List<Forms> findAllByDelStatusIsFalseAndFacilityId(Long id);

    /**
     * Get all forms filtered by patientProcess
     *
     * @param ppId
     * @param facId
     * @return
     */
    List<Forms> findAllByDelStatusIsFalseAndEnabledIsTrueAndTypePatientProcessIdAndFacilityId(Long ppId, Long facId);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.FormVO(" +
        "form.id, " +
        "form.name, " +
        "form.enabled, " +
        "form.onlyOnePerpatient, " +
        "form.typePatientProcess.name " +
        ") FROM Forms form " +
        "LEFT JOIN form.typePatientProcess patientProcess " +
        "LEFT JOIN form.typeLevelCares levelCare " +
        "LEFT JOIN form.facility facility " +
        "WHERE " +
        "patientProcess.id = :ppId AND " +
        "facility.id = :facId AND " +
        "levelCare.id = :lcId AND " +
        "form.delStatus = false " )
    List<FormVO> findAllByPatientProcessFacilityAndLevelCare(@Param("ppId") Long ppId, @Param("facId") Long facId, @Param("lcId") Long lcId);

    /**
     * Get the forms by facility
     * @param id belonging to a facility
     *  @return List of forms filtered by one facility
     *
     */
    @Query("SELECT form FROM Forms form " +
        "LEFT JOIN FETCH form.typeLevelCares levelCare " +
        "JOIN FETCH form.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "form.delStatus = false " )
    List<Forms> findAllByFacilityWithLevelCare(@Param("id")Long id);

}

