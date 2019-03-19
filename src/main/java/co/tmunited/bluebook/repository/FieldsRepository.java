package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Fields;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fields entity.
 */
@SuppressWarnings("unused")
public interface FieldsRepository extends JpaRepository<Fields,Long> {

    List<Fields> findAllByDelStatusIsFalse();
}

