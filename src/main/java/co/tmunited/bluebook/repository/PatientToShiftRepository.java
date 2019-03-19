package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.PatientToShift;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PatientToShift entity.
 */
@SuppressWarnings("unused")
public interface PatientToShiftRepository extends JpaRepository<PatientToShift,Long> {

    List<PatientToShift> findAllByDelStatusIsFalse();
}

