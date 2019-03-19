package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientMedicationTake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientMedicationTake entity.
 */
@SuppressWarnings("unused")
public interface PatientMedicationTakeRepository extends JpaRepository<PatientMedicationTake,Long> {

    @Query("SELECT DISTINCT patientMedicationTake FROM PatientMedicationTake patientMedicationTake " +
        "JOIN FETCH patientMedicationTake.patientMedicationPres prescr " +
        "JOIN FETCH prescr.patientMedication pmedication " +
        "JOIN FETCH pmedication.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "pmedication.medicationStatus <> 'CANCELED' AND (" +
        "(patientMedicationTake.scheduleDate BETWEEN :startD AND :endD) OR" +
        "(patientMedicationTake.collectedDate BETWEEN :startD AND :endD AND patientMedicationTake.scheduleDate IS NULL)" +
        ")"
    )
    List<PatientMedicationTake> findAllMedicationsByDate(@Param("id") Long id, @Param("startD") ZonedDateTime start, @Param("endD") ZonedDateTime end);
}
