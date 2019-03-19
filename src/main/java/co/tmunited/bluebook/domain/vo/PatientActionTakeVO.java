package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.enumeration.PatientActionStatus;
import co.tmunited.bluebook.domain.enumeration.PatientActionTakeStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by adriel on 7/31/17.
 */
public class PatientActionTakeVO {
    private Long patientActionId;
    private Long patientActionPreId;
    private Long patientActionTakeId;

    private String actionName;
    private boolean collected;// Ocurred
    private boolean asNeeded;

    private PatientActionTakeStatus actionTakeStatus;
    private PatientActionStatus actionStatus;
    private boolean canceled;
    private ZonedDateTime collectedDate;

    private String firstPatientResponse;
    private String notes;

    private String patientSignature;


    public PatientActionTakeVO(Long patientActionId, Long patientActionPreId, Long patientActionTakeId,
                               String actionName, boolean collected, boolean asNeeded, PatientActionTakeStatus actionTakeStatus,
                               PatientActionStatus actionStatus, boolean canceled, ZonedDateTime collectedDate,
                               String firstPatientResponse, String notes, String patientSignature) {
        this.patientActionId = patientActionId;
        this.patientActionPreId = patientActionPreId;
        this.patientActionTakeId = patientActionTakeId;
        this.actionName = actionName;
        this.collected = collected;
        this.asNeeded = asNeeded;
        this.actionTakeStatus = actionTakeStatus;
        this.actionStatus = actionStatus;
        this.canceled = canceled;
        this.collectedDate = collectedDate;
        this.firstPatientResponse = firstPatientResponse;
        this.notes = notes;
        this.patientSignature = patientSignature;
    }

    public Long getPatientActionId() {
        return patientActionId;
    }

    public void setPatientActionId(Long patientActionId) {
        this.patientActionId = patientActionId;
    }

    public Long getPatientActionPreId() {
        return patientActionPreId;
    }

    public void setPatientActionPreId(Long patientActionPreId) {
        this.patientActionPreId = patientActionPreId;
    }

    public Long getPatientActionTakeId() {
        return patientActionTakeId;
    }

    public void setPatientActionTakeId(Long patientActionTakeId) {
        this.patientActionTakeId = patientActionTakeId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public PatientActionTakeStatus getActionTakeStatus() {
        return actionTakeStatus;
    }

    public void setActionTakeStatus(PatientActionTakeStatus actionTakeStatus) {
        this.actionTakeStatus = actionTakeStatus;
    }

    public PatientActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(PatientActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public ZonedDateTime getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(ZonedDateTime collectedDate) {
        this.collectedDate = collectedDate;
    }

    public String getFirstPatientResponse() {
        return firstPatientResponse;
    }

    public void setFirstPatientResponse(String firstPatientResponse) {
        this.firstPatientResponse = firstPatientResponse;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(String patientSignature) {
        this.patientSignature = patientSignature;
    }
}
