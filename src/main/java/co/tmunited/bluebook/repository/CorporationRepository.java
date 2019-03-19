package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Corporation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Corporation entity.
 */
@SuppressWarnings("unused")
public interface CorporationRepository extends JpaRepository<Corporation,Long> {

    List<Corporation> findAllByDelStatusIsFalse();
}

