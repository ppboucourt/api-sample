package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Contacts;

import java.util.List;

/**
 * Service Interface for managing Contacts.
 */
public interface ContactsService {

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save
     * @return the persisted entity
     */
    Contacts save(Contacts contacts);

    /**
     *  Get all the contacts.
     *
     *  @return the list of entities
     */
    List<Contacts> findAll();

    /**
     *  Get the "id" contacts.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Contacts findOne(Long id);

    /**
     *  Delete the "id" contacts.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contacts corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Contacts> search(String query);

    List<Contacts> findByRelationOfChart(Long cid, Long rid);

    Contacts getGuarantor(Long chartId);

    List<Contacts> findByChart(Long id);
}
