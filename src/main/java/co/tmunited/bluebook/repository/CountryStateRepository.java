package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.CountryState;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CountryState entity.
 */
@SuppressWarnings("unused")
public interface CountryStateRepository extends JpaRepository<CountryState,Long> {

    List<CountryState> findAllByDelStatusIsFalse();
}

