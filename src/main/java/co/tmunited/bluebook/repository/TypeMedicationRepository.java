package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeMedication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeMedication entity.
 */
@SuppressWarnings("unused")
public interface TypeMedicationRepository extends JpaRepository<TypeMedication,Long> {

    List<TypeMedication> findAllByDelStatusIsFalse();
}

