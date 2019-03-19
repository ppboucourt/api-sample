package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PatientResult.
 */
@Entity
@Table(name = "patient_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientresult")
public class PatientResult extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_result_id_seq")
    @SequenceGenerator(
        name="patient_result_id_seq",
        sequenceName="patient_result_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "account")
    private String account;

    @Column(name = "status")
    private String status;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

    @Column(name = "reviewed_on")
    private LocalDate reviewedOn;

    @Column(name = "accession_number")
    private String accessionNumber;

    @Column(name = "abnormal")
    private Boolean abnormal;

    @Column(name = "send_by")
    private String sendBy;

    @Column(name = "reviewed_status")
    private Boolean reviewedStatus;

    @OneToMany(mappedBy = "patientResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientResultDet> resultDets = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PatientOrder order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee reviewedBy;

    @OneToMany(mappedBy = "patientResult", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OrderBy("createdDate DESC")
    private Set<PatientResultFile> patientResultFiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public PatientResult patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public PatientResult dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PatientResult status(String status) {
        this.status = status;
        return this;
    }

    public LocalDate getcollectionDate() {
        return collectionDate;
    }

    public PatientResult collectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
        return this;
    }

    public void setcollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    public LocalDate getReviewedOn() {
        return reviewedOn;
    }

    public void setReviewedOn(LocalDate reviewedOn) {
        this.reviewedOn = reviewedOn;
    }

    public PatientResult reviewedOn(LocalDate reviewedOn) {
        this.reviewedOn = reviewedOn;
        return this;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public PatientResult accessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
        return this;
    }

    public Boolean isAbnormal() {
        return abnormal;
    }

    public PatientResult abnormal(Boolean abnormal) {
        this.abnormal = abnormal;
        return this;
    }

    public void setAbnormal(Boolean abnormal) {
        this.abnormal = abnormal;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public PatientResult sendBy(String sendBy) {
        this.sendBy = sendBy;
        return this;
    }

    public Boolean isReviewedStatus() {
        return reviewedStatus;
    }

    public PatientResult reviewedStatus(Boolean reviewedStatus) {
        this.reviewedStatus = reviewedStatus;
        return this;
    }

    public void setReviewedStatus(Boolean reviewedStatus) {
        this.reviewedStatus = reviewedStatus;
    }

    public Set<PatientResultDet> getResultDets() {
        return resultDets;
    }

    public void setResultDets(Set<PatientResultDet> patientResultDets) {
        this.resultDets = patientResultDets;
    }

    public PatientResult resultDets(Set<PatientResultDet> patientResultDets) {
        this.resultDets = patientResultDets;
        return this;
    }

    public PatientResult addResultDet(PatientResultDet patientResultDet) {
        resultDets.add(patientResultDet);
        patientResultDet.setPatientResult(this);
        return this;
    }

    public PatientResult removeResultDet(PatientResultDet patientResultDet) {
        resultDets.remove(patientResultDet);
        patientResultDet.setPatientResult(null);
        return this;
    }

    public PatientOrder getOrder() {
        return order;
    }

    public void setOrder(PatientOrder patientOrder) {
        this.order = patientOrder;
    }

    public PatientResult order(PatientOrder patientOrder) {
        this.order = patientOrder;
        return this;
    }

    public Employee getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Employee employee) {
        this.reviewedBy = employee;
    }

    public PatientResult reviewedBy(Employee employee) {
        this.reviewedBy = employee;
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
        PatientResult patientResult = (PatientResult) o;
        if (patientResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientResult{" +
            "id=" + id +
            ", patientName='" + patientName + "'" +
            ", dob='" + dob + "'" +
            ", status='" + status + "'" +
            ", collectionDate='" + collectionDate + "'" +
            ", reviewedOn='" + reviewedOn + "'" +
            ", accessionNumber='" + accessionNumber + "'" +
            ", abnormal='" + abnormal + "'" +
            ", sendBy='" + sendBy + "'" +
            ", reviewedStatus='" + reviewedStatus + "'" +
            '}';
    }

    public Set<PatientResultFile> getPatientResultFiles() {
        return patientResultFiles;
    }

    public void setPatientResultFiles(Set<PatientResultFile> patientResultFiles) {
        this.patientResultFiles = patientResultFiles;
    }
}
