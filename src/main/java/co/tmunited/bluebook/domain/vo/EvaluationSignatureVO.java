package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.domain.Signature;
import co.tmunited.bluebook.domain.enumeration.SignTypeValues;

/**
 * Created by Pp on 7/19/2017.
 */
public class EvaluationSignatureVO {

    private Long id;

    private String role;

    private Long evaluationId;

    private Employee employee;

    private Signature signature;

    private SignTypeValues signType;

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

    public void setRole(String role) {
        this.role = role;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Signature getSignature() {
        return signature;
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

    public Signature getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Signature patientSignature) {
        this.patientSignature = patientSignature;
    }

    public EvaluationSignatureVO() {
    }

    public EvaluationSignatureVO(Long id, Signature signature, Signature patientSignature) {
        this.id = id;
        this.signature = signature;
        this.patientSignature = patientSignature;
    }
}
