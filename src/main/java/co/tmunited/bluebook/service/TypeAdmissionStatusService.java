package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeAdmissionStatus;

import java.util.List;

/**
 * Service Interface for managing TypeAdmissionStatus.
 */
public interface TypeAdmissionStatusService {

    /**
     * Save a typeAdmissionStatus.
     *
     * @param typeAdmissionStatus the entity to save
     * @return the persisted entity
     */
    TypeAdmissionStatus save(TypeAdmissionStatus typeAdmissionStatus);

    /**
     *  Get all the typeAdmissionStatuses.
     *  
     *  @return the list of entities
     */
    List<TypeAdmissionStatus> findAll();

    /**
     *  Get the "id" typeAdmissionStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeAdmissionStatus findOne(Long id);

    /**
     *  Delete the "id" typeAdmissionStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeAdmissionStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeAdmissionStatus> search(String query);
}
