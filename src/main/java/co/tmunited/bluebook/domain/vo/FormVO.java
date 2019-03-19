package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Signature;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PpTmUnited on 6/15/2017.
 */
public class FormVO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String status;

    private Boolean onlyOne;

    private Boolean patientSignatureRequired;

    private Boolean guarantorSignatureRequired;

    private Boolean allowAttachment;

    private Boolean allowRevocation;

    private String patientProcess;

    private Boolean enabled;

    private Boolean requiredLabRequisitions;

    private Integer expiresDays;

    private Boolean expires;

    private String contentHtml;

    private String typePatientProcessName;

    private Long typePatientProcessId;

    private Set<Long> typeLevelCares = new HashSet<>();

    private Boolean loadAutomatic;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime modifiedDate;

    private String modifiedBy;

    private Signature patientSignature;

    private Signature guarantorSignature;

    private Long formId;

    private Long evaluationTemplateId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isAllowAttachment() {
        return allowAttachment;
    }

    public void setAllowAttachment(Boolean allowAttachment) {
        this.allowAttachment = allowAttachment;
    }

    public Boolean getPatientSignatureRequired() {
        return patientSignatureRequired;
    }

    public void setPatientSignatureRequired(Boolean patientSignatureRequired) {
        this.patientSignatureRequired = patientSignatureRequired;
    }

    public Boolean getGuarantorSignatureRequired() {
        return guarantorSignatureRequired;
    }

    public void setGuarantorSignatureRequired(Boolean guarantorSignatureRequired) {
        this.guarantorSignatureRequired = guarantorSignatureRequired;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Signature getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Signature patientSignature) {
        this.patientSignature = patientSignature;
    }

    public Signature getGuarantorSignature() {
        return guarantorSignature;
    }

    public void setGuarantorSignature(Signature guarantorSignature) {
        this.guarantorSignature = guarantorSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormVO formVO = (FormVO) o;

        if (id != null ? !id.equals(formVO.id) : formVO.id != null) return false;
        if (name != null ? !name.equals(formVO.name) : formVO.name != null) return false;
        if (type != null ? !type.equals(formVO.type) : formVO.type != null) return false;
        return status != null ? status.equals(formVO.status) : formVO.status == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormVO{" +
            "id=" + id +
            ", name='" + name +
            ", type='" + type +
            ", status='" + status +
            '}';
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPatientProcess() {
        return patientProcess;
    }

    public void setPatientProcess(String patientProcess) {
        this.patientProcess = patientProcess;
    }

    public Boolean getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Boolean getAllowAttachment() {
        return allowAttachment;
    }

    public Boolean getAllowRevocation() {
        return allowRevocation;
    }

    public void setAllowRevocation(Boolean allowRevocation) {
        this.allowRevocation = allowRevocation;
    }

    public Boolean getRequiredLabRequisitions() {
        return requiredLabRequisitions;
    }

    public void setRequiredLabRequisitions(Boolean requiredLabRequisitions) {
        this.requiredLabRequisitions = requiredLabRequisitions;
    }

    public Integer getExpiresDays() {
        return expiresDays;
    }

    public void setExpiresDays(Integer expiresDays) {
        this.expiresDays = expiresDays;
    }

    public Boolean getExpires() {
        return expires;
    }

    public void setExpires(Boolean expires) {
        this.expires = expires;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getTypePatientProcessName() {
        return typePatientProcessName;
    }

    public void setTypePatientProcessName(String typePatientProcessName) {
        this.typePatientProcessName = typePatientProcessName;
    }

    public Long getTypePatientProcessId() {
        return typePatientProcessId;
    }

    public void setTypePatientProcessId(Long typePatientProcessId) {
        this.typePatientProcessId = typePatientProcessId;
    }

    public Set<Long> getTypeLevelCares() {
        return typeLevelCares;
    }

    public void setTypeLevelCares(Set<Long> typeLevelCares) {
        this.typeLevelCares = typeLevelCares;
    }

    public Boolean getLoadAutomatic() {
        return loadAutomatic;
    }

    public void setLoadAutomatic(Boolean loadAutomatic) {
        this.loadAutomatic = loadAutomatic;
    }

    public FormVO() {}

    public FormVO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.type = "Evaluation";
    }

    public FormVO(Long id, String name, Boolean enabled, Boolean onlyOne, String patientProcess) {
        this(id, name);
        this.enabled = enabled;
        this.onlyOne = onlyOne;
        this.patientProcess = patientProcess;
        this.type = "Evaluation";
    }

    public FormVO(Long id, String name, Boolean enabled, Boolean onlyOne,
                  String patientProcess, ZonedDateTime createdDate,
                  Signature patientSignature) {
        this(id, name, enabled, onlyOne, patientProcess);
        this.createdDate = createdDate;
        this.patientSignature = patientSignature;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getEvaluationTemplateId() {
        return evaluationTemplateId;
    }

    public void setEvaluationTemplateId(Long evaluationTemplateId) {
        this.evaluationTemplateId = evaluationTemplateId;
    }
}
