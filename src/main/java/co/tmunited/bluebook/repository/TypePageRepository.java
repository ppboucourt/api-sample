package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePage entity.
 */
@SuppressWarnings("unused")
public interface TypePageRepository extends JpaRepository<TypePage,Long> {

    List<TypePage> findAllByDelStatusIsFalse();
}

