package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientResultFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PatientResultFile entity.
 */
@SuppressWarnings("unused")
public interface PatientResultFileRepository extends JpaRepository<PatientResultFile, Long> {

    @Query(
        "SELECT patientResultFile " +
            "FROM PatientResultFile patientResultFile " +
            "JOIN FETCH patientResultFile.patientResult patientResult " +
            "WHERE " +
            "patientResult.id = :id AND " +
            "patientResultFile.createdDate = " +
            "(SELECT MAX(patientResultFile.createdDate) " +
            "FROM patientResultFile WHERE patientResultFile.patientResult.id = :id)"
    )
    PatientResultFile findLastByPatientResultId(@Param("id") Long id);

    List<PatientResultFile> findAllByPatientResultIdOrderByCreatedDateDesc(@Param("id") Long id);
}
