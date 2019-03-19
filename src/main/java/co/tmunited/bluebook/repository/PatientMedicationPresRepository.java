package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientMedicationPres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the PatientMedicationPres entity.
 */
@SuppressWarnings("unused")
public interface PatientMedicationPresRepository extends JpaRepository<PatientMedicationPres, Long> {

    List<PatientMedicationPres> findAllByDelStatusIsFalseAndPatientMedicationId(Long id);

    @Query("SELECT DISTINCT patientMedicationPres FROM PatientMedicationPres patientMedicationPres " +
        "JOIN patientMedicationPres.medications medication " +
        "JOIN patientMedicationPres.patientMedication pmedication " +
        "JOIN pmedication.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "patientMedicationPres.asNeeded = true AND " +
        "pmedication.medicationStatus <> 'CANCELED'"
    )
    List<PatientMedicationPres> findAllMedicationAsNeededByDate(@Param("id") Long id);


    @Query("SELECT DISTINCT patientMedicationPres FROM PatientMedicationPres patientMedicationPres " +
        "JOIN patientMedicationPres.patientMedication pmedication " +
        "JOIN pmedication.chart chart " +
        "WHERE " +
        "chart.id = :id AND " +
        "pmedication.medicationStatus <> 'CANCELED'"
    )
    List<PatientMedicationPres> findAllMedicationByChartAndDate(@Param("id") Long id);
}
