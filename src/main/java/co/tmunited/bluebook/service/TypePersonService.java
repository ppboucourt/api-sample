package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePerson;

import java.util.List;

/**
 * Service Interface for managing TypePerson.
 */
public interface TypePersonService {

    /**
     * Save a typePerson.
     *
     * @param typePerson the entity to save
     * @return the persisted entity
     */
    TypePerson save(TypePerson typePerson);

    /**
     *  Get all the typePeople.
     *  
     *  @return the list of entities
     */
    List<TypePerson> findAll();

    /**
     *  Get the "id" typePerson.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePerson findOne(Long id);

    /**
     *  Delete the "id" typePerson.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePerson corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePerson> search(String query);
}
