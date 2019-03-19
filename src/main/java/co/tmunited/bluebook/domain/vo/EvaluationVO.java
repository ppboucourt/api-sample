package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EvaluationVO .
 */
public class EvaluationVO implements Serializable {

    private Long id;

    private String name;

    private Boolean enabled;

    private Boolean billable;

    private String jsonTemplate;

    private Boolean onlyOne;

    private Long evaluationTemplateId;

    private EvaluationStatus status;

    private TypePatientProcess typePatientProcess;

    private TypeEvaluation typeEvaluation;

    private EvaluationContent evaluationContent;

    private Long chartId;

    private String rolesSign;

    private String rolesReview;

    private Boolean patientSignature;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime modifiedDate;

    private String modifiedBy;

    private ZonedDateTime signedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public Boolean getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Long getEvaluationTemplateId() {
        return evaluationTemplateId;
    }

    public void setEvaluationTemplateId(Long evaluationTemplateId) {
        this.evaluationTemplateId = evaluationTemplateId;
    }

    public EvaluationStatus getStatus() {
        return status;
    }

    public void setStatus(EvaluationStatus status) {
        this.status = status;
    }

    public TypePatientProcess getTypePatientProcess() {
        return typePatientProcess;
    }

    public void setTypePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
    }

    public TypeEvaluation getTypeEvaluation() {
        return typeEvaluation;
    }

    public void setTypeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }

    public EvaluationContent getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(EvaluationContent evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public String getRolesSign() {
        return rolesSign;
    }

    public void setRolesSign(String rolesSign) {
        this.rolesSign = rolesSign;
    }

    public String getRolesReview() {
        return rolesReview;
    }

    public void setRolesReview(String rolesReview) {
        this.rolesReview = rolesReview;
    }

    public Boolean getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Boolean patientSignature) {
        this.patientSignature = patientSignature;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ZonedDateTime getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(ZonedDateTime signedDate) {
        this.signedDate = signedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public EvaluationVO() {
    }

    public EvaluationVO(Long id, String name, Boolean enabled, Boolean billable, String jsonTemplate, Boolean onlyOne, Long evaluationTemplateId, EvaluationStatus status, TypePatientProcess typePatientProcess, TypeEvaluation typeEvaluation, EvaluationContent evaluationContent, Long chartId, String rolesSign, String rolesReview, Boolean patientSignature, ZonedDateTime createdDate, ZonedDateTime modifiedDate, ZonedDateTime signedDate) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.billable = billable;
        this.jsonTemplate = jsonTemplate;
        this.onlyOne = onlyOne;
        this.evaluationTemplateId = evaluationTemplateId;
        this.status = status;
        this.typePatientProcess = typePatientProcess;
        this.typeEvaluation = typeEvaluation;
        this.evaluationContent = evaluationContent;
        this.chartId = chartId;
        this.rolesSign = rolesSign;
        this.rolesReview = rolesReview;
        this.patientSignature = patientSignature;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.signedDate = signedDate;
    }

}
