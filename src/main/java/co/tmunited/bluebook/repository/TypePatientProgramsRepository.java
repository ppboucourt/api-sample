package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePatientPrograms;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePatientPrograms entity.
 */
@SuppressWarnings("unused")
public interface TypePatientProgramsRepository extends JpaRepository<TypePatientPrograms,Long> {

    List<TypePatientPrograms> findAllByDelStatusIsFalse();
}

