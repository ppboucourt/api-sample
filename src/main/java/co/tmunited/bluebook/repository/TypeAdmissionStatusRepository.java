package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeAdmissionStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeAdmissionStatus entity.
 */
@SuppressWarnings("unused")
public interface TypeAdmissionStatusRepository extends JpaRepository<TypeAdmissionStatus,Long> {

    List<TypeAdmissionStatus> findAllByDelStatusIsFalse();
}

