package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.InsuranceRelation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceRelation entity.
 */
@SuppressWarnings("unused")
public interface InsuranceRelationRepository extends JpaRepository<InsuranceRelation,Long> {

    List<InsuranceRelation> findAllByDelStatusIsFalse();
}

