package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Hospitalization;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hospitalization entity.
 */
@SuppressWarnings("unused")
public interface HospitalizationRepository extends JpaRepository<Hospitalization,Long> {

    List<Hospitalization> findAllByDelStatusIsFalse();
}

