package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.PatientActionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PatientAction.
 */
@Entity
@Table(name = "patient_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientaction")
public class PatientAction extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_action_id_seq")
    @SequenceGenerator(
        name="patient_action_id_seq",
        sequenceName="patient_action_id_seq",
        allocationSize=1
    )
    private Long id;

    @Column(name = "signature_date")
    private LocalDate signatureDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status")
    private PatientActionStatus actionStatus;

    @Column(name = "signed")
    private Boolean signed;

    @OneToMany(mappedBy = "patientAction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createdDate ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientActionPre> patientActionPres = new HashSet<>();

    @ManyToOne
    private Via via;

    @ManyToOne
    private Chart chart;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee signedBy;

    @Formula("(SELECT Min(patient_action_take.schedule_date) " +
        "FROM patient_action " +
        "INNER JOIN patient_action_pre ON patient_action_pre.patient_action_id = patient_action.id " +
        "INNER JOIN patient_action_take ON patient_action_take.patient_action_pre_id = patient_action_pre.id " +
        "WHERE patient_action.id=id)"
    )
    private LocalDate startDate;

    @Formula("(SELECT Max(patient_action_take.schedule_date) " +
        "FROM patient_action " +
        "INNER JOIN patient_action_pre ON patient_action_pre.patient_action_id = patient_action.id " +
        "INNER JOIN patient_action_take ON patient_action_take.patient_action_pre_id = patient_action_pre.id " +
        "WHERE patient_action.id=id)"
    )
    private LocalDate endDate;


    @Column(name = "justification")
    private String justification;

    @Column(name = "warning")
    private String warning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "discount_by_id", insertable = false, updatable = false)
    private Employee discountBy;

    @Column(name = "discount_by_id")
    private Long discountById;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_via_id", insertable = false, updatable = false)
    private Via discountVia;

    @Column(name = "discount_via_id")
    private Long discountViaId;

    private String discountCause;

    public Boolean getSigned() {
        return signed;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Employee getDiscountBy() {
        return discountBy;
    }

    public void setDiscountBy(Employee discountBy) {
        this.discountBy = discountBy;
    }

    public Long getDiscountById() {
        return discountById;
    }

    public void setDiscountById(Long discountById) {
        this.discountById = discountById;
    }

    public Via getDiscountVia() {
        return discountVia;
    }

    public void setDiscountVia(Via discountVia) {
        this.discountVia = discountVia;
    }

    public Long getDiscountViaId() {
        return discountViaId;
    }

    public void setDiscountViaId(Long discountViaId) {
        this.discountViaId = discountViaId;
    }

    public String getDiscountCause() {
        return discountCause;
    }

    public void setDiscountCause(String discountCause) {
        this.discountCause = discountCause;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public PatientAction signatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
        return this;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public PatientActionStatus getActionStatus() {
        return actionStatus;
    }

    public PatientAction actionStatus(PatientActionStatus actionStatus) {
        this.actionStatus = actionStatus;
        return this;
    }

    public void setActionStatus(PatientActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Boolean isSigned() {
        return signed;
    }

    public PatientAction signed(Boolean signed) {
        this.signed = signed;
        return this;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PatientAction endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<PatientActionPre> getPatientActionPres() {
        return patientActionPres;
    }

    public PatientAction patientActionPres(Set<PatientActionPre> patientActionPres) {
        this.patientActionPres = patientActionPres;
        return this;
    }

    public PatientAction addPatientActionPre(PatientActionPre patientActionPre) {
        patientActionPres.add(patientActionPre);
        patientActionPre.setPatientAction(this);
        return this;
    }

    public PatientAction removePatientActionPre(PatientActionPre patientActionPre) {
        patientActionPres.remove(patientActionPre);
        patientActionPre.setPatientAction(null);
        return this;
    }

    public void setPatientActionPres(Set<PatientActionPre> patientActionPres) {
        this.patientActionPres = patientActionPres;
    }

    public Via getVia() {
        return via;
    }

    public PatientAction via(Via via) {
        this.via = via;
        return this;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    public Chart getChart() {
        return chart;
    }

    public PatientAction chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Employee getEmployee() {
        return employee;
    }

    public PatientAction employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getSignedBy() {
        return signedBy;
    }

    public PatientAction signedBy(Employee employee) {
        this.signedBy = employee;
        return this;
    }

    public void setSignedBy(Employee employee) {
        this.signedBy = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientAction patientAction = (PatientAction) o;
        if(patientAction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientAction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientAction{" +
            "id=" + id +
            ", signatureDate='" + signatureDate + "'" +
            ", actionStatus='" + actionStatus + "'" +
            ", signed='" + signed + "'" +
            '}';
    }
}
