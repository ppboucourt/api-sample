package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChartToLevelCare.
 */
@Entity
@Table(name = "chart_to_level_care")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttolevelcare")
public class ChartToLevelCare extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_id", referencedColumnName="id")
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id", updatable = false, insertable = false)
    private Long chartId;

    @ManyToOne
    private TypeLevelCare typeLevelCare;

    @Formula("date_part('day', (now()-start_date))")
    private String daysLoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ChartToLevelCare startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public ChartToLevelCare endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToLevelCare chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public TypeLevelCare getTypeLevelCare() {
        return typeLevelCare;
    }

    public ChartToLevelCare typeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
        return this;
    }

    public void setTypeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
    }

    public String getDaysLoc() {
        return daysLoc;
    }

    public void setDaysLoc(String daysLoc) {
        this.daysLoc = daysLoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToLevelCare chartToLevelCare = (ChartToLevelCare) o;
        if(chartToLevelCare.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToLevelCare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToLevelCare{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
