package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.XMLConfig;
import co.tmunited.bluebook.repository.XMLConfigRepository;
import co.tmunited.bluebook.repository.search.XMLConfigSearchRepository;
import co.tmunited.bluebook.service.XMLConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing XMLConfig.
 */
@Service
@Transactional
public class XMLConfigServiceImpl implements XMLConfigService {

    private final Logger log = LoggerFactory.getLogger(XMLConfigServiceImpl.class);

    @Inject
    private XMLConfigRepository xMLConfigRepository;

    @Inject
    private XMLConfigSearchRepository xMLConfigSearchRepository;

    /**
     * Save a xMLConfig.
     *
     * @param xMLConfig the entity to save
     * @return the persisted entity
     */
    public XMLConfig save(XMLConfig xMLConfig) {
        log.debug("Request to save XMLConfig : {}", xMLConfig);
        XMLConfig result = xMLConfigRepository.save(xMLConfig);
        xMLConfigSearchRepository.save(result);

        return result;
    }

    /**
     * Get all the xMLConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<XMLConfig> findAll() {
        log.debug("Request to get all XMLConfigs");
        List<XMLConfig> result = xMLConfigRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one xMLConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public XMLConfig findOne(Long id) {
        log.debug("Request to get XMLConfig : {}", id);
        XMLConfig xMLConfig = xMLConfigRepository.findOne(id);
        return xMLConfig;
    }

    /**
     * Delete the  xMLConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete XMLConfig : {}", id);
        XMLConfig xMLConfig = xMLConfigRepository.findOne(id);
        xMLConfig.setDelStatus(true);
        XMLConfig result = xMLConfigRepository.save(xMLConfig);

        xMLConfigSearchRepository.save(result);
    }

    /**
     * Search for the xMLConfig corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<XMLConfig> search(String query) {
        log.debug("Request to search XMLConfigs for query {}", query);
        return StreamSupport
            .stream(xMLConfigSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public XMLConfig getDefaultPatientXMLConfig() {
        return xMLConfigRepository.findFirstByDelStatusIsFalseOrderByCreatedDateDesc();
    }
}
