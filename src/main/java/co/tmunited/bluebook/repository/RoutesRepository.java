package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Routes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Routes entity.
 */
@SuppressWarnings("unused")
public interface RoutesRepository extends JpaRepository<Routes,Long> {

    List<Routes> findAllByDelStatusIsFalse();
}

