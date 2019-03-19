package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patient")
public class Patient extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 9)
    @Column(name = "social", length = 25)
    private String social;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @Column(name = "identification")
    private String identification;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex;

    @Column(name = "date_birth")
    private ZonedDateTime dateBirth;

    @Column(name = "preferred_name")
    private String preferredName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chart> charts = new HashSet<>();

    @Formula("0")
    private long countCharts = 0;

    @ManyToOne
    private TypeRace typeRace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocial() {
        return social;
    }

    public Patient social(String social) {
        this.social = social;
        return this;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getStatus() {
        return status;
    }

    public Patient status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentification() {
        return identification;
    }

    public Patient identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Gender getSex() {
        return sex;
    }

    public Patient sex(Gender sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public ZonedDateTime getDateBirth() {
        return dateBirth;
    }

    public Patient dateBirth(ZonedDateTime dateBirth) {
        this.dateBirth = dateBirth;
        return this;
    }

    public void setDateBirth(ZonedDateTime dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public Patient preferredName(String preferredName) {
        this.preferredName = preferredName;
        return this;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patient firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Patient middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public Patient charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public Patient addCharts(Chart chart) {
        charts.add(chart);
        chart.setPatient(this);
        return this;
    }

    public Patient removeCharts(Chart chart) {
        charts.remove(chart);
        chart.setPatient(null);
        return this;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }

    public TypeRace getTypeRace() {
        return typeRace;
    }

    public Patient typeRace(TypeRace typeRace) {
        this.typeRace = typeRace;
        return this;
    }

    public void setTypeRace(TypeRace typeRace) {
        this.typeRace = typeRace;
    }

    public long getCountCharts() {
        return countCharts;
    }

    public void setCountCharts(long countCharts) {
        this.countCharts = countCharts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if(patient.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", social='" + social + "'" +
            ", status='" + status + "'" +
            ", identification='" + identification + "'" +
            ", sex='" + sex + "'" +
            ", dateBirth='" + dateBirth + "'" +
            ", preferredName='" + preferredName + "'" +
            ", firstName='" + firstName + "'" +
            ", middleName='" + middleName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }

    @PostLoad
    public void countCharts() {
        countCharts = charts.size();
    }
}
