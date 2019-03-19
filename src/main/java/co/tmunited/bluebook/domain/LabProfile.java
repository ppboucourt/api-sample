package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LabProfile.
 */
@Entity
@Table(name = "lab_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "labprofile")
public class LabProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lab_profile_lab_compendium",
               joinColumns = @JoinColumn(name="lab_profiles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="lab_compendiums_id", referencedColumnName="ID"))
    private Set<LabCompendium> labCompendiums = new HashSet<>();

    @ManyToOne
    private Facility facility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LabProfile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public LabProfile enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<LabCompendium> getLabCompendiums() {
        return labCompendiums;
    }

    public LabProfile labCompendiums(Set<LabCompendium> labCompendiums) {
        this.labCompendiums = labCompendiums;
        return this;
    }

    public LabProfile addLabCompendium(LabCompendium labCompendium) {
        labCompendiums.add(labCompendium);
        labCompendium.getLabProfiles().add(this);
        return this;
    }

    public LabProfile removeLabCompendium(LabCompendium labCompendium) {
        labCompendiums.remove(labCompendium);
        labCompendium.getLabProfiles().remove(this);
        return this;
    }

    public void setLabCompendiums(Set<LabCompendium> labCompendiums) {
        this.labCompendiums = labCompendiums;
    }

    public Facility getFacility() {
        return facility;
    }

    public LabProfile facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabProfile labProfile = (LabProfile) o;
        if(labProfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, labProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LabProfile{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
