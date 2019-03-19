package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.RoomService;
import co.tmunited.bluebook.domain.Room;
import co.tmunited.bluebook.repository.RoomRepository;
import co.tmunited.bluebook.repository.search.RoomSearchRepository;
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
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService{

    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Inject
    private RoomRepository roomRepository;

    @Inject
    private RoomSearchRepository roomSearchRepository;

    /**
     * Save a room.
     *
     * @param room the entity to save
     * @return the persisted entity
     */
    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        Room result = roomRepository.save(room);
        roomSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the rooms.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        log.debug("Request to get all Rooms");
        List<Room> result = roomRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one room by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Room findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        Room room = roomRepository.findOne(id);
        return room;
    }

    /**
     *  Delete the  room by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        Room room = roomRepository.findOne(id);
        room.setDelStatus(true);
        Room result = roomRepository.save(room);

        roomSearchRepository.save(result);
    }

    /**
     * Search for the room corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Room> search(String query) {
        log.debug("Request to search Rooms for query {}", query);
        return StreamSupport
            .stream(roomSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<Room> findAllByBuilding(Long id) {
        log.debug("Request to get all Rooms by Building");
        List<Room> result = roomRepository.findAllByDelStatusIsFalseAndBuildingId(id);
        return result;
    }

    @Override
    public List<Room> findAllByFacility(Long id) {
        log.debug("Request to get all Rooms by Facility");
        List<Room> result = roomRepository.findAllByDelStatusIsFalseAndFacilityId(id);
        return result;
    }
}
