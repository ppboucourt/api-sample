package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.Bed;

import co.tmunited.bluebook.domain.vo.BedBuildingVO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Bed entity.
 */
@SuppressWarnings("unused")
public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findAllByDelStatusIsFalse();

    /**
     * Get the bed for the current facility
     *
     * @param facilityId
     * @return List of Beds
     */
    List<Bed> findAllByDelStatusIsFalseAndFacilityId(Long facilityId);

    /**
     * Get all bed by facility that are not being used
     *
     * @param facilityId
     * @return List of Beds
     */

    @Query("SELECT bed FROM Bed bed " +
        "JOIN FETCH bed.room room " +
        "JOIN FETCH room.building building " +
        "JOIN FETCH building.facility facility " +
        "WHERE " +
        "bed.delStatus = false AND " +
        "room.delStatus = false AND " +
        "building.delStatus = false AND " +
        "(bed.chartId = :chartId OR  bed.chartId IS NULL) AND " +
        "facility.id = :facilityId AND " +
        "facility.delStatus = false " +
        "ORDER BY bed.name ")
    List<Bed> findAllByDelStatusIsFalseAndChartIdIsNullOrChartIdAndFacilityIdOrderByName(@Param("chartId") Long chartId,
                                                                                         @Param("facilityId") Long facilityId);

    @Query("SELECT bed FROM Bed bed " +
        "JOIN bed.room room " +
        "JOIN room.building building " +
        "WHERE " +
        "building.id = :id AND " +
        "bed.delStatus = false AND " +
        "room.delStatus = false AND " +
        "building.delStatus = false " +
        "ORDER BY bed.name,room.name ")
    List<Bed> findAllByBuilding(@Param("id") Long id);

    @Query("SELECT NEW co.tmunited.bluebook.domain.vo.BedBuildingVO(" +
        "bed.id, " +
        "bed.name, " +
        "bed.notes, " +
        "bed.status, " +
        "bed.createdBy, " +
        "bed.createdDate, " +
        "room.id, " +
        "room, " +
        "chart.id, " +
        "CONCAT(patient.firstName, ' ', patient.lastName), " +
        "pictureRef.picture, " +
        "pictureRef.pictureContentType, " +
        "chart.mrNo" +
        ") " +
        "FROM Bed bed " +
        "JOIN bed.room room " +
        "LEFT JOIN bed.chart chart " +
        "LEFT JOIN chart.pictureRef pictureRef " +
        "LEFT JOIN chart.patient patient " +
        "JOIN room.building building " +
        "WHERE " +
        "building.id = :id AND " +
        "bed.delStatus = false AND " +
        "room.delStatus = false AND " +
        "building.delStatus = false " +
        "ORDER BY bed.name,room.name ")
    List<BedBuildingVO> findAllByBuildingId(@Param("id") Long id);


    /**
     * Get the bed belonging to a patient
     *
     * @param chartId
     * @return the insurannce filtered by the parameters
     */
    Bed findByChartId(Long chartId);

    @Query("SELECT bed FROM Bed bed " +
        "JOIN FETCH bed.room room " +
        "JOIN FETCH room.building building " +
        "WHERE " +
        "bed.delStatus = false AND " +
        "bed.chartId IN (:ids) AND " +
        "room.delStatus = false AND " +
        "building.delStatus = false " +
        "ORDER BY bed.name,room.name ")
    List<Bed> findAllByChartIds(@Param("ids") List<Long> ids);


    @Query("SELECT bed FROM Bed bed " +
        "JOIN FETCH bed.room room " +
        "JOIN FETCH room.building building " +
        "JOIN FETCH building.facility facility " +
        "WHERE " +
        "facility.id = :id AND " +
        "bed.chartId IS NULL AND " +
        "bed.delStatus = false AND " +
        "room.delStatus = false AND " +
        "building.delStatus = false " +
        "ORDER BY bed.name,room.name ")
    List<Bed> findAllFreeBedsByBuilding(@Param("id") Long id);
}

