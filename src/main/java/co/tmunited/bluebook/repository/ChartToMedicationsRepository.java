package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToMedications;

import co.tmunited.bluebook.domain.Medications;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the ChartToMedications entity.
 */
@SuppressWarnings("unused")
public interface ChartToMedicationsRepository extends JpaRepository<ChartToMedications,Long> {

    List<ChartToMedications> findAllByDelStatusIsFalse();

    /**
     * Get all prescription by a patient in one date
     * @param id
     * @param date
     * @return
     */
    List<ChartToMedications> findAllByChartIdAndDatePrescription(Long id, ZonedDateTime date);


    @Query("SELECT chartToMedications FROM ChartToMedications chartToMedications " +
        "JOIN chartToMedications.chart chart " +
        "WHERE " +
        "chart.facility.id = :facilityId AND " + //current facility
        "chart.delStatus = false AND " +
        "chartToMedications.taken is null AND " +
        "chartToMedications.delStatus = false")
    List<ChartToMedications> findAllMedicationsByPatientInCurrentFacility(@Param("facilityId") Long facilityId);


    @Query("SELECT DISTINCT chartToMedications.datePrescription FROM ChartToMedications chartToMedications " +
        "JOIN chartToMedications.chart chart " +
        "WHERE " +
        "chart.facility.id = :facilityId AND " + //current facility
        "chart.delStatus = false AND " +
        "chartToMedications.taken is null AND " +
        "chartToMedications.delStatus = false")
    List<ZonedDateTime> findAllMedicationsByPatientInCurrentFacilityDistinctPrescription(@Param("facilityId") Long facilityId);
}

