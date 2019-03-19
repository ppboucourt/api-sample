package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.GroupSession;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the GroupSession entity.
 */
@SuppressWarnings("unused")
public interface GroupSessionRepository extends JpaRepository<GroupSession,Long> {

    List<GroupSession> findAllByDelStatusIsFalse();

    /**
     * Get the GroupSession by facility
     * @param id belonging to a facility
     * @return List of forms filtered by one facility
     *
     */

    List<GroupSession> findAllByDelStatusIsFalseAndFacilityId(Long id);
    List<GroupSession> findAllByDelStatusIsFalseAndFacilityIdAndEndTimeLessThan(Long id, ZonedDateTime end);
}

