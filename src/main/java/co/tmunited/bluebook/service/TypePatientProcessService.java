package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePatientProcess;

import java.util.List;

/**
 * Service Interface for managing TypePatientProcess.
 */
public interface TypePatientProcessService {

    /**
     * Save a typePatientProcess.
     *
     * @param typePatientProcess the entity to save
     * @return the persisted entity
     */
    TypePatientProcess save(TypePatientProcess typePatientProcess);

    /**
     *  Get all the typePatientProcesses.
     *
     *  @return the list of entities
     */
    List<TypePatientProcess> findAll();

    /**
     *  Get the "id" typePatientProcess.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePatientProcess findOne(Long id);

    /**
     *  Delete the "id" typePatientProcess.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePatientProcess corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<TypePatientProcess> search(String query);

    List<TypePatientProcess> findAllByTypePage(Long pagId, Long facId);

}
