package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Diet.
 */
@Entity
@Table(name = "diet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "diet")
public class Diet extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @JoinColumn(name = "chart_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @ManyToOne
    private TypeFoodDiets typeFoodDiets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Diet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Diet status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public Diet chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public TypeFoodDiets getTypeFoodDiets() {
        return typeFoodDiets;
    }

    public Diet typeFoodDiets(TypeFoodDiets typeFoodDiets) {
        this.typeFoodDiets = typeFoodDiets;
        return this;
    }

    public void setTypeFoodDiets(TypeFoodDiets typeFoodDiets) {
        this.typeFoodDiets = typeFoodDiets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diet diet = (Diet) o;
        if(diet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, diet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Diet{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
