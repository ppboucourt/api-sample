package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReportDetails.
 */
@Entity
@Table(name = "report_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reportdetails")
public class ReportDetails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sorting")
    private String sorting;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @ManyToOne
    private Reports report;

    @ManyToOne
    private Tables table;

    @ManyToOne
    private Fields field;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSorting() {
        return sorting;
    }

    public ReportDetails sorting(String sorting) {
        this.sorting = sorting;
        return this;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public String getStatus() {
        return status;
    }

    public ReportDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reports getReport() {
        return report;
    }

    public ReportDetails report(Reports reports) {
        this.report = reports;
        return this;
    }

    public void setReport(Reports reports) {
        this.report = reports;
    }

    public Tables getTable() {
        return table;
    }

    public ReportDetails table(Tables tables) {
        this.table = tables;
        return this;
    }

    public void setTable(Tables tables) {
        this.table = tables;
    }

    public Fields getField() {
        return field;
    }

    public ReportDetails field(Fields fields) {
        this.field = fields;
        return this;
    }

    public void setField(Fields fields) {
        this.field = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportDetails reportDetails = (ReportDetails) o;
        if(reportDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reportDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReportDetails{" +
            "id=" + id +
            ", sorting='" + sorting + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
