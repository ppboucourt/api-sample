package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeAdmissionStatus.
 */
@Entity
@Table(name = "type_admission_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typeadmissionstatus")
public class TypeAdmissionStatus extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "typeAdmissionStatus")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chart> charts = new HashSet<>();

    @ManyToOne
    private Facility facility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TypeAdmissionStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public TypeAdmissionStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public TypeAdmissionStatus charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public TypeAdmissionStatus addChart(Chart chart) {
        charts.add(chart);
        chart.setTypeAdmissionStatus(this);
        return this;
    }

    public TypeAdmissionStatus removeChart(Chart chart) {
        charts.remove(chart);
        chart.setTypeAdmissionStatus(null);
        return this;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }

    public Facility getFacility() {
        return facility;
    }

    public TypeAdmissionStatus facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeAdmissionStatus typeAdmissionStatus = (TypeAdmissionStatus) o;
        if(typeAdmissionStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typeAdmissionStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypeAdmissionStatus{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
