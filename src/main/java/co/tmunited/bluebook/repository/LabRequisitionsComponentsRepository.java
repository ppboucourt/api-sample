package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.LabRequisitionsComponents;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LabRequisitionsComponents entity.
 */
@SuppressWarnings("unused")
public interface LabRequisitionsComponentsRepository extends JpaRepository<LabRequisitionsComponents,Long> {

    List<LabRequisitionsComponents> findAllByDelStatusIsFalse();
}

