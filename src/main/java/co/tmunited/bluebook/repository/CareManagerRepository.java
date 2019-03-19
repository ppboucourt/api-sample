package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.CareManager;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareManager entity.
 */
@SuppressWarnings("unused")
public interface CareManagerRepository extends JpaRepository<CareManager,Long> {

    List<CareManager> findAllByDelStatusIsFalse();
}

