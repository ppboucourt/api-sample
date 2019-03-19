package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.TypeEmployee;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeEmployee entity.
 */
@SuppressWarnings("unused")
public interface TypeEmployeeRepository extends JpaRepository<TypeEmployee,Long> {

    List<TypeEmployee> findAllByDelStatusIsFalse();

    @Query("select distinct typeEmployee from TypeEmployee typeEmployee left join fetch typeEmployee.authorities")
    List<TypeEmployee> findAllWithEagerRelationships();

    @Query("select typeEmployee from TypeEmployee typeEmployee left join fetch typeEmployee.authorities where typeEmployee.id =:id")
    TypeEmployee findOneWithEagerRelationships(@Param("id") Long id);


    @Query("select distinct typeEmployee from TypeEmployee typeEmployee left join fetch typeEmployee.authorities where typeEmployee.delStatus=false")
    List<TypeEmployee> findAllWithEagerRelationshipsAndDelStatusIsFalse();

    @Query("select typeEmployee from TypeEmployee typeEmployee left join fetch typeEmployee.authorities where typeEmployee.id =:id and typeEmployee.delStatus=false")
    TypeEmployee findOneWithEagerRelationshipsDelStatusIsFalse(@Param("id") Long id);

    @Query("select typeEmployee from TypeEmployee typeEmployee left join fetch typeEmployee.authorities where typeEmployee.delStatus=false and typeEmployee.name='Physician'")
    TypeEmployee findPhysicianWithEagerRelationshipsDelStatusIsFalse();
}

