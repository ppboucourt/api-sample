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
 * A OrderFrequency.
 */
@Entity
@Table(name = "order_frequency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderfrequency")
public class OrderFrequency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "days")
    private Integer days;

    @OneToMany(mappedBy = "orderFrequency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientOrderTest> patientOrderTests = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OrderFrequency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public OrderFrequency days(Integer days) {
        this.days = days;
        return this;
    }

    public Set<PatientOrderTest> getPatientOrderTests() {
        return patientOrderTests;
    }

    public OrderFrequency patientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
        return this;
    }

    public OrderFrequency addPatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.add(patientOrderTest);
        patientOrderTest.setOrderFrequency(this);
        return this;
    }

    public OrderFrequency removePatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.remove(patientOrderTest);
        patientOrderTest.setOrderFrequency(null);
        return this;
    }

    public void setPatientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderFrequency orderFrequency = (OrderFrequency) o;
        if(orderFrequency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderFrequency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderFrequency{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
