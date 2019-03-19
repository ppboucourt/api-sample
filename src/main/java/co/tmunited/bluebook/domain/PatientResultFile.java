package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PatientResultFile.
 */
@Entity
@Table(name = "patient_result_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patientresultfile")
public class PatientResultFile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @JsonIgnore
    @ManyToOne
    @NotNull
    private PatientResult patientResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PatientResultFile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public PatientResultFile uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PatientResult getPatientResult() {
        return patientResult;
    }

    public PatientResultFile patientResult(PatientResult patientResult) {
        this.patientResult = patientResult;
        return this;
    }

    public void setPatientResult(PatientResult patientResult) {
        this.patientResult = patientResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientResultFile patientResultFile = (PatientResultFile) o;
        if(patientResultFile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patientResultFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PatientResultFile{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", uuid='" + uuid + "'" +
            '}';
    }
}
