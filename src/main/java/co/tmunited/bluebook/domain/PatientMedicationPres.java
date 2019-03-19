package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PatientMedicationPres.
 */
@Entity
@Table(name = "patient_medication_pres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientmedicationpres")
public class PatientMedicationPres extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_medication_pres_id_seq")
    @SequenceGenerator(
        name="patient_medication_pres_id_seq",
        sequenceName="patient_medication_pres_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "staring_date")
    private ZonedDateTime staringDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "hours")
    private Long hours = new Long(0);

    @Column(name = "as_needed")
    private Boolean asNeeded;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "icd_10_patient_medication_pres",
        joinColumns = @JoinColumn(name = "patient_medication_press_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "icd_10s_id", referencedColumnName = "ID"))
    private Set<Icd10> icd10s = new HashSet<>();

    @OneToMany(mappedBy = "patientMedicationPres", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdDate ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientMedicationTake> patientMedicationTakes = new HashSet<>();

    @ManyToOne
    private Medications medications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_medication_id", referencedColumnName = "id")
    private PatientMedication patientMedication;

    @JoinColumn(name = "dosege_form")
    private String dosegeForm;

    @JoinColumn(name = "dose")
    private String dose;

    @JoinColumn(name = "amount_to_dispense")
    private String amountToDispense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStaringDate() {
        return staringDate;
    }

    public PatientMedicationPres staringDate(ZonedDateTime staringDate) {
        this.staringDate = staringDate;
        return this;
    }

    public void setStaringDate(ZonedDateTime staringDate) {
        this.staringDate = staringDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public PatientMedicationPres endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(Boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Set<Icd10> getIcd10s() {
        return icd10s;
    }

    public PatientMedicationPres icd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
        return this;
    }

    public PatientMedicationPres addIcd10(Icd10 icd10) {
        icd10s.add(icd10);
        icd10.getPatientMedicationPress().add(this);
        return this;
    }

    public PatientMedicationPres removeIcd10(Icd10 icd10) {
        icd10s.remove(icd10);
        icd10.getPatientMedicationPress().remove(this);
        return this;
    }

    public void setIcd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
    }

    public Set<PatientMedicationTake> getPatientMedicationTakes() {
        return patientMedicationTakes;
    }

    public PatientMedicationPres patientMedicationTakes(Set<PatientMedicationTake> patientMedicationTakes) {
        this.patientMedicationTakes = patientMedicationTakes;
        return this;
    }

    public PatientMedicationPres addPatientMedicationTake(PatientMedicationTake patientMedicationTake) {
        patientMedicationTakes.add(patientMedicationTake);
        patientMedicationTake.setPatientMedicationPres(this);
        return this;
    }

    public PatientMedicationPres removePatientMedicationTake(PatientMedicationTake patientMedicationTake) {
        patientMedicationTakes.remove(patientMedicationTake);
        patientMedicationTake.setPatientMedicationPres(null);
        return this;
    }

    public void setPatientMedicationTakes(Set<PatientMedicationTake> patientMedicationTakes) {
        this.patientMedicationTakes = patientMedicationTakes;
    }

    public Medications getMedications() {
        return medications;
    }

    public void setMedications(Medications medications) {
        this.medications = medications;
    }

    public PatientMedication getPatientMedication() {
        return patientMedication;
    }

    public PatientMedicationPres patientMedication(PatientMedication patientMedication) {
        this.patientMedication = patientMedication;
        return this;
    }

    public String getDosegeForm() {
        return dosegeForm;
    }

    public void setDosegeForm(String dosegeForm) {
        this.dosegeForm = dosegeForm;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getAmountToDispense() {
        return amountToDispense;
    }

    public void setAmountToDispense(String amountToDispense) {
        this.amountToDispense = amountToDispense;
    }

    public void setPatientMedication(PatientMedication patientMedication) {
        this.patientMedication = patientMedication;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientMedicationPres patientMedicationPres = (PatientMedicationPres) o;
        if(patientMedicationPres.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientMedicationPres.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientMedicationPres{" +
            "id=" + id +
            ", staringDate='" + staringDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
