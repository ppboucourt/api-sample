package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bed.
 */
@Entity
@Table(name = "bed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bed")
public class Bed extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //    @Id
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bed_id_seq")
    //    @SequenceGenerator(
    //        name = "bed_id_seq",
    //        sequenceName = "bed_id_seq",
    //        allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @Column(name = "actual_chart")
    private Long actualChart;

    @Column(name = "name")
    private String name;

    @Size(max = 800)
    @Column(name = "notes", length = 800)
    private String notes;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Facility facility;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bed")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Chart> charts = new HashSet<>();

//    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @Transient
    private Chart activeChart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Bed status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActualChart() {
        return actualChart;
    }

    public Bed actualChart(Long actualChart) {
        this.actualChart = actualChart;
        return this;
    }

    public void setActualChart(Long actualChart) {
        this.actualChart = actualChart;
    }

    public String getName() {
        return name;
    }

    public Bed name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public Bed notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Room getRoom() {
        return room;
    }

    public Bed room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Facility getFacility() {
        return facility;
    }

    public Bed facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Chart getActiveChart() {
        return activeChart;
    }

    public void setActiveChart(Chart activeChart) {
        this.activeChart = activeChart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bed bed = (Bed) o;
        if (bed.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bed{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", actualChart='" + actualChart + "'" +
            ", name='" + name + "'" +
            ", notes='" + notes + "'" +
            '}';
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
