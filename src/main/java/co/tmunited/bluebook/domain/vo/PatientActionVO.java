package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.enumeration.PatientActionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by adriel on 7/28/17.
 */
public class PatientActionVO {
    private Long patientActionId;
    private Long patientActionPresId;
    private String patientActionPresAction;
    private Boolean asNeeded;
    private Long signedById;
    private Boolean signed;
    private String firstName;
    private String lastName;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private PatientActionStatus patientActionStatus;

    public PatientActionVO(Long patientActionId, Long patientActionPresId, String patientActionPresAction, Boolean asNeeded,Long signedById, Boolean signed, String firstName,
                           String lastName, ZonedDateTime startDate, ZonedDateTime endDate, PatientActionStatus patientActionStatus) {
        this.patientActionId = patientActionId;
        this.patientActionPresId = patientActionPresId;
        this.asNeeded = asNeeded;
        this.signedById = signedById;
        this.signed = signed;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientActionStatus = patientActionStatus;
        this.patientActionPresAction = patientActionPresAction;
    }

    public Long getPatientActionId() {
        return patientActionId;
    }

    public void setPatientActionId(Long patientActionId) {
        this.patientActionId = patientActionId;
    }

    public Long getPatientActionPresId() {
        return patientActionPresId;
    }

    public void setPatientActionPresId(Long patientActionPresId) {
        this.patientActionPresId = patientActionPresId;
    }

    public Boolean getAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(Boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public PatientActionStatus getPatientActionStatus() {
        return patientActionStatus;
    }

    public void setPatientActionStatus(PatientActionStatus patientActionStatus) {
        this.patientActionStatus = patientActionStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatientActionPresAction() {
        return patientActionPresAction;
    }

    public void setPatientActionPresAction(String patientActionPresAction) {
        this.patientActionPresAction = patientActionPresAction;
    }

    public Long getSignedById() {
        return signedById;
    }

    public void setSignedById(Long signedById) {
        this.signedById = signedById;
    }
}
