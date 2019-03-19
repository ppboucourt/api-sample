package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Patient_properties;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Patient_properties entity.
 */
@SuppressWarnings("unused")
public interface Patient_propertiesRepository extends JpaRepository<Patient_properties,Long> {

    List<Patient_properties> findAllByDelStatusIsFalse();
}

