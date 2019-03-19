package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientActionTake;
import co.tmunited.bluebook.domain.vo.PatientActionTakeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientActionTake entity.
 */
@SuppressWarnings("unused")
public interface PatientActionTakeRepository extends JpaRepository<PatientActionTake, Long> {

    @Query("SELECT DISTINCT patientActionTake FROM PatientActionTake patientActionTake " +
        "JOIN patientActionTake.patientActionPre prescr " +
        "JOIN prescr.patientAction paction " +
        "JOIN paction.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "paction.actionStatus <> 'CANCELED' AND (" +
        "(patientActionTake.scheduleDate BETWEEN :startD AND :endD) OR " +
        "(patientActionTake.collectedDate BETWEEN :startD AND :endD AND patientActionTake.scheduleDate IS NULL)" +
        ")"
    )
    List<PatientActionTake> findAllActionsByDate(@Param("id") Long id, @Param("startD") LocalDateTime start, @Param("endD") LocalDateTime end);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.PatientActionTakeVO(" +
        "paction.id, " +
        "prescr.id, " +
        "patientActionTake.id, " +
        "prescr.action, " +
        "patientActionTake.collected, " +
        "prescr.asNeeded, " +
        "patientActionTake.actionTakeStatus, " +
        "paction.actionStatus, " +
        "patientActionTake.canceled, " +
        "patientActionTake.collectedDate, " +
        "patientActionTake.firstPatientResponse, " +
        "patientActionTake.notes, " +
        "patientActionTake.patientSignature " +

        ") " +
        " FROM PatientActionTake patientActionTake " +
        "JOIN patientActionTake.patientActionPre prescr " +
        "JOIN prescr.patientAction paction " +
        "JOIN paction.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "paction.actionStatus <> 'CANCELED' AND (" +
        "(patientActionTake.scheduleDate BETWEEN :startD AND :endD) OR " +
        "(patientActionTake.collectedDate BETWEEN :startD AND :endD AND patientActionTake.scheduleDate IS NULL)" +
        ")"
    )
    List<PatientActionTakeVO> findAllActionsByDateVO(@Param("id") Long id, @Param("startD") ZonedDateTime start, @Param("endD") ZonedDateTime end);
}
