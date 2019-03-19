package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeMaritalStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeMaritalStatus entity.
 */
@SuppressWarnings("unused")
public interface TypeMaritalStatusRepository extends JpaRepository<TypeMaritalStatus,Long> {

    List<TypeMaritalStatus> findAllByDelStatusIsFalse();
}

