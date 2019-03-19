package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.LabProfileService;
import co.tmunited.bluebook.domain.LabProfile;
import co.tmunited.bluebook.repository.LabProfileRepository;
import co.tmunited.bluebook.repository.search.LabProfileSearchRepository;
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
 * Service Implementation for managing LabProfile.
 */
@Service
@Transactional
public class LabProfileServiceImpl implements LabProfileService{

    private final Logger log = LoggerFactory.getLogger(LabProfileServiceImpl.class);

    @Inject
    private LabProfileRepository labProfileRepository;

    @Inject
    private LabProfileSearchRepository labProfileSearchRepository;

    /**
     * Save a labProfile.
     *
     * @param labProfile the entity to save
     * @return the persisted entity
     */
    public LabProfile save(LabProfile labProfile) {
        log.debug("Request to save LabProfile : {}", labProfile);
        LabProfile result = labProfileRepository.save(labProfile);
        labProfileSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the labProfiles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabProfile> findAll() {
        log.debug("Request to get all LabProfiles");
        List<LabProfile> result = labProfileRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one labProfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LabProfile findOne(Long id) {
        log.debug("Request to get LabProfile : {}", id);
        LabProfile labProfile = labProfileRepository.findOneWithEagerRelationships(id);
        return labProfile;
    }

    /**
     *  Delete the  labProfile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LabProfile : {}", id);
      LabProfile labProfile = labProfileRepository.findOneWithEagerRelationships(id);
      labProfile.setDelStatus(true);
      LabProfile result = labProfileRepository.save(labProfile);

      labProfileSearchRepository.save(result);
    }

    /**
     * Search for the labProfile corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabProfile> search(String query) {
        log.debug("Request to search LabProfiles for query {}", query);
        return StreamSupport
            .stream(labProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the labProfiles filtering by facility.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabProfile> findAllByFacility(Long id) {
        log.debug("Request to get all LabProfiles");
        List<LabProfile> result = labProfileRepository.findAllByDelStatusIsFalseAndFacilityId(id);

        return result;
    }
}
