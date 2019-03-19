package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Via;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Via entity.
 */
@SuppressWarnings("unused")
public interface ViaRepository extends JpaRepository<Via,Long> {

    List<Via> findAllByDelStatusIsFalse();
}

