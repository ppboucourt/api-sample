package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeMedication;

import java.util.List;

/**
 * Service Interface for managing TypeMedication.
 */
public interface TypeMedicationService {

    /**
     * Save a typeMedication.
     *
     * @param typeMedication the entity to save
     * @return the persisted entity
     */
    TypeMedication save(TypeMedication typeMedication);

    /**
     *  Get all the typeMedications.
     *  
     *  @return the list of entities
     */
    List<TypeMedication> findAll();

    /**
     *  Get the "id" typeMedication.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeMedication findOne(Long id);

    /**
     *  Delete the "id" typeMedication.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeMedication corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeMedication> search(String query);
}
