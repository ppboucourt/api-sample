package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePatientContactsRelationship;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePatientContactsRelationship entity.
 */
@SuppressWarnings("unused")
public interface TypePatientContactsRelationshipRepository extends JpaRepository<TypePatientContactsRelationship,Long> {

    List<TypePatientContactsRelationship> findAllByDelStatusIsFalse();
}

