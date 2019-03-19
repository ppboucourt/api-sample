package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePatientPrograms;

import java.util.List;

/**
 * Service Interface for managing TypePatientPrograms.
 */
public interface TypePatientProgramsService {

    /**
     * Save a typePatientPrograms.
     *
     * @param typePatientPrograms the entity to save
     * @return the persisted entity
     */
    TypePatientPrograms save(TypePatientPrograms typePatientPrograms);

    /**
     *  Get all the typePatientPrograms.
     *  
     *  @return the list of entities
     */
    List<TypePatientPrograms> findAll();

    /**
     *  Get the "id" typePatientPrograms.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePatientPrograms findOne(Long id);

    /**
     *  Delete the "id" typePatientPrograms.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePatientPrograms corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePatientPrograms> search(String query);
}
