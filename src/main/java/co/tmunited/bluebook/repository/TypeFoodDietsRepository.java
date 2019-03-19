package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeFoodDiets;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeFoodDiets entity.
 */
@SuppressWarnings("unused")
public interface TypeFoodDietsRepository extends JpaRepository<TypeFoodDiets,Long> {

    List<TypeFoodDiets> findAllByDelStatusIsFalse();
}

