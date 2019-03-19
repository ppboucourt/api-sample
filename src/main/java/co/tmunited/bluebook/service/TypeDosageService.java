package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypeDosage;

import java.util.List;

/**
 * Service Interface for managing TypeDosage.
 */
public interface TypeDosageService {

    /**
     * Save a typeDosage.
     *
     * @param typeDosage the entity to save
     * @return the persisted entity
     */
    TypeDosage save(TypeDosage typeDosage);

    /**
     *  Get all the typeDosages.
     *  
     *  @return the list of entities
     */
    List<TypeDosage> findAll();

    /**
     *  Get the "id" typeDosage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeDosage findOne(Long id);

    /**
     *  Delete the "id" typeDosage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeDosage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypeDosage> search(String query);
}
