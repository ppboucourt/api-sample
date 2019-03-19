package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Signature;

import java.util.List;

/**
 * Service Interface for managing Signature.
 */
public interface SignatureService {

    /**
     * Save a signature.
     *
     * @param signature the entity to save
     * @return the persisted entity
     */
    Signature save(Signature signature);

    /**
     *  Get all the signatures.
     *  
     *  @return the list of entities
     */
    List<Signature> findAll();

    /**
     *  Get the "id" signature.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Signature findOne(Long id);

    /**
     *  Delete the "id" signature.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the signature corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Signature> search(String query);
}
