package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePreAdmissionStatus;

import java.util.List;

/**
 * Service Interface for managing TypePreAdmissionStatus.
 */
public interface TypePreAdmissionStatusService {

    /**
     * Save a typePreAdmissionStatus.
     *
     * @param typePreAdmissionStatus the entity to save
     * @return the persisted entity
     */
    TypePreAdmissionStatus save(TypePreAdmissionStatus typePreAdmissionStatus);

    /**
     *  Get all the typePreAdmissionStatuses.
     *  
     *  @return the list of entities
     */
    List<TypePreAdmissionStatus> findAll();

    /**
     *  Get the "id" typePreAdmissionStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePreAdmissionStatus findOne(Long id);

    /**
     *  Delete the "id" typePreAdmissionStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePreAdmissionStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePreAdmissionStatus> search(String query);
}
