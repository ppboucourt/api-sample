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
 * A InsuranceLevel.
 */
@Entity
@Table(name = "insurance_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "insurancelevel")
public class InsuranceLevel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "insuranceLevel")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Insurance> insurances = new HashSet<>();

    @ManyToOne
    private Corporation corporation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public InsuranceLevel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public InsuranceLevel insurances(Set<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

//    public InsuranceLevel addInsurance(Insurance insurance) {
//        insurances.add(insurance);
//        insurance.setInsuranceLevel(this);
//        return this;
//    }
//
//    public InsuranceLevel removeInsurance(Insurance insurance) {
//        insurances.remove(insurance);
//        insurance.setInsuranceLevel(null);
//        return this;
//    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Corporation getCorporation() {
        return corporation;
    }

    public InsuranceLevel corporation(Corporation corporation) {
        this.corporation = corporation;
        return this;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceLevel insuranceLevel = (InsuranceLevel) o;
        if(insuranceLevel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insuranceLevel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InsuranceLevel{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
