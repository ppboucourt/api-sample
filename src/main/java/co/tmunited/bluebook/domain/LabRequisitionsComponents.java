package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LabRequisitionsComponents.
 */
@Entity
@Table(name = "lab_requisitions_components")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "labrequisitionscomponents")
public class LabRequisitionsComponents extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private LabRequisition labRequisition;

    @ManyToOne
    private LabCompendium labCompendium;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public LabRequisitionsComponents status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LabRequisition getLabRequisition() {
        return labRequisition;
    }

    public LabRequisitionsComponents labRequisition(LabRequisition labRequisition) {
        this.labRequisition = labRequisition;
        return this;
    }

    public void setLabRequisition(LabRequisition labRequisition) {
        this.labRequisition = labRequisition;
    }

    public LabCompendium getLabCompendium() {
        return labCompendium;
    }

    public LabRequisitionsComponents labCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
        return this;
    }

    public void setLabCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabRequisitionsComponents labRequisitionsComponents = (LabRequisitionsComponents) o;
        if(labRequisitionsComponents.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, labRequisitionsComponents.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LabRequisitionsComponents{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
