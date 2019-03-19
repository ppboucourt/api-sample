package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.GroupTypeService;
import co.tmunited.bluebook.domain.GroupType;
import co.tmunited.bluebook.repository.GroupTypeRepository;
import co.tmunited.bluebook.repository.search.GroupTypeSearchRepository;
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
 * Service Implementation for managing GroupType.
 */
@Service
@Transactional
public class GroupTypeServiceImpl implements GroupTypeService{

    private final Logger log = LoggerFactory.getLogger(GroupTypeServiceImpl.class);
    
    @Inject
    private GroupTypeRepository groupTypeRepository;

    @Inject
    private GroupTypeSearchRepository groupTypeSearchRepository;

    /**
     * Save a groupType.
     *
     * @param groupType the entity to save
     * @return the persisted entity
     */
    public GroupType save(GroupType groupType) {
        log.debug("Request to save GroupType : {}", groupType);
        GroupType result = groupTypeRepository.save(groupType);
        groupTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the groupTypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GroupType> findAll() {
        log.debug("Request to get all GroupTypes");
        List<GroupType> result = groupTypeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one groupType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GroupType findOne(Long id) {
        log.debug("Request to get GroupType : {}", id);
        GroupType groupType = groupTypeRepository.findOne(id);
        return groupType;
    }

    /**
     *  Delete the  groupType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupType : {}", id);
      GroupType groupType = groupTypeRepository.findOne(id);
      groupType.setDelStatus(true);
      GroupType result = groupTypeRepository.save(groupType);
      
      groupTypeSearchRepository.save(result);
    }

    /**
     * Search for the groupType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupType> search(String query) {
        log.debug("Request to search GroupTypes for query {}", query);
        return StreamSupport
            .stream(groupTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
