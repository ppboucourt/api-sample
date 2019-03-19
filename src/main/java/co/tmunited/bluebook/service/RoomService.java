package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Room;

import java.util.List;

/**
 * Service Interface for managing Room.
 */
public interface RoomService {

    /**
     * Save a room.
     *
     * @param room the entity to save
     * @return the persisted entity
     */
    Room save(Room room);

    /**
     *  Get all the rooms.
     *
     *  @return the list of entities
     */
    List<Room> findAll();

    /**
     *  Get the "id" room.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Room findOne(Long id);

    /**
     *  Delete the "id" room.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the room corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Room> search(String query);

    List<Room> findAllByBuilding(Long id);

    List<Room> findAllByFacility(Long id);
}
