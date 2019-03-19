package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PatientOrderTest.
 */
@Entity
@Table(name = "patient_order_test")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientordertest")
public class PatientOrderTest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_order_test_id_seq")
    @SequenceGenerator(
        name="patient_order_test_id_seq",
        sequenceName="patient_order_test_id_seq",
        allocationSize=20
    )
    private Long id;

    @NotNull
    @Column(name = "staring_date", nullable = false)
    private LocalDate staringDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER)//, cascade = CascadeType.MERGE
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "icd_10_patient_order_test",
        joinColumns = @JoinColumn(name = "patient_order_tests_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "icd_10s_id", referencedColumnName = "ID"))
    private Set<Icd10> icd10s = new HashSet<>();

    @OneToMany(mappedBy = "patientOrderTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OrderBy("createdDate ASC")
    private Set<PatientOrderItem> patientOrderItems = new HashSet<>();

    @ManyToOne
    private LabCompendium labCompendium;

    @Column(name = "patient_order_id", insertable = false, updatable = false)
    private Long patientOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_order_id", referencedColumnName = "id")
    private PatientOrder patientOrder;

    @ManyToOne
    private OrderFrequency orderFrequency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStaringDate() {
        return staringDate;
    }

    public void setStaringDate(LocalDate staringDate) {
        this.staringDate = staringDate;
    }

    public PatientOrderTest staringDate(LocalDate staringDate) {
        this.staringDate = staringDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PatientOrderTest endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Set<Icd10> getIcd10s() {
        return icd10s;
    }

    public void setIcd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
    }

    public PatientOrderTest icd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
        return this;
    }

    public PatientOrderTest addIcd10(Icd10 diagnosis) {
        icd10s.add(diagnosis);
        diagnosis.getPatientOrderTests().add(this);
        return this;
    }

    public PatientOrderTest removeIcd10(Icd10 icd10) {
        icd10s.remove(icd10);
        icd10.getPatientOrderTests().remove(this);
        return this;
    }

    public Set<PatientOrderItem> getPatientOrderItems() {
        return patientOrderItems;
    }

    public void setPatientOrderItems(Set<PatientOrderItem> patientOrderItems) {
        this.patientOrderItems = patientOrderItems;
    }

    public PatientOrderTest patientOrderItems(Set<PatientOrderItem> patientOrderItems) {
        this.patientOrderItems = patientOrderItems;
        return this;
    }

    public PatientOrderTest addPatientOrderItem(PatientOrderItem patientOrderItem) {
        patientOrderItems.add(patientOrderItem);
        patientOrderItem.setPatientOrderTest(this);
        return this;
    }

    public PatientOrderTest removePatientOrderItem(PatientOrderItem patientOrderItem) {
        patientOrderItems.remove(patientOrderItem);
        patientOrderItem.setPatientOrderTest(null);
        return this;
    }

    public LabCompendium getLabCompendium() {
        return labCompendium;
    }

    public void setLabCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
    }

    public PatientOrderTest labCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
        return this;
    }

    @JsonIgnore
    public PatientOrder getPatientOrder() {
        return patientOrder;
    }

    public void setPatientOrder(PatientOrder patientOrder) {
        this.patientOrder = patientOrder;
    }

    public PatientOrderTest patientOrder(PatientOrder patientOrder) {
        this.patientOrder = patientOrder;
        return this;
    }

    public OrderFrequency getOrderFrequency() {
        return orderFrequency;
    }

    public void setOrderFrequency(OrderFrequency orderFrequency) {
        this.orderFrequency = orderFrequency;
    }

    public PatientOrderTest orderFrequency(OrderFrequency orderFrequency) {
        this.orderFrequency = orderFrequency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientOrderTest patientOrderTest = (PatientOrderTest) o;
        if (patientOrderTest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientOrderTest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientOrderTest{" +
            "id=" + id +
            ", staringDate='" + staringDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }

    public Long getPatientOrderId() {
        return patientOrderId;
    }

    public void setPatientOrderId(Long patientOrderId) {
        this.patientOrderId = patientOrderId;
    }
}
