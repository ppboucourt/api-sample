package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Room;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DTO for the Bed entity.
 */
public class BedBuildingVO implements Serializable {

    private Long id;

    private String name;

    private String notes;

    private String status;

    private String createdBy;

    private ZonedDateTime createdDate;

    private Long roomId;

    private Room room;

    private Long chartId;

    private String patientFullName;

    private String patientPicture;

    private String patientPictureContentType;

    private String patientMrNo;

    public BedBuildingVO(Long id, String name, String notes, String status, String createdBy, ZonedDateTime createdDate,
                         Long roomId, Room room, Long chartId, String patientFullName,
                         String patientPicture, String patientPictureContentType, String patientMrNo) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.roomId = roomId;
        this.room = room;
        this.chartId = chartId;
        this.patientFullName = patientFullName;
        this.patientPicture = patientPicture;
        this.patientPictureContentType = patientPictureContentType;
        this.patientMrNo = patientMrNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }


    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientPictureContentType() {
        return patientPictureContentType;
    }

    public void setPatientPictureContentType(String patientPictureContentType) {
        this.patientPictureContentType = patientPictureContentType;
    }

    public String getPatientPicture() {
        return patientPicture;
    }

    public void setPatientPicture(String patientPicture) {
        this.patientPicture = patientPicture;
    }
}
