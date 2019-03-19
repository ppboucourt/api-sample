package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Tube.
 */
@Entity
@Table(name = "tube")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tube")
public class Tube extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tube_id_seq")
    @SequenceGenerator(
        name="tube_id_seq",
        sequenceName="tube_id_seq",
        allocationSize=20
    )
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tubes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LabCompendium> labCompendiums = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tube name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LabCompendium> getLabCompendiums() {
        return labCompendiums;
    }

    public Tube compendiums(Set<LabCompendium> labCompendiums) {
        this.labCompendiums = labCompendiums;
        return this;
    }

    public Tube addCompendium(LabCompendium labCompendium) {
        labCompendiums.add(labCompendium);
        labCompendium.getTubes().add(this);
        return this;
    }

    public void setCompendiums(Set<LabCompendium> labCompendiums) {
        this.labCompendiums = labCompendiums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tube tube = (Tube) o;
        if(tube.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tube.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tube{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
