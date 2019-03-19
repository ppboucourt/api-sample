package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Fields;

import java.util.List;

/**
 * Service Interface for managing Fields.
 */
public interface FieldsService {

    /**
     * Save a fields.
     *
     * @param fields the entity to save
     * @return the persisted entity
     */
    Fields save(Fields fields);

    /**
     *  Get all the fields.
     *  
     *  @return the list of entities
     */
    List<Fields> findAll();

    /**
     *  Get the "id" fields.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Fields findOne(Long id);

    /**
     *  Delete the "id" fields.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fields corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Fields> search(String query);
}
