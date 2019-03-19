package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeTreatmentPlanStatuses.
 */
@Entity
@Table(name = "type_treatment_plan_statuses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typetreatmentplanstatuses")
public class TypeTreatmentPlanStatuses extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TypeTreatmentPlanStatuses name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public TypeTreatmentPlanStatuses status(String status) {
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
        TypeTreatmentPlanStatuses typeTreatmentPlanStatuses = (TypeTreatmentPlanStatuses) o;
        if(typeTreatmentPlanStatuses.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typeTreatmentPlanStatuses.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypeTreatmentPlanStatuses{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
