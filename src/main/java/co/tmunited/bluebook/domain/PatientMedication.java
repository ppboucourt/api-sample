package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.PatientMedicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * A PatientMedication.
 */
@Entity
@Table(name = "patient_medication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientmedication")
public class PatientMedication extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_medication_id_seq")
    @SequenceGenerator(
        name="patient_medication_id_seq",
        sequenceName="patient_medication_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "signature_date")
    private LocalDate signatureDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "medication_status")
    private PatientMedicationStatus medicationStatus;

    @Column(name = "signed")
    private Boolean signed;

    @OneToMany(mappedBy = "patientMedication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdDate ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientMedicationPres> patientMedicationPress = new HashSet<>();

    @ManyToOne
    private Via via;

//    @ManyToOne

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "chart_id", referencedColumnName = "id")
    private Chart chart;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee signedBy;

    @OneToOne(fetch = FetchType.LAZY)
    private Routes route;

    @Column(name = "justification")
    private String justification;

    @Column(name = "warning")
    private String warning;

    @Column(name = "refills")
    private String refills;

    @Formula("(SELECT Min(patient_medication_take.schedule_date) " +
        "FROM patient_medication " +
        "INNER JOIN patient_medication_pres ON patient_medication_pres.patient_medication_id = patient_medication.id " +
        "INNER JOIN patient_medication_take ON patient_medication_take.patient_medication_pres_id = patient_medication_pres.id " +
        "WHERE patient_medication.id=id)"
    )
    private LocalDate startDate;

    @Formula("(SELECT Max(patient_medication_take.schedule_date) " +
        "FROM patient_medication " +
        "INNER JOIN patient_medication_pres ON patient_medication_pres.patient_medication_id = patient_medication.id " +
        "INNER JOIN patient_medication_take ON patient_medication_take.patient_medication_pres_id = patient_medication_pres.id " +
        "WHERE patient_medication.id=id)"
    )
    private LocalDate endDate;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public PatientMedication signatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
        return this;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public PatientMedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    public PatientMedication medicationStatus(PatientMedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
        return this;
    }

    public void setMedicationStatus(PatientMedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public Boolean isSigned() {
        return signed;
    }

    public PatientMedication signed(Boolean signed) {
        this.signed = signed;
        return this;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PatientMedication endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<PatientMedicationPres> getPatientMedicationPress() {
        return patientMedicationPress;
    }

    public PatientMedication patientMedicationPress(Set<PatientMedicationPres> patientMedicationPres) {
        this.patientMedicationPress = patientMedicationPres;
        return this;
    }

    public PatientMedication addPatientMedicationPress(PatientMedicationPres patientMedicationPres) {
        patientMedicationPress.add(patientMedicationPres);
        patientMedicationPres.setPatientMedication(this);
        return this;
    }

    public PatientMedication removePatientMedicationPress(PatientMedicationPres patientMedicationPres) {
        patientMedicationPress.remove(patientMedicationPres);
        patientMedicationPres.setPatientMedication(null);
        return this;
    }

    public void setPatientMedicationPress(Set<PatientMedicationPres> patientMedicationPress) {
        this.patientMedicationPress = patientMedicationPress;
    }

    public Via getVia() {
        return via;
    }

    public PatientMedication via(Via via) {
        this.via = via;
        return this;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    public Chart getChart() {
        return chart;
    }

    public PatientMedication chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Employee getEmployee() {
        return employee;
    }

    public PatientMedication employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getSignedBy() {
        return signedBy;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
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

    public String getRefills() {
        return refills;
    }

    public void setRefills(String refills) {
        this.refills = refills;
    }

    public PatientMedication signedBy(Employee employee) {
        this.signedBy = employee;
        return this;

    }

    public void setSignedBy(Employee employee) {
        this.signedBy = employee;
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

    public String getDiscountCause() {
        return discountCause;
    }

    public void setDiscountCause(String discountCause) {
        this.discountCause = discountCause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientMedication patientMedication = (PatientMedication) o;
        if(patientMedication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientMedication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientMedication{" +
            "id=" + id +
            ", signatureDate='" + signatureDate + "'" +
            ", medicationStatus='" + medicationStatus + "'" +
            ", signed='" + signed + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
