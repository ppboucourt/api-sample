package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ReportDetails;

import java.util.List;

/**
 * Service Interface for managing ReportDetails.
 */
public interface ReportDetailsService {

    /**
     * Save a reportDetails.
     *
     * @param reportDetails the entity to save
     * @return the persisted entity
     */
    ReportDetails save(ReportDetails reportDetails);

    /**
     *  Get all the reportDetails.
     *  
     *  @return the list of entities
     */
    List<ReportDetails> findAll();

    /**
     *  Get the "id" reportDetails.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReportDetails findOne(Long id);

    /**
     *  Delete the "id" reportDetails.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reportDetails corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ReportDetails> search(String query);
}
