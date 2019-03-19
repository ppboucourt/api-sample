package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.BuildingService;
import co.tmunited.bluebook.domain.Building;
import co.tmunited.bluebook.repository.BuildingRepository;
import co.tmunited.bluebook.repository.search.BuildingSearchRepository;
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
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingServiceImpl implements BuildingService{

    private final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);

    @Inject
    private BuildingRepository buildingRepository;

    @Inject
    private BuildingSearchRepository buildingSearchRepository;

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    public Building save(Building building) {
        log.debug("Request to save Building : {}", building);
        Building result = buildingRepository.save(building);
        buildingSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the buildings.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Building> findAll() {
        log.debug("Request to get all Buildings");
        List<Building> result = buildingRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one building by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Building findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        return building;
    }

    /**
     *  Delete the  building by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
      Building building = buildingRepository.findOne(id);
      building.setDelStatus(true);
      Building result = buildingRepository.save(building);

      buildingSearchRepository.save(result);
    }

    /**
     * Search for the building corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Building> search(String query) {
        log.debug("Request to search Buildings for query {}", query);
        return StreamSupport
            .stream(buildingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Return the List of building corresponding to a facility
     * @param id facility required
     * @return
     */
    @Override
    public List<Building> findAllByFacility(Long id) {
        log.debug("Request to search Buildings by facility {}");
        List<Building> result = buildingRepository.findAllByDelStatusIsFalseAndFacilityId(id);
        return result;
    }
}
