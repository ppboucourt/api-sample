package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Diet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Diet entity.
 */
@SuppressWarnings("unused")
public interface DietRepository extends JpaRepository<Diet,Long> {

    List<Diet> findAllByDelStatusIsFalse();
}

