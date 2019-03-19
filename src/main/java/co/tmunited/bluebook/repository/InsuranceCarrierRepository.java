package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.InsuranceCarrier;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceCarrier entity.
 */
@SuppressWarnings("unused")
public interface InsuranceCarrierRepository extends JpaRepository<InsuranceCarrier,Long> {

    List<InsuranceCarrier> findAllByDelStatusIsFalse();
}

