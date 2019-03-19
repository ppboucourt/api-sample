package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ChartToGroupSession.
 */
@Entity
@Table(name = "chart_to_group_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttogroupsession")
public class ChartToGroupSession extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @ManyToOne
    private GroupSession groupSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public ChartToGroupSession status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToGroupSession chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public GroupSession getGroupSession() {
        return groupSession;
    }

    public ChartToGroupSession groupSession(GroupSession groupSession) {
        this.groupSession = groupSession;
        return this;
    }

    public void setGroupSession(GroupSession groupSession) {
        this.groupSession = groupSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToGroupSession chartToGroupSession = (ChartToGroupSession) o;
        if(chartToGroupSession.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToGroupSession.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToGroupSession{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
