package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeSpeciality entity.
 */
@SuppressWarnings("unused")
public interface TypeSpecialityRepository extends JpaRepository<TypeSpeciality,Long> {

    List<TypeSpeciality> findAllByDelStatusIsFalse();
}

