package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.GroupSessionDetails;
import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import java.util.Set;

/**
 * Service Interface for managing GroupSessionDetails.
 */
public interface GroupSessionDetailsService {

    /**
     * Save a groupSessionDetails.
     *
     * @param groupSessionDetails the entity to save
     * @return the persisted entity
     */
    GroupSessionDetails save(GroupSessionDetails groupSessionDetails);



    /**
     * Update a groupSessionDetails, ip is needed.
     *
     * @param groupSessionDetails the entity to update
     * @return the persisted entity
     */
    GroupSessionDetails update(GroupSessionDetails groupSessionDetails, String ip);

    /**
     *  Get all the groupSessionDetails.
     *
     *  @return the list of entities
     */
    List<GroupSessionDetails> findAll();

    /**
     *  Get the "id" groupSessionDetails.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GroupSessionDetails findOne(Long id);

    /**
     *  Delete the "id" groupSessionDetails.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the groupSessionDetails corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<GroupSessionDetails> search(String query);

    /**
     * Search for the groupSessionDetails corresponding to the query.
     *
     *  @param dateTime the date for search
     *
     *  @return the list of entities
     */
    List<GroupSessionDetails> searchByDate(Long id, Date dateTime);

    /**
     * Assign a list of Chart to GroupSessionDetails
     *
     *  @param id the id of GroupSessionDetails
     *
     *  @param listCharts the list of Chart id
     *
     *  @return the GroupSessionDetails
     */
    GroupSessionDetails assignChartsToGroupSessionDetails(Long id, Set<Long> listCharts);



    /**
     * Search for the GroupSessionDetailsChart corresponding to the GroupSessionDetails Id.
     *
     *  @param id the ID for GroupSessionDetails
     *
     *  @return the list of GroupSessionDetailsChart
     */
    List<GroupSessionDetailsChartVO> findChartList(Long id) ;


}
