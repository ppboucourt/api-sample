package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeContactFacilityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeContactFacilityType entity.
 */
@SuppressWarnings("unused")
public interface TypeContactFacilityTypeRepository extends JpaRepository<TypeContactFacilityType,Long> {

    List<TypeContactFacilityType> findAllByDelStatusIsFalse();
}

