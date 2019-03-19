package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeTreatmentPlanStatuses;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeTreatmentPlanStatuses entity.
 */
@SuppressWarnings("unused")
public interface TypeTreatmentPlanStatusesRepository extends JpaRepository<TypeTreatmentPlanStatuses,Long> {

    List<TypeTreatmentPlanStatuses> findAllByDelStatusIsFalse();
}

