package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.PatientMedicationTakeStatus;
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
 * A PatientMedicationTake.
 */
@Entity
@Table(name = "patient_medication_take")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientmedicationtake")
public class PatientMedicationTake extends AbstractAuditingEntity implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_medication_take_id_seq")
    @SequenceGenerator(
        name="patient_medication_take_id_seq",
        sequenceName="patient_medication_take_id_seq",
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
    private String medication;

    @Formula(value = "now()")
    private String dose;

    @Formula(value = "now()")
    private String route;

    @Column(name = "notes")
    private String notes;

    @Column(name = "first_patient_response")
    private String firstPatientResponse;

    @Column(name = "patient_signature")
    private String patientSignature;

    @Enumerated(EnumType.STRING)
    @Column(name = "medication_take_status")
    private PatientMedicationTakeStatus medicationTakeStatus;

    @Column(name = "take_details")
    private String takeDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_medication_pres_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PatientMedicationPres patientMedicationPres;

    @Column(name = "patient_medication_pres_id")
    private Long patientMedicationPresId;

    @ManyToOne
    private Employee administerBy;

    @Formula(value = "0")
    private Long medicationId;

    public Long getPatientMedicationPresId() {
        return patientMedicationPresId;
    }

    public void setPatientMedicationPresId(Long patientMedicationPresId) {
        this.patientMedicationPresId = patientMedicationPresId;
    }

    public PatientMedicationTakeStatus getMedicationTakeStatus() {
        return medicationTakeStatus;
    }

    public void setMedicationTakeStatus(PatientMedicationTakeStatus medicationTakeStatus) {
        this.medicationTakeStatus = medicationTakeStatus;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public Employee getAdministerBy() {
        return administerBy;
    }

    public void setAdministerBy(Employee administerBy) {
        this.administerBy = administerBy;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
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

    public PatientMedicationTake collected(Boolean collected) {
        this.collected = collected;
        return this;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public ZonedDateTime getCollectedDate() {
        return collectedDate;
    }

    public PatientMedicationTake collectedDate(ZonedDateTime collectedDate) {
        this.collectedDate = collectedDate;
        return this;
    }

    public void setCollectedDate(ZonedDateTime collectedDate) {
        this.collectedDate = collectedDate;
    }

    public ZonedDateTime getScheduleDate() {
        return scheduleDate;
    }

    public PatientMedicationTake scheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public void setScheduleDate(ZonedDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public PatientMedicationTake canceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public PatientMedicationPres getPatientMedicationPres() {
        return patientMedicationPres;
    }

    public PatientMedicationTake patientMedicationPres(PatientMedicationPres patientMedicationPres) {
        this.patientMedicationPres = patientMedicationPres;
        return this;
    }

    public void setPatientMedicationPres(PatientMedicationPres patientMedicationPres) {
        this.patientMedicationPres = patientMedicationPres;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getTakeDetails() {
        return takeDetails;
    }

    public void setTakeDetails(String takeDetails) {
        this.takeDetails = takeDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientMedicationTake patientMedicationTake = (PatientMedicationTake) o;
        if(patientMedicationTake.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientMedicationTake.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientMedicationTake{" +
            "id=" + id +
            ", collected='" + collected + "'" +
            ", collectedDate='" + collectedDate + "'" +
            ", scheduleDate='" + scheduleDate + "'" +
            ", canceled='" + canceled + "'" +
            '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.getScheduleDate().compareTo(((PatientMedicationTake) o).getScheduleDate());
    }
}
