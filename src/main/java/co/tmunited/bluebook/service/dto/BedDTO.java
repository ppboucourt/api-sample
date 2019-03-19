package co.tmunited.bluebook.service.dto;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Facility;
import co.tmunited.bluebook.domain.Room;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Bed entity.
 */
public class BedDTO implements Serializable {

    private Long id;

    private String name;

    private String notes;

    private String createdBy;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    private ZonedDateTime createdDate;

    @Size(max = 3)
    private String status;

    private Long roomId;

    private Long facilityId;

    private Room room;

    private Facility facility;

    private Long actualChart;

    private Chart activeChart;

    public Chart getActiveChart() {
        return activeChart;
    }

    public void setActiveChart(Chart activeChart) {
        this.activeChart = activeChart;
    }

    public Long getActualChart() {
        return actualChart;
    }

    public void setActualChart(Long actualChart) {
        this.actualChart = actualChart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BedDTO bedDTO = (BedDTO) o;

        if ( ! Objects.equals(id, bedDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BedDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", notes='" + notes + "'" +
            ", created_date='" + createdDate + "'" +
            ", modified_date='" + lastModifiedDate + "'" +
            ", user_created='" + createdBy + "'" +
            ", user_modified='" + lastModifiedBy + "'" +
            ", actualChart='" + actualChart + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
