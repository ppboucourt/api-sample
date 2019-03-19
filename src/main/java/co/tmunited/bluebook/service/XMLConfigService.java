package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.XMLConfig;

import java.util.List;

/**
 * Service Interface for managing XMLConfig.
 */
public interface XMLConfigService {

    /**
     * Save a xMLConfig.
     *
     * @param xMLConfig the entity to save
     * @return the persisted entity
     */
    XMLConfig save(XMLConfig xMLConfig);

    /**
     *  Get all the xMLConfigs.
     *
     *  @return the list of entities
     */
    List<XMLConfig> findAll();

    /**
     *  Get the "id" xMLConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    XMLConfig findOne(Long id);

    /**
     *  Delete the "id" xMLConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the xMLConfig corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<XMLConfig> search(String query);

    XMLConfig getDefaultPatientXMLConfig();
}
