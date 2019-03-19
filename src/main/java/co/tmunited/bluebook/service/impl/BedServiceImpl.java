package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.FacilityRepository;
import co.tmunited.bluebook.repository.RoomRepository;
import co.tmunited.bluebook.service.BedService;
import co.tmunited.bluebook.domain.Bed;
import co.tmunited.bluebook.repository.BedRepository;
import co.tmunited.bluebook.repository.search.BedSearchRepository;
import co.tmunited.bluebook.domain.vo.BedBuildingVO;
import co.tmunited.bluebook.service.dto.BedDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Bed.
 */
@Service
@Transactional
public class BedServiceImpl implements BedService {

    private final Logger log = LoggerFactory.getLogger(BedServiceImpl.class);

    @Inject
    private BedRepository bedRepository;

    @Inject
    private BedSearchRepository bedSearchRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private FacilityRepository facilityRepository;

    @Inject
    private RoomRepository roomRepository;

    /**
     * Save a bed.
     *
     * @param bed the entity to save
     * @return the persisted entity
     */
    public Bed save(Bed bed) {
        log.debug("Request to save Bed : {}", bed);
        Bed result = bedRepository.save(bed);
        bedSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the beds.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Bed> findAll() {
        log.debug("Request to get all Beds");
        List<Bed> result = bedRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one bed by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Bed findOne(Long id) {
        log.debug("Request to get Bed : {}", id);
        Bed bed = bedRepository.findOne(id);
        return bed;
    }

    /**
     * Delete the  bed by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bed : {}", id);
        Bed bed = bedRepository.findOne(id);
        bed.setDelStatus(true);
        Bed result = bedRepository.save(bed);

        bedSearchRepository.save(result);
    }

    /**
     * Search for the bed corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Bed> search(String query) {
        log.debug("Request to search Beds for query {}", query);
        return StreamSupport
            .stream(bedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<Bed> findAllByFacility(Long id) {
        log.debug("Request to get all Beds");
        List<Bed> result = bedRepository.findAllByDelStatusIsFalseAndFacilityId(id);
        return result;
    }

//    @Override
//    public BedDTO findOneWithChart(Long id) {
//        log.debug("Request to get Bed : {}", id);
//        Bed bed = bedRepository.findOne(id);
//        bed.setActiveChart(chartRepository.findOne(bed.getActualChart()));
//        BedDTO bedDTO = bedMapper.bedToBedDTO(bed);
//        bedDTO.setRoom(bed.getRoom());
//        bedDTO.setFacility(bed.getFacility());
//
//        return bedDTO;
//    }

    @Override
    public List<Bed> findAllFreeBeds(Long facilityId, Long actualChart) {
        log.debug("Request to get Bed by Facility and that are not being used", facilityId);
//        List<BedDTO> result = bedRepository.findAllByDelStatusIsFalseAndActualChartIsNullAndFacilityId(id).stream()
//            .map(bedMapper::bedToBedDTO).collect(Collectors.toCollection(LinkedList::new));
        return bedRepository.findAllByDelStatusIsFalseAndChartIdIsNullOrChartIdAndFacilityIdOrderByName(actualChart, facilityId);
    }

    public List<Bed> findAllByBuilding(Long id) {
        List<Bed> result = bedRepository.findAllByBuilding(id);
        return result;
    }

    @Override
    public List<BedBuildingVO> findAllByBuildingId(Long id) {
        return  bedRepository.findAllByBuildingId(id);
    }


    public List<Bed> findBedByListIds(CollectedBody collectedBody) {
        List<Bed> result = new ArrayList<>();
        collectedBody.getIds().stream().forEach(bedId -> {
                result.add(bedRepository.findOne(bedId));
            }
        );
        return result;
    }
}
