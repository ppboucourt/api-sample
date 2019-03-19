package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientOrderTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PatientOrderTest entity.
 */
@SuppressWarnings("unused")
public interface PatientOrderTestRepository extends JpaRepository<PatientOrderTest,Long> {

    List<PatientOrderTest> findAllByDelStatusIsFalse();

    List<PatientOrderTest> findAllByDelStatusIsFalseAndPatientOrderId(Long id);
}
