package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PatientToShift.
 */
@Entity
@Table(name = "patient_to_shift")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patienttoshift")
public class PatientToShift extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @ManyToOne
    private Shifts shifts;

    @ManyToOne
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public PatientToShift status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Shifts getShifts() {
        return shifts;
    }

    public PatientToShift shifts(Shifts shifts) {
        this.shifts = shifts;
        return this;
    }

    public void setShifts(Shifts shifts) {
        this.shifts = shifts;
    }

    public Chart getChart() {
        return chart;
    }

    public PatientToShift chart(Chart chart) {
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
        PatientToShift patientToShift = (PatientToShift) o;
        if(patientToShift.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientToShift.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientToShift{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
