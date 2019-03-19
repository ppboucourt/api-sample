package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeSpeciality;

import java.util.List;

/**
 * Service Interface for managing TypeSpeciality.
 */
public interface TypeSpecialityService {

    /**
     * Save a TypeSpeciality.
     *
     * @param TypeSpeciality the entity to save
     * @return the persisted entity
     */
    TypeSpeciality save(TypeSpeciality TypeSpeciality);

    /**
     *  Get all the TypeSpeciality.
     *
     *  @return the list of entities
     */
    List<TypeSpeciality> findAll();

    /**
     *  Get the "id" TypeSpeciality.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeSpeciality findOne(Long id);

    /**
     *  Delete the "id" TypeSpeciality.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the TypeSpeciality corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<TypeSpeciality> search(String query);
}
