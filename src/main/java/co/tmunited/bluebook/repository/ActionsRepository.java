package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Actions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Actions entity.
 */
@SuppressWarnings("unused")
public interface ActionsRepository extends JpaRepository<Actions,Long> {

    List<Actions> findAllByDelStatusIsFalse();
}

