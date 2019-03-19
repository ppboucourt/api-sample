package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePatientProcess;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePatientProcess entity.
 */
@SuppressWarnings("unused")
public interface TypePatientProcessRepository extends JpaRepository<TypePatientProcess,Long> {

    List<TypePatientProcess> findAllByDelStatusIsFalse();

    List<TypePatientProcess> findAllByDelStatusIsFalseAndTypePageIdAndFacilityId(Long pagId, Long facId);


}

