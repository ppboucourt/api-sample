package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChartToMedications.
 */
@Entity
@Table(name = "chart_to_medications")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttomedications")
public class ChartToMedications extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private String status;

    @Column(name = "taken")
    private Boolean taken;

    @Column(name = "date_prescription")
    private ZonedDateTime datePrescription;

    @ManyToOne
    private Chart chart;

    @ManyToOne
    private Medications medication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public ChartToMedications notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public ChartToMedications status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isTaken() {
        return taken;
    }

    public ChartToMedications taken(Boolean taken) {
        this.taken = taken;
        return this;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public ZonedDateTime getDatePrescription() {
        return datePrescription;
    }

    public ChartToMedications datePrescription(ZonedDateTime datePrescription) {
        this.datePrescription = datePrescription;
        return this;
    }

    public void setDatePrescription(ZonedDateTime datePrescription) {
        this.datePrescription = datePrescription;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToMedications chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Medications getMedication() {
        return medication;
    }

    public ChartToMedications medication(Medications medications) {
        this.medication = medications;
        return this;
    }

    public void setMedication(Medications medications) {
        this.medication = medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToMedications chartToMedications = (ChartToMedications) o;
        if(chartToMedications.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToMedications.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToMedications{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            ", taken='" + taken + "'" +
            ", datePrescription='" + datePrescription + "'" +
            '}';
    }
}
