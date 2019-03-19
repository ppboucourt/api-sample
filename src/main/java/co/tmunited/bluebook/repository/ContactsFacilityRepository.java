package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ContactsFacility;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContactsFacility entity.
 */
@SuppressWarnings("unused")
public interface ContactsFacilityRepository extends JpaRepository<ContactsFacility,Long> {

    List<ContactsFacility> findAllByDelStatusIsFalse();

    /**
     * Get the contacts for the current facility
     * @param facilityId
     * @return List of contactsFacility
     */
    List<ContactsFacility> findAllByDelStatusIsFalseAndFacilityId(Long facilityId);
}

