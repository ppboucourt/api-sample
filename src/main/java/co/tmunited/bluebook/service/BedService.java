package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Bed;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.BedBuildingVO;
import co.tmunited.bluebook.service.dto.BedDTO;

import java.util.List;

/**
 * Service Interface for managing Bed.
 */
public interface BedService {

    /**
     * Save a bed.
     *
     * @param bed the entity to save
     * @return the persisted entity
     */
    Bed save(Bed bed);

    /**
     *  Get all the beds.
     *
     *  @return the list of entities
     */
    List<Bed> findAll();

    /**
     *  Get the "id" bed.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Bed findOne(Long id);

    /**
     *  Delete the "id" bed.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bed corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Bed> search(String query);

    /**
     *  Get all the beds filtering by facility.
     *
     *  @return the list of entities
     */
    List<Bed> findAllByFacility(Long id);

//    BedDTO findOneWithChart(Long id);

    /**
     * Get the facility's bed that are not being user
     * @param id
     * @return list of Beds
     */
    List<Bed> findAllFreeBeds(Long id, Long actualChart);

    List<Bed> findAllByBuilding(Long id);

    List<BedBuildingVO> findAllByBuildingId(Long id);

    List<Bed> findBedByListIds(CollectedBody collectedBody);
}
