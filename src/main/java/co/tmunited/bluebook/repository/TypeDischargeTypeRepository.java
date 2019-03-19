package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeDischargeType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeDischargeType entity.
 */
@SuppressWarnings("unused")
public interface TypeDischargeTypeRepository extends JpaRepository<TypeDischargeType,Long> {

    List<TypeDischargeType> findAllByDelStatusIsFalse();
}

