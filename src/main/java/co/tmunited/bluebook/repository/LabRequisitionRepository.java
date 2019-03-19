package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.LabRequisition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LabRequisition entity.
 */
@SuppressWarnings("unused")
public interface LabRequisitionRepository extends JpaRepository<LabRequisition,Long> {

    List<LabRequisition> findAllByDelStatusIsFalse();
}

