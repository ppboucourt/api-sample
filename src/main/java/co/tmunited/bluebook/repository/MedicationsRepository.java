package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Medications;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Medications entity.
 */
@SuppressWarnings("unused")
public interface MedicationsRepository extends JpaRepository<Medications, Long> {


    @Query("select med from Medications med")
    List<Medications> findAllWithEagerRelationships();

    List<Medications> findAllByDelStatusIsFalse();
}

