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
 * A LabCompendium.
 */
@Entity
@Table(name = "lab_compendium")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "labcompendium")
public class LabCompendium extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "units")
    private String units;

    @Column(name = "specimen")
    private String specimen;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "labCompendium")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LabRequisition> labRequisitions = new HashSet<>();

    @OneToMany(mappedBy = "labCompendium")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LabRequisitionsComponents> labRequisitionsComponents = new HashSet<>();

    @OneToMany(mappedBy = "labCompendium")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orders = new HashSet<>();

    @ManyToMany(mappedBy = "labCompendiums")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LabProfile> labProfiles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lab_compendium_tube",
        joinColumns = @JoinColumn(name="lab_compendiums_id", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="tubes_id", referencedColumnName="ID"))
    private Set<Tube> tubes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public LabCompendium code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public LabCompendium description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnits() {
        return units;
    }

    public LabCompendium units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSpecimen() {
        return specimen;
    }

    public LabCompendium specimen(String specimen) {
        this.specimen = specimen;
        return this;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getStatus() {
        return status;
    }

    public LabCompendium status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<LabRequisition> getLabRequisitions() {
        return labRequisitions;
    }

    public LabCompendium labRequisitions(Set<LabRequisition> labRequisitions) {
        this.labRequisitions = labRequisitions;
        return this;
    }

    public LabCompendium addLabRequisitions(LabRequisition labRequisition) {
        labRequisitions.add(labRequisition);
        labRequisition.setLabCompendium(this);
        return this;
    }

    public LabCompendium removeLabRequisitions(LabRequisition labRequisition) {
        labRequisitions.remove(labRequisition);
        labRequisition.setLabCompendium(null);
        return this;
    }

    public void setLabRequisitions(Set<LabRequisition> labRequisitions) {
        this.labRequisitions = labRequisitions;
    }

    public Set<LabRequisitionsComponents> getLabRequisitionsComponents() {
        return labRequisitionsComponents;
    }

    public LabCompendium labRequisitionsComponents(Set<LabRequisitionsComponents> labRequisitionsComponents) {
        this.labRequisitionsComponents = labRequisitionsComponents;
        return this;
    }

    /*public LabCompendium addLabRequisitionsComponents(LabRequisitionsComponents labRequisitionsComponents) {
        labRequisitionsComponents.add(labRequisitionsComponents);
        labRequisitionsComponents.setLabCompendium(this);
        return this;
    }

    public LabCompendium removeLabRequisitionsComponents(LabRequisitionsComponents labRequisitionsComponents) {
        labRequisitionsComponents.remove(labRequisitionsComponents);
        labRequisitionsComponents.setLabCompendium(null);
        return this;
    }*/

    public void setLabRequisitionsComponents(Set<LabRequisitionsComponents> labRequisitionsComponents) {
        this.labRequisitionsComponents = labRequisitionsComponents;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public LabCompendium orders(Set<Orders> orders) {
        this.orders = orders;
        return this;
    }

    /*public LabCompendium addOrders(Orders orders) {
        orders.add(orders);
        orders.setLabCompendium(this);
        return this;
    }

    public LabCompendium removeOrders(Orders orders) {
        orders.remove(orders);
        orders.setLabCompendium(null);
        return this;
    }*/

    public Set<Tube> getTubes() {
        return tubes;
    }

    public LabCompendium tubes(Set<Tube> tubes) {
        this.tubes = tubes;
        return this;
    }

    public LabCompendium addTube(Tube tube) {
        tubes.add(tube);
        tube.getLabCompendiums().add(this);
        return this;
    }

    public LabCompendium removeTube(Tube tube) {
        tubes.remove(tube);
        tube.getLabCompendiums().remove(this);
        return this;
    }

    public void setTubes(Set<Tube> tubes) {
        this.tubes = tubes;
    }


    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<LabProfile> getLabProfiles() {
        return labProfiles;
    }

    public LabCompendium labProfiles(Set<LabProfile> labProfiles) {
        this.labProfiles = labProfiles;
        return this;
    }

    public LabCompendium addLabProfile(LabProfile labProfile) {
        labProfiles.add(labProfile);
        labProfile.getLabCompendiums().add(this);
        return this;
    }

    public LabCompendium removeLabProfile(LabProfile labProfile) {
        labProfiles.remove(labProfile);
        labProfile.getLabCompendiums().remove(this);
        return this;
    }

    public void setLabProfiles(Set<LabProfile> labProfiles) {
        this.labProfiles = labProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabCompendium labCompendium = (LabCompendium) o;
        if(labCompendium.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, labCompendium.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LabCompendium{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            ", units='" + units + "'" +
            ", specimen='" + specimen + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
