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
 * A Icd10.
 */
@Entity
@Table(name = "icd_10")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "icd10")
public class Icd10 extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "icd10S", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Chart> charts = new HashSet<>();

    @ManyToMany(mappedBy = "icd10s", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientOrderTest> patientOrderTests = new HashSet<>();

    @ManyToMany(mappedBy = "icd10s")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientMedicationPres> patientMedicationPress = new HashSet<>();

    @ManyToMany(mappedBy = "icd10s")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientActionPre> patientActionPres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Icd10 code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Icd10 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public Icd10 charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public Icd10 addChart(Chart chart) {
        charts.add(chart);
        chart.getIcd10S().add(this);
        return this;
    }

    public Icd10 removeChart(Chart chart) {
        charts.remove(chart);
        chart.getIcd10S().remove(this);
        return this;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }

    public Set<PatientOrderTest> getPatientOrderTests() {
        return patientOrderTests;
    }

    public Icd10 patientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
        return this;
    }

    public Icd10 addPatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.add(patientOrderTest);
        patientOrderTest.getIcd10s().add(this);
        return this;
    }

    public Icd10 removePatientOrderTest(PatientOrderTest patientOrderTest) {
        patientOrderTests.remove(patientOrderTest);
        patientOrderTest.getIcd10s().remove(this);
        return this;
    }

    public void setPatientOrderTests(Set<PatientOrderTest> patientOrderTests) {
        this.patientOrderTests = patientOrderTests;
    }

    public Set<PatientMedicationPres> getPatientMedicationPress() {
        return patientMedicationPress;
    }

    public void setPatientMedicationPress(Set<PatientMedicationPres> patientMedicationPress) {
        this.patientMedicationPress = patientMedicationPress;
    }

    public Set<PatientActionPre> getPatientActionPres() {
        return patientActionPres;
    }

    public void setPatientActionPres(Set<PatientActionPre> patientActionPres) {
        this.patientActionPres = patientActionPres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Icd10 icd10 = (Icd10) o;
        if(icd10.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, icd10.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Icd10{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
