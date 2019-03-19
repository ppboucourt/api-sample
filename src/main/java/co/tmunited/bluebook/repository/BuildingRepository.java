package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Building;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Building entity.
 */
@SuppressWarnings("unused")
public interface BuildingRepository extends JpaRepository<Building,Long> {

    List<Building> findAllByDelStatusIsFalse();

    List<Building> findAllByDelStatusIsFalseAndFacilityId(Long id);
}

