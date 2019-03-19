package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeMedicationRoutes;

import java.util.List;

/**
 * Service Interface for managing TypeMedicationRoutes.
 */
public interface TypeMedicationRoutesService {

    /**
     * Save a typeMedicationRoutes.
     *
     * @param typeMedicationRoutes the entity to save
     * @return the persisted entity
     */
    TypeMedicationRoutes save(TypeMedicationRoutes typeMedicationRoutes);

    /**
     *  Get all the typeMedicationRoutes.
     *  
     *  @return the list of entities
     */
    List<TypeMedicationRoutes> findAll();

    /**
     *  Get the "id" typeMedicationRoutes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeMedicationRoutes findOne(Long id);

    /**
     *  Delete the "id" typeMedicationRoutes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeMedicationRoutes corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeMedicationRoutes> search(String query);
}
