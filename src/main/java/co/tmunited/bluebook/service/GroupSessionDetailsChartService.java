package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToGroupSession;
import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;

import java.util.List;

/**
 * Service Interface for managing GroupSessionDetailsChart.
 */
public interface GroupSessionDetailsChartService {

    /**
     * Save a groupSessionDetailsChart.
     *
     * @param groupSessionDetailsChart the entity to save
     * @return the persisted entity
     */
    GroupSessionDetailsChart save(GroupSessionDetailsChart groupSessionDetailsChart);

    /**
     *  Get all the groupSessionDetailsCharts.
     *
     *  @return the list of entities
     */
    List<GroupSessionDetailsChart> findAll();

    /**
     *  Get the "id" groupSessionDetailsChart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GroupSessionDetailsChart findOne(Long id);

    /**
     *  Delete the "id" groupSessionDetailsChart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the groupSessionDetailsChart corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<GroupSessionDetailsChart> search(String query);
    List<GroupSessionDetailsChart>findAllGroupSessionByPatientInCurrentFacility( Long gsId);

    List<GroupSessionDetailsChart> getGroupSessionDetailsChartByChart(Long chartId);

    /**
     *
     * @param chartId
     * @return
     */
    List<GroupSessionDetailsChartVO> findAllByDelStatusIsFalseAndChartId(Long chartId);



}
