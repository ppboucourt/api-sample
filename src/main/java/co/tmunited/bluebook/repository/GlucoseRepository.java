package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Glucose entity.
 */
@SuppressWarnings("unused")
public interface GlucoseRepository extends JpaRepository<Glucose,Long> {


    List<Glucose> findAllByDelStatusIsFalse();

    @Query("select distinct glucose from Glucose glucose left join fetch glucose.glucoseInterventions where glucose.delStatus=false")
    List<Glucose> findAllWithEagerRelationshipsAndDelStatusIsFalse();

    @Query("select glucose from Glucose glucose left join fetch glucose.glucoseInterventions where glucose.id =:id and glucose.delStatus=false")
    Glucose findOneWithEagerRelationshipsAndDelStatusIsFalse(@Param("id") Long id);

    List<Glucose> findAllByDelStatusIsFalseAndChartId(Long id);

}
