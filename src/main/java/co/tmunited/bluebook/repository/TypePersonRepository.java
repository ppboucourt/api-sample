package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePerson;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePerson entity.
 */
@SuppressWarnings("unused")
public interface TypePersonRepository extends JpaRepository<TypePerson,Long> {

    List<TypePerson> findAllByDelStatusIsFalse();
}

