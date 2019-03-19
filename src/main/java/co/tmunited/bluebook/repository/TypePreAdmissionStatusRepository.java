package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypePreAdmissionStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypePreAdmissionStatus entity.
 */
@SuppressWarnings("unused")
public interface TypePreAdmissionStatusRepository extends JpaRepository<TypePreAdmissionStatus,Long> {

    List<TypePreAdmissionStatus> findAllByDelStatusIsFalse();
}

