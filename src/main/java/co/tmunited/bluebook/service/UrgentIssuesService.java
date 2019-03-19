package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.UrgentIssues;

import java.util.List;

/**
 * Service Interface for managing UrgentIssues.
 */
public interface UrgentIssuesService {

    /**
     * Save a urgentIssues.
     *
     * @param urgentIssues the entity to save
     * @return the persisted entity
     */
    UrgentIssues save(UrgentIssues urgentIssues);

    /**
     *  Get all the urgentIssues.
     *
     *  @return the list of entities
     */
    List<UrgentIssues> findAll();

    /**
     *  Get the "id" urgentIssues.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UrgentIssues findOne(Long id);

    /**
     *  Delete the "id" urgentIssues.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the urgentIssues corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<UrgentIssues> search(String query);

    /**
     *  Get all the urgentIssues by chart.
     *
     *  @return the list of entities
     */
    List<UrgentIssues> findAllByChart(Long id);

    /**
     *  Get all the urgentIssues by chart and employee.
     *
     *  @return the list of entities
     */
    List<UrgentIssues> findAllByChartAndEmployee(Long chartId, Long empId);
}
