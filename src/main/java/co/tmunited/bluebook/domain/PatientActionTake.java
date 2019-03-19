package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.PatientActionTakeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PatientActionTake.
 */
@Entity
@Table(name = "patient_action_take")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientactiontake")
public class PatientActionTake extends AbstractAuditingEntity implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_action_take_id_seq")
    @SequenceGenerator(
        name="patient_action_take_id_seq",
        sequenceName="patient_action_take_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "collected")
    private Boolean collected = false;

    @Column(name = "collected_date")
    private ZonedDateTime collectedDate;

    @Column(name = "schedule_date")
    private ZonedDateTime scheduleDate;

    @Column(name = "canceled")
    private Boolean canceled = false;

    @Formula(value = "now()")
    private String action;

    @Formula(value = "0")
    private Long actionId;

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_action_pre_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PatientActionPre patientActionPre;

    @Column(name = "patient_action_pre_id")
    private Long patientActionPreId;

    @ManyToOne
    private Employee administerBy;

    @Column(name = "notes")
    private String notes;

    @Column(name = "first_patient_response")
    private String firstPatientResponse;

    @Column(name = "patient_signature")
    private String patientSignature;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_take_status")
    private PatientActionTakeStatus actionTakeStatus;

    public Long getPatientActionPreId() {
        return patientActionPreId;
    }

    public void setPatientActionPreId(Long patientActionPreId) {
        this.patientActionPreId = patientActionPreId;
    }

    public Boolean getCollected() {
        return collected;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFirstPatientResponse() {
        return firstPatientResponse;
    }

    public void setFirstPatientResponse(String firstPatientResponse) {
        this.firstPatientResponse = firstPatientResponse;
    }

    public String getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(String patientSignature) {
        this.patientSignature = patientSignature;
    }

    public PatientActionTakeStatus getActionTakeStatus() {
        return actionTakeStatus;
    }

    public void setActionTakeStatus(PatientActionTakeStatus actionTakeStatus) {
        this.actionTakeStatus = actionTakeStatus;
    }

    public Employee getAdministerBy() {
        return administerBy;
    }

    public void setAdministerBy(Employee administerBy) {
        this.administerBy = administerBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCollected() {
        return collected;
    }

    public PatientActionTake collected(Boolean collected) {
        this.collected = collected;
        return this;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public ZonedDateTime getCollectedDate() {
        return collectedDate;
    }

    public PatientActionTake collectedDate(ZonedDateTime collectedDate) {
        this.collectedDate = collectedDate;
        return this;
    }

    public void setCollectedDate(ZonedDateTime collectedDate) {
        this.collectedDate = collectedDate;
    }

    public ZonedDateTime getScheduleDate() {
        return scheduleDate;
    }

    public PatientActionTake scheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public void setScheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public PatientActionTake canceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public PatientActionPre getPatientActionPre() {
        return patientActionPre;
    }

    public PatientActionTake patientActionPre(PatientActionPre patientActionPre) {
        this.patientActionPre = patientActionPre;
        return this;
    }

    public void setPatientActionPre(PatientActionPre patientActionPre) {
        this.patientActionPre = patientActionPre;
    }

    @Override
    public int compareTo(Object o) {
        return this.getScheduleDate().compareTo(((PatientActionTake) o).getScheduleDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientActionTake patientActionTake = (PatientActionTake) o;
        if(patientActionTake.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientActionTake.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientActionTake{" +
            "id=" + id +
            ", collected='" + collected + "'" +
            ", collectedDate='" + collectedDate + "'" +
            ", scheduleDate='" + scheduleDate + "'" +
            ", canceled='" + canceled + "'" +
            '}';
    }
}
