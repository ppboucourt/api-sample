package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Reports;

import java.util.List;

/**
 * Service Interface for managing Reports.
 */
public interface ReportsService {

    /**
     * Save a reports.
     *
     * @param reports the entity to save
     * @return the persisted entity
     */
    Reports save(Reports reports);

    /**
     *  Get all the reports.
     *  
     *  @return the list of entities
     */
    List<Reports> findAll();

    /**
     *  Get the "id" reports.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Reports findOne(Long id);

    /**
     *  Delete the "id" reports.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reports corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Reports> search(String query);
}
