package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.LabCompendiumService;
import co.tmunited.bluebook.domain.LabCompendium;
import co.tmunited.bluebook.repository.LabCompendiumRepository;
import co.tmunited.bluebook.repository.search.LabCompendiumSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LabCompendium.
 */
@Service
@Transactional
public class LabCompendiumServiceImpl implements LabCompendiumService {

    private final Logger log = LoggerFactory.getLogger(LabCompendiumServiceImpl.class);

    @Inject
    private LabCompendiumRepository labCompendiumRepository;

    @Inject
    private LabCompendiumSearchRepository labCompendiumSearchRepository;

    /**
     * Save a labCompendium.
     *
     * @param labCompendium the entity to save
     * @return the persisted entity
     */
    public LabCompendium save(LabCompendium labCompendium) {
        log.debug("Request to save LabCompendium : {}", labCompendium);
        LabCompendium result = labCompendiumRepository.save(labCompendium);
        labCompendiumSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the labCompendiums.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabCompendium> findAll() {
        log.debug("Request to get all LabCompendiums");
        List<LabCompendium> result = labCompendiumRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one labCompendium by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LabCompendium findOne(Long id) {
        log.debug("Request to get LabCompendium : {}", id);
        LabCompendium labCompendium = labCompendiumRepository.findOne(id);
        return labCompendium;
    }

    /**
     * Delete the  labCompendium by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LabCompendium : {}", id);
        LabCompendium labCompendium = labCompendiumRepository.findOne(id);
        labCompendium.setDelStatus(true);
        LabCompendium result = labCompendiumRepository.save(labCompendium);

        labCompendiumSearchRepository.save(result);
    }

    /**
     * Search for the labCompendium corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
//    @Transactional(readOnly = true)
//    public List<LabCompendium> search(String query) {
//        log.debug("Request to search LabCompendiums for query {}", query);
//        return StreamSupport
//            .stream(labCompendiumSearchRepository.search(fuzzyQuery("code", query)).spliterator(), false).sorted(
//                (o1, o2) -> o1.getCode().compareTo(o2.getCode()))
//                .collect(Collectors.toList());
//    }
    @Transactional(readOnly = true)
    public List<LabCompendium> search(String query) {
        log.debug("Request to search Compendiums for query {}", query);
        query = query + "*";
        List<LabCompendium> result = StreamSupport
            .stream(labCompendiumSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }
}
