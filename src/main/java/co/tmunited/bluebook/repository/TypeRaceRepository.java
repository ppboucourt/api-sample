package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeRace;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeRace entity.
 */
@SuppressWarnings("unused")
public interface TypeRaceRepository extends JpaRepository<TypeRace,Long> {

    List<TypeRace> findAllByDelStatusIsFalse();
}

