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
 * A Via.
 */
@Entity
@Table(name = "via")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "via")
public class Via extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "via")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientOrder> patientOrders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Via name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PatientOrder> getPatientOrders() {
        return patientOrders;
    }

    public Via patientOrders(Set<PatientOrder> patientOrders) {
        this.patientOrders = patientOrders;
        return this;
    }

    public Via addPatientOrder(PatientOrder patientOrder) {
        patientOrders.add(patientOrder);
        patientOrder.setVia(this);
        return this;
    }

    public Via removePatientOrder(PatientOrder patientOrder) {
        patientOrders.remove(patientOrder);
        patientOrder.setVia(null);
        return this;
    }

    public void setPatientOrders(Set<PatientOrder> patientOrders) {
        this.patientOrders = patientOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Via via = (Via) o;
        if(via.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, via.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Via{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
