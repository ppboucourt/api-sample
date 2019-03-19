package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.OrderStatus;
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
 * A PatientOrder.
 */
@Entity
@Table(name = "patient_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientorder")
public class PatientOrder extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_order_id_seq")
    @SequenceGenerator(
        name="patient_order_id_seq",
        sequenceName="patient_order_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "signature_date")
    private LocalDate signatureDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus ordStatus;

    @Column(name = "signed")
    private Boolean signed = false;

    @OneToMany(mappedBy = "patientOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("createdDate ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientOrderTest> patientOrderTests = new HashSet<>();

    @ManyToOne
    private Via via;

    @JoinColumn(name = "chart_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Employee employee;

    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signed_by_id", insertable = false, updatable = false)
    private Employee signedBy;

    @Column(name = "signed_by_id")
    private Long signedById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "discount_by_id", insertable = false, updatable = false)
    private Employee discountBy;

    @Column(name = "discount_by_id")
    private Long discountById;

    @OneToOne(fetch = FetchType.LAZY)
    private Via discountVia;

    private String discountCause;

    @Formula("(SELECT Min(patient_order_item.schedule_date) " +
        "FROM patient_order " +
        "INNER JOIN patient_order_test ON patient_order_test.patient_order_id = patient_order.id " +
        "INNER JOIN patient_order_item ON patient_order_item.patient_order_test_id = patient_order_test.id " +
        "WHERE patient_order.id=id)"
    )
    private LocalDate startDate;

    @Formula("(SELECT Max(patient_order_item.schedule_date) " +
        "FROM patient_order " +
        "INNER JOIN patient_order_test ON patient_order_test.patient_order_id = patient_order.id " +
        "INNER JOIN patient_order_item ON patient_order_item.patient_order_test_id = patient_order_test.id " +
        "WHERE patient_order.id=id)"
    )
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public PatientOrder signatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
        return this;
    }

    public OrderStatus getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(OrderStatus ordStatus) {
        this.ordStatus = ordStatus;
    }

    public Boolean isSigned() {
        return signed;
    }

    public PatientOrder signed(Boolean signed) {
        this.signed = signed;
        return this;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Set<PatientOrderTest> getPatientOrderTests() {
        return patientOrderTests;
    }

    public void setPatientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
    }

    public PatientOrder patientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
        return this;
    }

    public PatientOrder addPatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.add(patientOrderTest);
        patientOrderTest.setPatientOrder(this);
        return this;
    }

    public PatientOrder removePatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.remove(patientOrderTest);
        patientOrderTest.setPatientOrder(null);
        return this;
    }

    public Via getVia() {
        return via;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    public PatientOrder via(Via via) {
        this.via = via;
        return this;
    }

    //    @JsonIgnore
//    @JsonBackReference
    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public PatientOrder chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public Long getDiscountById() {
        return discountById;
    }

    public void setDiscountById(Long discountById) {
        this.discountById = discountById;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PatientOrder employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Employee getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(Employee employee) {
        this.signedBy = employee;
    }

    public PatientOrder signedBy(Employee employee) {
        this.signedBy = employee;
        return this;
    }

    public Long getSignedById() {
        return signedById;
    }

    public void setSignedById(Long signedById) {
        this.signedById = signedById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientOrder patientOrder = (PatientOrder) o;
        if (patientOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientOrder{" +
            "id=" + id +
            ", signatureDate='" + signatureDate + "'" +
            ", signed='" + signed + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Employee getDiscountBy() {
        return discountBy;
    }

    public void setDiscountBy(Employee discountBy) {
        this.discountBy = discountBy;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public PatientOrder updateStartEndDate(){
        getPatientOrderTests().stream().forEach(test ->
            test.getPatientOrderItems().stream().forEach(item -> {
                if (getEndDate() == null || item.getScheduleDate().isAfter(getEndDate())) {
                    setEndDate(item.getScheduleDate());
                }

                if (getStartDate() == null || item.getScheduleDate().isBefore(getStartDate())) {
                    setStartDate(item.getScheduleDate());
                }
            })
        );

        return this;
    }
}
