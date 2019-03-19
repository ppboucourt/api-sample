package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.SignTypeValues;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EvaluationSignature.
 */
@Entity
@Table(name = "evaluation_signature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evaluationsignature")
public class EvaluationSignature extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToOne
    private Evaluation evaluation;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Signature signature;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_type")
    private SignTypeValues signType;

    @ManyToOne
    private Signature patientSignature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public EvaluationSignature role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public EvaluationSignature evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EvaluationSignature employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Signature getSignature() {
        return signature;
    }

    public EvaluationSignature signature(Signature signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public SignTypeValues getSignType() {
        return signType;
    }

    public void setSignType(SignTypeValues signType) {
        this.signType = signType;
    }

    public EvaluationSignature signType(SignTypeValues signType) {
        this.signType = signType;
        return this;
    }

    public Signature getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Signature patientSignature) {
        this.patientSignature = patientSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EvaluationSignature evaluationSignature = (EvaluationSignature) o;
        if(evaluationSignature.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evaluationSignature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EvaluationSignature{" +
            "id=" + id +
            ", role='" + role + "'" +
            '}';
    }
}
