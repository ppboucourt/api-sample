package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Hospitalization.
 */
@Entity
@Table(name = "hospitalization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hospitalization")
public class Hospitalization extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "admission_diagnosis")
    private String admissionDiagnosis;

    @Column(name = "admission_date")
    private ZonedDateTime admissionDate;

    @Column(name = "hospital")
    private String hospital;

    @Column(name = "admitting_physician")
    private String admittingPhysician;

    @Column(name = "discharge_date")
    private ZonedDateTime dischargeDate;

    @Column(name = "discharge_to")
    private String dischargeTo;

    @Size(max = 800)
    @Column(name = "reason", length = 800)
    private String reason;

    @ManyToOne
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdmissionDiagnosis() {
        return admissionDiagnosis;
    }

    public Hospitalization admissionDiagnosis(String admissionDiagnosis) {
        this.admissionDiagnosis = admissionDiagnosis;
        return this;
    }

    public void setAdmissionDiagnosis(String admissionDiagnosis) {
        this.admissionDiagnosis = admissionDiagnosis;
    }

    public ZonedDateTime getAdmissionDate() {
        return admissionDate;
    }

    public Hospitalization admissionDate(ZonedDateTime admissionDate) {
        this.admissionDate = admissionDate;
        return this;
    }

    public void setAdmissionDate(ZonedDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getHospital() {
        return hospital;
    }

    public Hospitalization hospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAdmittingPhysician() {
        return admittingPhysician;
    }

    public Hospitalization admittingPhysician(String admittingPhysician) {
        this.admittingPhysician = admittingPhysician;
        return this;
    }

    public void setAdmittingPhysician(String admittingPhysician) {
        this.admittingPhysician = admittingPhysician;
    }

    public ZonedDateTime getDischargeDate() {
        return dischargeDate;
    }

    public Hospitalization dischargeDate(ZonedDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
        return this;
    }

    public void setDischargeDate(ZonedDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getDischargeTo() {
        return dischargeTo;
    }

    public Hospitalization dischargeTo(String dischargeTo) {
        this.dischargeTo = dischargeTo;
        return this;
    }

    public void setDischargeTo(String dischargeTo) {
        this.dischargeTo = dischargeTo;
    }

    public String getReason() {
        return reason;
    }

    public Hospitalization reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Chart getChart() {
        return chart;
    }

    public Hospitalization chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hospitalization hospitalization = (Hospitalization) o;
        if(hospitalization.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hospitalization.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hospitalization{" +
            "id=" + id +
            ", admissionDiagnosis='" + admissionDiagnosis + "'" +
            ", admissionDate='" + admissionDate + "'" +
            ", hospital='" + hospital + "'" +
            ", admittingPhysician='" + admittingPhysician + "'" +
            ", dischargeDate='" + dischargeDate + "'" +
            ", dischargeTo='" + dischargeTo + "'" +
            ", reason='" + reason + "'" +
            '}';
    }
}
