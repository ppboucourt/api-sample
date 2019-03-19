package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.GroupSessionDetails;

import co.tmunited.bluebook.domain.enumeration.Progress;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the GroupSessionDetails entity.
 */
@SuppressWarnings("unused")
public interface GroupSessionDetailsRepository extends JpaRepository<GroupSessionDetails,Long> {

    List<GroupSessionDetails> findAllByDelStatusIsFalse();
    List<GroupSessionDetails> findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndStartBetween(Long id, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
    GroupSessionDetails findByIdAndGroupSessionDetailsChartsDelStatusIsFalse(Long id);
    List<GroupSessionDetails>  findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(Long id, Progress progress);

}
