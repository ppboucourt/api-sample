package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientActionPre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PatientActionPre entity.
 */
@SuppressWarnings("unused")
public interface PatientActionPreRepository extends JpaRepository<PatientActionPre,Long> {

    List<PatientActionPre> findAllByDelStatusIsFalseAndPatientActionId(Long id);

    @Query("SELECT DISTINCT patientActionPre FROM PatientActionPre patientActionPre " +
        "JOIN patientActionPre.patientAction paction " +
        "JOIN paction.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "patientActionPre.asNeeded = true AND " +
        "paction.actionStatus <> 'CANCELED'"
    )
    List<PatientActionPre> findAllActionsAsNeededByDate(@Param("id") Long id);

}
