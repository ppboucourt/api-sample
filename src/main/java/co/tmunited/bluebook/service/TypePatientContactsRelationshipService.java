package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.TypePatientContactsRelationship;

import java.util.List;

/**
 * Service Interface for managing TypePatientContactsRelationship.
 */
public interface TypePatientContactsRelationshipService {

    /**
     * Save a typePatientContactsRelationship.
     *
     * @param typePatientContactsRelationship the entity to save
     * @return the persisted entity
     */
    TypePatientContactsRelationship save(TypePatientContactsRelationship typePatientContactsRelationship);

    /**
     *  Get all the typePatientContactsRelationships.
     *  
     *  @return the list of entities
     */
    List<TypePatientContactsRelationship> findAll();

    /**
     *  Get the "id" typePatientContactsRelationship.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypePatientContactsRelationship findOne(Long id);

    /**
     *  Delete the "id" typePatientContactsRelationship.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typePatientContactsRelationship corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TypePatientContactsRelationship> search(String query);
}
