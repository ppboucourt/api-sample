package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Drugs;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drugs entity.
 */
@SuppressWarnings("unused")
public interface DrugsRepository extends JpaRepository<Drugs,Long> {

    List<Drugs> findAllByDelStatusIsFalse();
}

