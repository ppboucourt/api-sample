package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reports.
 */
@Entity
@Table(name = "reports")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reports")
public class Reports extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "report_name")
    private String report_name;

    @Column(name = "template")
    private String template;

    @Column(name = "sort_direction")
    private String sort_direction;

    @Column(name = "date_range")
    private String date_range;

    @Column(name = "start_date")
    private ZonedDateTime start_date;

    @Column(name = "end_date")
    private ZonedDateTime end_date;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "selection")
    private String selection;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReport_name() {
        return report_name;
    }

    public Reports report_name(String report_name) {
        this.report_name = report_name;
        return this;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getTemplate() {
        return template;
    }

    public Reports template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSort_direction() {
        return sort_direction;
    }

    public Reports sort_direction(String sort_direction) {
        this.sort_direction = sort_direction;
        return this;
    }

    public void setSort_direction(String sort_direction) {
        this.sort_direction = sort_direction;
    }

    public String getDate_range() {
        return date_range;
    }

    public Reports date_range(String date_range) {
        this.date_range = date_range;
        return this;
    }

    public void setDate_range(String date_range) {
        this.date_range = date_range;
    }

    public ZonedDateTime getStart_date() {
        return start_date;
    }

    public Reports start_date(ZonedDateTime start_date) {
        this.start_date = start_date;
        return this;
    }

    public void setStart_date(ZonedDateTime start_date) {
        this.start_date = start_date;
    }

    public ZonedDateTime getEnd_date() {
        return end_date;
    }

    public Reports end_date(ZonedDateTime end_date) {
        this.end_date = end_date;
        return this;
    }

    public void setEnd_date(ZonedDateTime end_date) {
        this.end_date = end_date;
    }

    public String getCriteria() {
        return criteria;
    }

    public Reports criteria(String criteria) {
        this.criteria = criteria;
        return this;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getSelection() {
        return selection;
    }

    public Reports selection(String selection) {
        this.selection = selection;
        return this;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getStatus() {
        return status;
    }

    public Reports status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reports reports = (Reports) o;
        if(reports.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reports.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reports{" +
            "id=" + id +
            ", report_name='" + report_name + "'" +
            ", template='" + template + "'" +
            ", sort_direction='" + sort_direction + "'" +
            ", date_range='" + date_range + "'" +
            ", start_date='" + start_date + "'" +
            ", end_date='" + end_date + "'" +
            ", criteria='" + criteria + "'" +
            ", selection='" + selection + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
