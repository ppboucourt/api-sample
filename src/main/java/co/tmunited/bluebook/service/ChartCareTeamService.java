package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartCareTeam;
import co.tmunited.bluebook.domain.vo.ChartCareTeamVO;

import java.util.List;

/**
 * Service Interface for managing ChartCareTeam.
 */
public interface ChartCareTeamService {

    /**
     * Save a ChartCareTeam.
     *
     * @param ChartCareTeam the entity to save
     * @return the persisted entity
     */
    ChartCareTeam save(ChartCareTeam ChartCareTeam);

    /**
     *  Get all the ChartCareTeams.
     *
     *  @return the list of entities
     */
    List<ChartCareTeam> findAll();

    /**
     *  Get the "id" ChartCareTeam.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartCareTeam findOne(Long id);

    /**
     *  Delete the "id" ChartCareTeam.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

//    /**
//     * Search for the ChartCareTeam corresponding to the query.
//     *
//     *  @param query the query of the search
//     *
//     *  @return the list of entities
//     */
//    List<ChartCareTeam> search(String query);

    List<ChartCareTeam> findChartCareTeamByChart(Long id);

    List<ChartCareTeam> findChartCareTeamByEmployee(Long id);

    /**
     * Find ChartCareTeams by chart throw VO
     * @param id
     * @return ChartCareTeamVO
     */
    List<ChartCareTeamVO> findChartCareTeamVOByChart(Long id);
}
