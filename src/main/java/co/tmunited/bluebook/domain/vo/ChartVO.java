package co.tmunited.bluebook.domain.vo;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PpTmUnited on 6/15/2017.
 */
public class ChartVO implements Serializable {

    private long id;

    private String firstName;
    private String lastName;
    private String mrNo;
    private String bed;
    private String sustance;
    private String payment;
    private String facility;
    private String picture;
    private String pictureContentType;

    //For Incoming and discharge Dates
    private ZonedDateTime admissionDate;
    private ZonedDateTime dischargeDate;

    //For Evaluation and Forms
    private Long eFId;
    private String eFName;

    private String phone;
    private Boolean waitingRoom;


    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, String picture, String pictureContentType) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
    }


    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, byte[] picture, String pictureContentType) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = new String(picture);
        this.pictureContentType = pictureContentType;
    }

    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, String picture, String pictureContentType, ZonedDateTime admissionDate,
                   ZonedDateTime dischargeDate) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, String picture, String pictureContentType, ZonedDateTime admissionDate,
                   ZonedDateTime dischargeDate, Boolean waitingRoom) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.waitingRoom = waitingRoom;
    }

    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, String picture, String pictureContentType, ZonedDateTime admissionDate,
                   ZonedDateTime dischargeDate, Long eFId, String eFName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = picture;
        this.pictureContentType = pictureContentType;

        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;

        this.eFId = eFId;
        this.eFName = eFName;
    }


    public ChartVO(long id, String firstName, String lastName, String mrNo,
                   String bed, String sustance, String payment, String facility, String picture, String pictureContentType, String phone) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrNo = mrNo;
        this.bed = bed;
        this.sustance = sustance;
        this.payment = payment;
        this.facility = facility;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firtName) {
        this.firstName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getSustance() {
        return sustance;
    }

    public void setSustance(String sustance) {
        this.sustance = sustance;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getPayment() {

        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public ZonedDateTime getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(ZonedDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public ZonedDateTime getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(ZonedDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Long getEFId() {
        return eFId;
    }

    public void setEFId(Long eFId) {
        this.eFId = eFId;
    }

    public String getEFName() {
        return eFName;
    }

    public void setEFName(String eFName) {
        this.eFName = eFName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Boolean getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(Boolean waitingRoom) {
        this.waitingRoom = waitingRoom;
    }
}
