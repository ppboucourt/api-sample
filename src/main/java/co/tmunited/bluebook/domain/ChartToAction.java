package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChartToAction.
 */
@Entity
@Table(name = "chart_to_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttoaction")
public class ChartToAction extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "notes")
    private String notes;

    @Column(name = "archived")
    private String archived;

    @Column(name = "date_prescription")
    private ZonedDateTime date_prescription;

    @Column(name = "taken")
    private String taken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "chart_id", referencedColumnName="id")
    private Chart chart;

    @ManyToOne
    private Actions actions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public ChartToAction status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public ChartToAction notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getArchived() {
        return archived;
    }

    public ChartToAction archived(String archived) {
        this.archived = archived;
        return this;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public ZonedDateTime getDate_prescription() {
        return date_prescription;
    }

    public ChartToAction date_prescription(ZonedDateTime date_prescription) {
        this.date_prescription = date_prescription;
        return this;
    }

    public void setDate_prescription(ZonedDateTime date_prescription) {
        this.date_prescription = date_prescription;
    }

    public String getTaken() {
        return taken;
    }

    public ChartToAction taken(String taken) {
        this.taken = taken;
        return this;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToAction chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Actions getActions() {
        return actions;
    }

    public ChartToAction actions(Actions actions) {
        this.actions = actions;
        return this;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToAction chartToAction = (ChartToAction) o;
        if(chartToAction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToAction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToAction{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", notes='" + notes + "'" +
            ", archived='" + archived + "'" +
            ", date_prescription='" + date_prescription + "'" +
            ", taken='" + taken + "'" +
            '}';
    }
}
