package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import co.tmunited.bluebook.domain.enumeration.TypeOfCheckValues;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import co.tmunited.bluebook.domain.enumeration.TypeOfCheckValues;

/**
 * A Glucose.
 */
@Entity
@Table(name = "glucose")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "glucose")
public class Glucose extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "reading")
    private String reading;

    @Column(name = "type_check")
    private String type_check;

    @Column(name = "intervention")
    private String intervention;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_check")
    private TypeOfCheckValues typeOfCheck;

    @JoinColumn(name = "chart_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "glucose_glucose_intervention",
               joinColumns = @JoinColumn(name="glucoses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="glucose_interventions_id", referencedColumnName="ID"))
    private Set<GlucoseIntervention> glucoseInterventions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Glucose date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Glucose time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getReading() {
        return reading;
    }

    public Glucose reading(String reading) {
        this.reading = reading;
        return this;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getType_check() {
        return type_check;
    }

    public Glucose type_check(String type_check) {
        this.type_check = type_check;
        return this;
    }

    public void setType_check(String type_check) {
        this.type_check = type_check;
    }

    public String getIntervention() {
        return intervention;
    }

    public Glucose intervention(String intervention) {
        this.intervention = intervention;
        return this;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public String getNotes() {
        return notes;
    }

    public Glucose notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public Glucose status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TypeOfCheckValues getTypeOfCheck() {
        return typeOfCheck;
    }

    public Glucose typeOfCheck(TypeOfCheckValues typeOfCheck) {
        this.typeOfCheck = typeOfCheck;
        return this;
    }

    public void setTypeOfCheck(TypeOfCheckValues typeOfCheck) {
        this.typeOfCheck = typeOfCheck;
    }

    public Chart getChart() {
        return chart;
    }

    public Glucose chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Set<GlucoseIntervention> getGlucoseInterventions() {
        return glucoseInterventions;
    }

    public Glucose glucoseInterventions(Set<GlucoseIntervention> glucoseInterventions) {
        this.glucoseInterventions = glucoseInterventions;
        return this;
    }

    public Glucose addGlucoseIntervention(GlucoseIntervention glucoseIntervention) {
        glucoseInterventions.add(glucoseIntervention);
        glucoseIntervention.getGlucoses().add(this);
        return this;
    }

    public Glucose removeGlucoseIntervention(GlucoseIntervention glucoseIntervention) {
        glucoseInterventions.remove(glucoseIntervention);
        glucoseIntervention.getGlucoses().remove(this);
        return this;
    }

    public void setGlucoseInterventions(Set<GlucoseIntervention> glucoseInterventions) {
        this.glucoseInterventions = glucoseInterventions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Glucose glucose = (Glucose) o;
        if(glucose.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, glucose.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Glucose{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", time='" + time + "'" +
            ", reading='" + reading + "'" +
            ", type_check='" + type_check + "'" +
            ", intervention='" + intervention + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            ", typeOfCheck='" + typeOfCheck + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
