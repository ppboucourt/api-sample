package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePatientPropertyCondition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePatientPropertyCondition entity.
 */
@SuppressWarnings("unused")
public interface TypePatientPropertyConditionRepository extends JpaRepository<TypePatientPropertyCondition,Long> {

    List<TypePatientPropertyCondition> findAllByDelStatusIsFalse();
}

