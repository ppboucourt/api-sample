package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToLevelCare;
import co.tmunited.bluebook.domain.vo.ChartToLevelCareVO;

import java.util.List;

/**
 * Service Interface for managing ChartToLevelCare.
 */
public interface ChartToLevelCareService {

    /**
     * Save a chartToLevelCare.
     *
     * @param chartToLevelCare the entity to save
     * @return the persisted entity
     */
    ChartToLevelCare save(ChartToLevelCare chartToLevelCare);

    /**
     *  Get all the chartToLevelCares.
     *
     *  @return the list of entities
     */
    List<ChartToLevelCare> findAll();

    /**
     *  Get the "id" chartToLevelCare.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToLevelCare findOne(Long id);

    /**
     *  Delete the "id" chartToLevelCare.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToLevelCare corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ChartToLevelCare> search(String query);

    List<ChartToLevelCareVO> findByChart(Long id);

    ChartToLevelCare findLastByChart(Long id);

}
