package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Tables;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tables entity.
 */
@SuppressWarnings("unused")
public interface TablesRepository extends JpaRepository<Tables,Long> {

    List<Tables> findAllByDelStatusIsFalse();
}

