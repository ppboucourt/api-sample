package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PatientActionPre.
 */
@Entity
@Table(name = "patient_action_pre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientactionpre")
public class PatientActionPre extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_action_pre_id_seq")
    @SequenceGenerator(
        name="patient_action_pre_id_seq",
        sequenceName="patient_action_pre_id_seq",
        allocationSize=1
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

    @NotNull
    @Column(name = "action")
    private String action;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "icd_10_patient_action_pre",
        joinColumns = @JoinColumn(name = "patient_action_pres_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "icd_10s_id", referencedColumnName = "ID"))
    private Set<Icd10> icd10s = new HashSet<>();

    @OneToMany(mappedBy = "patientActionPre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createdDate ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientActionTake> patientActionTakes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_action_id", referencedColumnName = "id")
    private PatientAction patientAction;


    @Column(name = "patient_action_id", insertable = false, updatable = false)
    private Long patientActionId;

    public Long getPatientActionId() {
        return patientActionId;
    }

    public void setPatientActionId(Long patientActionId) {
        this.patientActionId = patientActionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStaringDate() {
        return staringDate;
    }

    public PatientActionPre staringDate(ZonedDateTime staringDate) {
        this.staringDate = staringDate;
        return this;
    }

    public void setStaringDate(ZonedDateTime staringDate) {
        this.staringDate = staringDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public PatientActionPre endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Boolean getAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(Boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Set<Icd10> getIcd10s() {
        return icd10s;
    }

    public PatientActionPre icd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
        return this;
    }

    public PatientActionPre addIcd10(Icd10 icd10) {
        icd10s.add(icd10);
        icd10.getPatientActionPres().add(this);
        return this;
    }

    public PatientActionPre removeIcd10(Icd10 icd10) {
        icd10s.remove(icd10);
        icd10.getPatientActionPres().remove(this);
        return this;
    }

    public void setIcd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
    }

    public Set<PatientActionTake> getPatientActionTakes() {
        return patientActionTakes;
    }

    public PatientActionPre patientActionTakes(Set<PatientActionTake> patientActionTakes) {
        this.patientActionTakes = patientActionTakes;
        return this;
    }

    public PatientActionPre addPatientActionTake(PatientActionTake patientActionTake) {
        patientActionTakes.add(patientActionTake);
        patientActionTake.setPatientActionPre(this);
        return this;
    }

    public PatientActionPre removePatientActionTake(PatientActionTake patientActionTake) {
        patientActionTakes.remove(patientActionTake);
        patientActionTake.setPatientActionPre(null);
        return this;
    }

    public void setPatientActionTakes(Set<PatientActionTake> patientActionTakes) {
        this.patientActionTakes = patientActionTakes;
    }

    public PatientAction getPatientAction() {
        return patientAction;
    }

    public PatientActionPre patientAction(PatientAction patientAction) {
        this.patientAction = patientAction;
        return this;
    }

    public void setPatientAction(PatientAction patientAction) {
        this.patientAction = patientAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientActionPre patientActionPre = (PatientActionPre) o;
        if(patientActionPre.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientActionPre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientActionPre{" +
            "id=" + id +
            ", staringDate='" + staringDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
