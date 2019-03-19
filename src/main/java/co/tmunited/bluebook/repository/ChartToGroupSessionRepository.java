package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ChartToGroupSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ChartToGroupSession entity.
 */
@SuppressWarnings("unused")
public interface ChartToGroupSessionRepository extends JpaRepository<ChartToGroupSession,Long> {

    List<ChartToGroupSession> findAllByDelStatusIsFalse();

    @Query("SELECT chartToGroupSession FROM ChartToGroupSession chartToGroupSession " +
        "JOIN chartToGroupSession.groupSession groupSession " +
        "WHERE " +
        "groupSession.facility.id = :gsId AND " + //current facility
        "groupSession.delStatus = false ")
    List<ChartToGroupSession> findAllGroupSessionByPatientInCurrentFacility(@Param("gsId") Long gsId);

}

