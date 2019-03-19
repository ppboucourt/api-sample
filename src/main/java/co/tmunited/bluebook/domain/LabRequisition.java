package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LabRequisition.
 */
@Entity
@Table(name = "lab_requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "labrequisition")
public class LabRequisition extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "patient_signature")
    private String patient_signature;

    @Column(name = "status")
    private String status;

    @Column(name = "barcode")
    private String barcode;

    @ManyToOne
    private LabCompendium labCompendium;

    @OneToMany(mappedBy = "labRequisition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LabRequisitionsComponents> labRequisitionsComponents = new HashSet<>();

    @ManyToOne
    private Chart chart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatient_signature() {
        return patient_signature;
    }

    public LabRequisition patient_signature(String patient_signature) {
        this.patient_signature = patient_signature;
        return this;
    }

    public void setPatient_signature(String patient_signature) {
        this.patient_signature = patient_signature;
    }

    public String getStatus() {
        return status;
    }

    public LabRequisition status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public LabRequisition barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LabCompendium getLabCompendium() {
        return labCompendium;
    }

    public LabRequisition labCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
        return this;
    }

    public void setLabCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
    }

    public Set<LabRequisitionsComponents> getLabRequisitionsComponents() {
        return labRequisitionsComponents;
    }

    public LabRequisition labRequisitionsComponents(Set<LabRequisitionsComponents> labRequisitionsComponents) {
        this.labRequisitionsComponents = labRequisitionsComponents;
        return this;
    }

//    public LabRequisition addLabRequisitionsComponents(LabRequisitionsComponents labRequisitionsComponents) {
//        labRequisitionsComponents.add(labRequisitionsComponents);
//        labRequisitionsComponents.setLabRequisition(this);
//        return this;
//    }
//
//    public LabRequisition removeLabRequisitionsComponents(LabRequisitionsComponents labRequisitionsComponents) {
//        labRequisitionsComponents.remove(labRequisitionsComponents);
//        labRequisitionsComponents.setLabRequisition(null);
//        return this;
//    }

    public void setLabRequisitionsComponents(Set<LabRequisitionsComponents> labRequisitionsComponents) {
        this.labRequisitionsComponents = labRequisitionsComponents;
    }

    public Chart getChart() {
        return chart;
    }

    public LabRequisition chart(Chart chart) {
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
        LabRequisition labRequisition = (LabRequisition) o;
        if(labRequisition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, labRequisition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LabRequisition{" +
            "id=" + id +
            ", patient_signature='" + patient_signature + "'" +
            ", status='" + status + "'" +
            ", barcode='" + barcode + "'" +
            '}';
    }
}
