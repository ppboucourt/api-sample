package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeMedicationRoutes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeMedicationRoutes entity.
 */
@SuppressWarnings("unused")
public interface TypeMedicationRoutesRepository extends JpaRepository<TypeMedicationRoutes,Long> {

    List<TypeMedicationRoutes> findAllByDelStatusIsFalse();
}

