package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PatientResultDet.
 */
@Entity
@Table(name = "patient_result_det")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientresultdet")
public class PatientResultDet extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_result_det_id_seq")
    @SequenceGenerator(
        name="patient_result_det_id_seq",
        sequenceName="patient_result_det_id_seq",
        allocationSize=20
    )
    private Long id;

    @Column(name = "test_code")
    private String testCode;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "result")
    private String result;

    @Column(name = "cut_off")
    private String cutOff;

    @Column(name = "units")
    private String units;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "patient_result_id", referencedColumnName = "id")
    private PatientResult patientResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public PatientResultDet testCode(String testCode) {
        this.testCode = testCode;
        return this;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public PatientResultDet testName(String testName) {
        this.testName = testName;
        return this;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PatientResultDet result(String result) {
        this.result = result;
        return this;
    }

    public String getCutOff() {
        return cutOff;
    }

    public void setCutOff(String cutOff) {
        this.cutOff = cutOff;
    }

    public PatientResultDet cutOff(String cutOff) {
        this.cutOff = cutOff;
        return this;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public PatientResultDet units(String units) {
        this.units = units;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PatientResultDet status(String status) {
        this.status = status;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PatientResultDet comments(String comments) {
        this.comments = comments;
        return this;
    }

    public PatientResult getPatientResult() {
        return patientResult;
    }

    public void setPatientResult(PatientResult patientResult) {
        this.patientResult = patientResult;
    }

    public PatientResultDet patientResult(PatientResult patientResult) {
        this.patientResult = patientResult;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientResultDet patientResultDet = (PatientResultDet) o;
        if (patientResultDet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientResultDet.id);
    }

    public boolean equals(PatientResultDet patientResultDet) {
        initObjects(patientResultDet);

        if (this.testCode.contentEquals(patientResultDet.getTestCode()) &&
            this.testName.contentEquals(patientResultDet.getTestName()) &&
            this.result.contentEquals(patientResultDet.getResult()) &&
            this.cutOff.contentEquals(patientResultDet.getCutOff()) &&
            this.units.contentEquals(patientResultDet.getUnits()) &&
            this.status.contentEquals(patientResultDet.getStatus()) &&
            this.comments.contentEquals(patientResultDet.getComments())) {
            return true;
        } else {
            return false;
        }
    }

    private void initObjects(PatientResultDet patientResultDet) {
        if (this.testCode == null) {
            this.testCode = "";
        }
        if (this.testName == null) {
            this.testName = "";
        }
        if (this.result == null) {
            this.result = "";
        }
        if (this.cutOff == null) {
            this.cutOff = "";
        }
        if (this.units == null) {
            this.units = "";
        }
        if (this.status == null) {
            this.status = "";
        }
        if (this.comments == null) {
            this.comments = "";
        }

        if (patientResultDet.testCode == null) {
            patientResultDet.testCode = "";
        }
        if (patientResultDet.testName == null) {
            patientResultDet.testName = "";
        }
        if (patientResultDet.result == null) {
            patientResultDet.result = "";
        }
        if (patientResultDet.cutOff == null) {
            patientResultDet.cutOff = "";
        }
        if (patientResultDet.units == null) {
            patientResultDet.units = "";
        }
        if (patientResultDet.status == null) {
            patientResultDet.status = "";
        }
        if (patientResultDet.comments == null) {
            patientResultDet.comments = "";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientResultDet{" +
            "id=" + id +
            ", testCode='" + testCode + "'" +
            ", testName='" + testName + "'" +
            ", result='" + result + "'" +
            ", cutOff='" + cutOff + "'" +
            ", units='" + units + "'" +
            ", status='" + status + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
}
