package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Laboratories;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Laboratories entity.
 */
@SuppressWarnings("unused")
public interface LaboratoriesRepository extends JpaRepository<Laboratories,Long> {

    List<Laboratories> findAllByDelStatusIsFalse();
}

