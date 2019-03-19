package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Room;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Room entity.
 */
@SuppressWarnings("unused")
public interface RoomRepository extends JpaRepository<Room,Long> {

    List<Room> findAllByDelStatusIsFalse();

    List<Room> findAllByDelStatusIsFalseAndBuildingId(Long id);

    List<Room> findAllByDelStatusIsFalseAndFacilityId(Long id);
}

