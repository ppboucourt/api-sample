package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.InsuranceLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceLevel entity.
 */
@SuppressWarnings("unused")
public interface InsuranceLevelRepository extends JpaRepository<InsuranceLevel,Long> {

    List<InsuranceLevel> findAllByDelStatusIsFalse();
}

