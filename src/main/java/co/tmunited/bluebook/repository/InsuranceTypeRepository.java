package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.InsuranceType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceType entity.
 */
@SuppressWarnings("unused")
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType,Long> {

    List<InsuranceType> findAllByDelStatusIsFalse();
}

