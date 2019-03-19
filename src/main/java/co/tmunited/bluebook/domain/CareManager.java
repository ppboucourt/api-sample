package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareManager.
 */
@Entity
@Table(name = "care_manager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "caremanager")
public class CareManager extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "insurance_company")
    private String insurance_company;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public CareManager full_name(String full_name) {
        this.full_name = full_name;
        return this;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public CareManager phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInsurance_company() {
        return insurance_company;
    }

    public CareManager insurance_company(String insurance_company) {
        this.insurance_company = insurance_company;
        return this;
    }

    public void setInsurance_company(String insurance_company) {
        this.insurance_company = insurance_company;
    }

    public String getNotes() {
        return notes;
    }

    public CareManager notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public CareManager status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public CareManager chart(Chart chart) {
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
        CareManager careManager = (CareManager) o;
        if(careManager.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careManager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareManager{" +
            "id=" + id +
            ", full_name='" + full_name + "'" +
            ", phone='" + phone + "'" +
            ", insurance_company='" + insurance_company + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
