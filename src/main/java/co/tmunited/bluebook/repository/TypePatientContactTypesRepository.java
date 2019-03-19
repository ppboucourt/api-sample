package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePatientContactTypes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePatientContactTypes entity.
 */
@SuppressWarnings("unused")
public interface TypePatientContactTypesRepository extends JpaRepository<TypePatientContactTypes,Long> {

    List<TypePatientContactTypes> findAllByDelStatusIsFalse();
}

