package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ChartToIcd10.
 */
@Entity
@Table(name = "chart_to_icd_10")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttoicd10")
public class ChartToIcd10 extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Chart chart;

    @ManyToOne
    private Icd10 icd10;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public ChartToIcd10 status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToIcd10 chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Icd10 getIcd10() {
        return icd10;
    }

    public ChartToIcd10 icd10(Icd10 icd10) {
        this.icd10 = icd10;
        return this;
    }

    public void setIcd10(Icd10 icd10) {
        this.icd10 = icd10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToIcd10 chartToIcd10 = (ChartToIcd10) o;
        if(chartToIcd10.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToIcd10.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToIcd10{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
