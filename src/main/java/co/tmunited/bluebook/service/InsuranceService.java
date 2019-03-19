package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Insurance;

import java.util.List;

/**
 * Service Interface for managing Insurance.
 */
public interface InsuranceService {

    /**
     * Save a insurance.
     *
     * @param insurance the entity to save
     * @return the persisted entity
     */
    Insurance save(Insurance insurance);

    /**
     *  Get all the insurances.
     *
     *  @return the list of entities
     */
    List<Insurance> findAll();

    /**
     *  Get the "id" insurance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Insurance findOne(Long id);

    /**
     *  Delete the "id" insurance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insurance corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Insurance> search(String query);

    Insurance findByChartIdAndOrder(Long id, Integer value);
}
