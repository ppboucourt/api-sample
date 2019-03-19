package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeEthnicity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeEthnicity entity.
 */
@SuppressWarnings("unused")
public interface TypeEthnicityRepository extends JpaRepository<TypeEthnicity,Long> {

    List<TypeEthnicity> findAllByDelStatusIsFalse();
}

