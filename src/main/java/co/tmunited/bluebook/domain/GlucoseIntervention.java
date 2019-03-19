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
 * A GlucoseIntervention.
 */
@Entity
@Table(name = "glucose_intervention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "glucoseintervention")
public class GlucoseIntervention extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "glucoseInterventions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Glucose> glucoses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GlucoseIntervention name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Glucose> getGlucoses() {
        return glucoses;
    }

    public GlucoseIntervention glucoses(Set<Glucose> glucoses) {
        this.glucoses = glucoses;
        return this;
    }

    public GlucoseIntervention addGlucose(Glucose glucose) {
        glucoses.add(glucose);
        glucose.getGlucoseInterventions().add(this);
        return this;
    }

    public GlucoseIntervention removeGlucose(Glucose glucose) {
        glucoses.remove(glucose);
        glucose.getGlucoseInterventions().remove(this);
        return this;
    }

    public void setGlucoses(Set<Glucose> glucoses) {
        this.glucoses = glucoses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlucoseIntervention glucoseIntervention = (GlucoseIntervention) o;
        if(glucoseIntervention.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, glucoseIntervention.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GlucoseIntervention{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
