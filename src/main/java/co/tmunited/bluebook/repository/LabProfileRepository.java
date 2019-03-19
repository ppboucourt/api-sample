package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.LabProfile;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LabProfile entity.
 */
@SuppressWarnings("unused")
public interface LabProfileRepository extends JpaRepository<LabProfile,Long> {

    @Query("select distinct labProfile from LabProfile labProfile left join fetch labProfile.labCompendiums")
    List<LabProfile> findAllWithEagerRelationships();

    @Query("select labProfile from LabProfile labProfile left join fetch labProfile.labCompendiums where labProfile.id =:id")
    LabProfile findOneWithEagerRelationships(@Param("id") Long id);


    List<LabProfile> findAllByDelStatusIsFalse();

    /**
     * Get the LabProfile by facility
     * @param id belonging to a facility
     * @return List of forms filtered by one facility
     *
     */

    List<LabProfile> findAllByDelStatusIsFalseAndFacilityId(Long id);
}

