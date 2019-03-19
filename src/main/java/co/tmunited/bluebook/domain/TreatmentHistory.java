package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TreatmentHistory.
 */
@Entity
@Table(name = "treatment_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "treatmenthistory")
public class TreatmentHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "primary_therapist")
    private String primaryTherapist;

    @Column(name = "how_hear")
    private String howHear;

    @Column(name = "coordinator")
    private String coordinator;

    @ManyToOne
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TreatmentHistory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public TreatmentHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Chart getChart() {
        return chart;
    }

    public TreatmentHistory chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public String getPrimaryTherapist() {
        return primaryTherapist;
    }

    public void setPrimaryTherapist(String primaryTherapist) {
        this.primaryTherapist = primaryTherapist;
    }

    public String getHowHear() {
        return howHear;
    }

    public void setHowHear(String howHear) {
        this.howHear = howHear;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreatmentHistory treatmentHistory = (TreatmentHistory) o;
        if(treatmentHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, treatmentHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TreatmentHistory{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
