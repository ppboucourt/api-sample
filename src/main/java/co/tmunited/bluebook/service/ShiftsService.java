package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.Shifts;
import co.tmunited.bluebook.domain.vo.ShiftVO;
import co.tmunited.bluebook.service.dto.FileDTO;

import java.util.List;

/**
 * Service Interface for managing Shifts.
 */
public interface ShiftsService {

    /**
     * Save a shifts.
     *
     * @param shifts the entity to save
     * @return the persisted entity
     */
    Shifts save(Shifts shifts);

    /**
     * Get all the shifts.
     *
     * @return the list of entities
     */
    List<Shifts> findAll();

    /**
     * Get all the shifts.
     *
     * @return the list of entities
     */
    List<ShiftVO> findAllShifts();

    /**
     * Get the "id" shifts.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Shifts findOne(Long id);

    /**
     * Delete the "id" shifts.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shifts corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Shifts> search(String query);


    File attachFile(FileDTO fileDTO);

    void deleteFile(Long id);
}
