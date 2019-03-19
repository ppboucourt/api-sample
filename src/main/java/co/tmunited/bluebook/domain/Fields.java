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
 * A Fields.
 */
@Entity
@Table(name = "fields")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fields")
public class Fields extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @Column(name = "field_name")
    private String field_name;

    @ManyToOne
    private Tables table;

    @OneToMany(mappedBy = "field")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportDetails> reportDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Fields status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getField_name() {
        return field_name;
    }

    public Fields field_name(String field_name) {
        this.field_name = field_name;
        return this;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public Tables getTable() {
        return table;
    }

    public Fields table(Tables tables) {
        this.table = tables;
        return this;
    }

    public void setTable(Tables tables) {
        this.table = tables;
    }

    public Set<ReportDetails> getReportDetails() {
        return reportDetails;
    }

    public Fields reportDetails(Set<ReportDetails> reportDetails) {
        this.reportDetails = reportDetails;
        return this;
    }

//    public Fields addReportDetails(ReportDetails reportDetails) {
//        reportDetails.add(reportDetails);
//        reportDetails.setField(this);
//        return this;
//    }
//
//    public Fields removeReportDetails(ReportDetails reportDetails) {
//        reportDetails.remove(reportDetails);
//        reportDetails.setField(null);
//        return this;
//    }

    public void setReportDetails(Set<ReportDetails> reportDetails) {
        this.reportDetails = reportDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fields fields = (Fields) o;
        if(fields.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fields.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fields{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", field_name='" + field_name + "'" +
            '}';
    }
}
