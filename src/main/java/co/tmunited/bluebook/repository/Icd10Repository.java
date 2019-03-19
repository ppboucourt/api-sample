package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Icd10;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Icd10 entity.
 */
@SuppressWarnings("unused")
public interface Icd10Repository extends JpaRepository<Icd10,Long> {

    List<Icd10> findAllByDelStatusIsFalse();
}

