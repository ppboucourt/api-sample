package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePatientContactTypes;

import java.util.List;

/**
 * Service Interface for managing TypePatientContactTypes.
 */
public interface TypePatientContactTypesService {

    /**
     * Save a typePatientContactTypes.
     *
     * @param typePatientContactTypes the entity to save
     * @return the persisted entity
     */
    TypePatientContactTypes save(TypePatientContactTypes typePatientContactTypes);

    /**
     *  Get all the typePatientContactTypes.
     *  
     *  @return the list of entities
     */
    List<TypePatientContactTypes> findAll();

    /**
     *  Get the "id" typePatientContactTypes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePatientContactTypes findOne(Long id);

    /**
     *  Delete the "id" typePatientContactTypes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePatientContactTypes corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePatientContactTypes> search(String query);
}
