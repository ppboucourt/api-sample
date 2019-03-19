package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GroupSessionDetailsChart.
 */
@Entity
@Table(name = "group_session_details_chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "groupsessiondetailschart")
public class GroupSessionDetailsChart extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 3000)
    @Column(name = "notes", length = 3000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupSessionDetails groupSessionDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public GroupSessionDetailsChart notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public GroupSessionDetails getGroupSessionDetails() {
        return groupSessionDetails;
    }

    public GroupSessionDetailsChart groupSessionDetails(GroupSessionDetails groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
        return this;
    }

    public void setGroupSessionDetails(GroupSessionDetails groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
    }

    public Chart getChart() {
        return chart;
    }

    public GroupSessionDetailsChart chart(Chart chart) {
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
        GroupSessionDetailsChart groupSessionDetailsChart = (GroupSessionDetailsChart) o;
        if(groupSessionDetailsChart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupSessionDetailsChart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupSessionDetailsChart{" +
            "id=" + id +
            ", notes='" + notes + "'" +
            '}';
    }
}
