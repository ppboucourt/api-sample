package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Facility;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Facility entity.
 */
@SuppressWarnings("unused")
public interface FacilityRepository extends JpaRepository<Facility,Long> {

    List<Facility> findAllByDelStatusIsFalse();
}

