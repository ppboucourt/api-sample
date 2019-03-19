package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TypePatientProcess.
 */
@Entity
@Table(name = "type_patient_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typepatientprocess")
public class TypePatientProcess extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "allow_ur")
    private String allow_ur;

    @Column(name = "protect")
    private String protect;

    @Size(max = 5)
    @Column(name = "status", length = 5)
    private String status;

    @ManyToOne
    private TypePage typePage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", insertable = false, updatable = false)
    private Facility facility;

    @Column(name = "facility_id")
    private Long facilityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TypePatientProcess name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllow_ur() {
        return allow_ur;
    }

    public TypePatientProcess allow_ur(String allow_ur) {
        this.allow_ur = allow_ur;
        return this;
    }

    public void setAllow_ur(String allow_ur) {
        this.allow_ur = allow_ur;
    }

    public String getProtect() {
        return protect;
    }

    public TypePatientProcess protect(String protect) {
        this.protect = protect;
        return this;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }

    public String getStatus() {
        return status;
    }

    public TypePatientProcess status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TypePage getTypePage() {
        return typePage;
    }

    public TypePatientProcess typePage(TypePage typePage) {
        this.typePage = typePage;
        return this;
    }

    public void setTypePage(TypePage typePage) {
        this.typePage = typePage;
    }

    public Facility getFacility() {
        return facility;
    }

    public TypePatientProcess facility(Facility facility) {
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
        TypePatientProcess typePatientProcess = (TypePatientProcess) o;
        if(typePatientProcess.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typePatientProcess.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypePatientProcess{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", allow_ur='" + allow_ur + "'" +
            ", protect='" + protect + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }
}
