package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeDosage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeDosage entity.
 */
@SuppressWarnings("unused")
public interface TypeDosageRepository extends JpaRepository<TypeDosage,Long> {

    List<TypeDosage> findAllByDelStatusIsFalse();
}

