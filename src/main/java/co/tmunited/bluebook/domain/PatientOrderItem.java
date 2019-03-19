package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PatientOrderItem.
 */
@Entity
@Table(name = "patient_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientorderitem")
public class PatientOrderItem extends AbstractAuditingEntity implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_order_item_id_seq")
    @SequenceGenerator(
        name="patient_order_item_id_seq",
        sequenceName="patient_order_item_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "group_number_id")
    private String groupNumberId;

    @Column(name = "collected")
    private Boolean collected = false;

    @Column(name = "collected_date")
    private LocalDate collectedDate;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "sent")
    private Boolean sent = false;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "canceled")
    private Boolean canceled = false;

    @Column(name = "patient_order_test_id", insertable = false, updatable = false)
    private Long patientOrderTestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_order_test_id", referencedColumnName = "id")
    private PatientOrderTest patientOrderTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNumberId() {
        return groupNumberId;
    }

    public void setGroupNumberId(String groupNumberId) {
        this.groupNumberId = groupNumberId;
    }

    public PatientOrderItem groupNumberId(String groupNumberId) {
        this.groupNumberId = groupNumberId;
        return this;
    }

    public void setBarCode(BarCode barCode) {
        this.groupNumberId = Constants.BAR_CODE + StringUtils.leftPad("" + barCode.getId(), 9, "0");
    }

    public Boolean isCollected() {
        return collected;
    }

    public PatientOrderItem collected(Boolean collected) {
        this.collected = collected;
        return this;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public LocalDate getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(LocalDate collectedDate) {
        this.collectedDate = collectedDate;
    }

    public PatientOrderItem collectedDate(LocalDate collectedDate) {
        this.collectedDate = collectedDate;
        return this;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public PatientOrderItem scheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public Boolean isSent() {
        return sent;
    }

    public PatientOrderItem sent(Boolean sent) {
        this.sent = sent;
        return this;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public PatientOrderItem sentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public PatientOrderItem canceled(Boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public PatientOrderTest getPatientOrderTest() {
        return patientOrderTest;
    }

    public void setPatientOrderTest(PatientOrderTest patientOrderTest) {
        this.patientOrderTest = patientOrderTest;
    }

    public PatientOrderItem patientOrderTest(PatientOrderTest patientOrderTest) {
        this.patientOrderTest = patientOrderTest;
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
        PatientOrderItem patientOrderItem = (PatientOrderItem) o;
        if (patientOrderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientOrderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientOrderItem{" +
            "id=" + id +
            ", groupNumberId='" + groupNumberId + "'" +
            ", collected='" + collected + "'" +
            ", collectedDate='" + collectedDate + "'" +
            ", scheduleDate='" + scheduleDate + "'" +
            ", sent='" + sent + "'" +
            ", sentDate='" + sentDate + "'" +
            ", canceled='" + canceled + "'" +
            '}';
    }

    public Long getPatientOrderTestId() {
        return patientOrderTestId;
    }

    public void setPatientOrderTestId(Long patientOrderTestId) {
        this.patientOrderTestId = patientOrderTestId;
    }

    @Override
    public int compareTo(Object o) {
        return this.getScheduleDate().compareTo(((PatientOrderItem) o).getScheduleDate());
    }
}
