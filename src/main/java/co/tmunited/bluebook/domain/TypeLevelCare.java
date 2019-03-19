package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TypeLevelCare.
 */
@Entity
@Table(name = "type_level_care")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typelevelcare")
public class TypeLevelCare extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Facility facility;

    @ManyToMany(mappedBy = "typeLevelCares", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EvaluationTemplate> evaluationTemplates = new HashSet<>();

    @ManyToMany(mappedBy = "typeLevelCares", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Forms> forms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TypeLevelCare name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public TypeLevelCare status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Facility getFacility() {
        return facility;
    }

    public TypeLevelCare facility(Facility facility) {
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
        TypeLevelCare typeLevelCare = (TypeLevelCare) o;
        if(typeLevelCare.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typeLevelCare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypeLevelCare{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public Set<EvaluationTemplate> getEvaluationTemplates() {
        return evaluationTemplates;
    }

    public void setEvaluationTemplates(Set<EvaluationTemplate> evaluationTemplates) {
        this.evaluationTemplates = evaluationTemplates;
    }

    public Set<Forms> getForms() {
        return forms;
    }

    public void setForms(Set<Forms> forms) {
        this.forms = forms;
    }
}
