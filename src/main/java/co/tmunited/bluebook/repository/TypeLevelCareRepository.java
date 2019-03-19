package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.TypeLevelCare;

import co.tmunited.bluebook.domain.vo.FormVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeLevelCare entity.
 */
@SuppressWarnings("unused")
public interface TypeLevelCareRepository extends JpaRepository<TypeLevelCare,Long> {

    List<TypeLevelCare> findAllByDelStatusIsFalse();

    @Query("SELECT form FROM TypeLevelCare levelCare " +
        "JOIN levelCare.forms form " +
        "JOIN levelCare.facility facility " +
        "WHERE " +
        "facility.id = :facId AND " +
        "levelCare.id = :lcId AND " +
        "form.delStatus = false AND " +
        "form.loadAutomatic = true AND " +
        "levelCare.delStatus = false ")
    List<Forms> findAllByFacilityAndLevelCareLoadedAutomatic(@Param("facId") Long facId, @Param("lcId") Long lcId);

    List<TypeLevelCare> findAllByFacilityIdAndDelStatusIsFalse(Long id);
}

