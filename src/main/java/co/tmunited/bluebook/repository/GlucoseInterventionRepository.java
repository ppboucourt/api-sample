package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.GlucoseIntervention;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GlucoseIntervention entity.
 */
@SuppressWarnings("unused")
public interface GlucoseInterventionRepository extends JpaRepository<GlucoseIntervention,Long> {

}
