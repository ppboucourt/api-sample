package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Tube;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Tube entity.
 */
@SuppressWarnings("unused")
public interface TubeRepository extends JpaRepository<Tube,Long> {

    List<Tube> findAllByDelStatusIsFalse();
}

