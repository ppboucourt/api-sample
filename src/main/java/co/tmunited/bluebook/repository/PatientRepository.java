package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Patient;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findAllByDelStatusIsFalse();


}

