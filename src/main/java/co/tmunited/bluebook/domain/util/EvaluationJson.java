package co.tmunited.bluebook.domain.util;

/**
 * Created by PpTMUnited on 4/12/2017.
 */
public class EvaluationJson {

    private Long id;

    private String name;

    private Boolean enabled;

    private Boolean patientSignature;

    private Boolean onlyOne;

    private Boolean billable;

    private Boolean forceStaffSignature;

    private Boolean forceStaffReviewSignature;

    private String jsonTemplate;

    private Long typePatientProcessId;

    private String typePatientProcessName;

    private Long typeEvaluationId;

    private String typeEvaluationName;

    private Long evaluationContentId;

    private String evaluationContentName;

    private Long facilityId;

    private String facilityName;

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

    public Boolean getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Boolean patientSignature) {
        this.patientSignature = patientSignature;
    }

    public Boolean getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean getForceStaffSignature() {
        return forceStaffSignature;
    }

    public void setForceStaffSignature(Boolean forceStaffSignature) {
        this.forceStaffSignature = forceStaffSignature;
    }

    public Boolean getForceStaffReviewSignature() {
        return forceStaffReviewSignature;
    }

    public void setForceStaffReviewSignature(Boolean forceStaffReviewSignature) {
        this.forceStaffReviewSignature = forceStaffReviewSignature;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public Long getTypePatientProcessId() {
        return typePatientProcessId;
    }

    public void setTypePatientProcessId(Long typePatientProcessId) {
        this.typePatientProcessId = typePatientProcessId;
    }

    public String getTypePatientProcessName() {
        return typePatientProcessName;
    }

    public void setTypePatientProcessName(String typePatientProcessName) {
        this.typePatientProcessName = typePatientProcessName;
    }

    public Long getTypeEvaluationId() {
        return typeEvaluationId;
    }

    public void setTypeEvaluationId(Long typeEvaluationId) {
        this.typeEvaluationId = typeEvaluationId;
    }

    public String getTypeEvaluationName() {
        return typeEvaluationName;
    }

    public void setTypeEvaluationName(String typeEvaluationName) {
        this.typeEvaluationName = typeEvaluationName;
    }

    public Long getEvaluationContentId() {
        return evaluationContentId;
    }

    public void setEvaluationContentId(Long evaluationContentId) {
        this.evaluationContentId = evaluationContentId;
    }

    public String getEvaluationContentName() {
        return evaluationContentName;
    }

    public void setEvaluationContentName(String evaluationContentName) {
        this.evaluationContentName = evaluationContentName;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public EvaluationJson() {}

    public EvaluationJson(Long id, String name, Boolean enabled, Boolean patientSignature,
                          Boolean onlyOne, Boolean billable, Boolean forceStaffSignature,
                          Boolean forceStaffReviewSignature, String jsonTemplate, Long typePatientProcessId,
                          String typePatientProcessName, Long typeEvaluationId, String typeEvaluationName,
                          Long evaluationContentId, String evaluationContentName, Long facilityId, String facilityName) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.patientSignature = patientSignature;
        this.onlyOne = onlyOne;
        this.billable = billable;
        this.forceStaffSignature = forceStaffSignature;
        this.forceStaffReviewSignature = forceStaffReviewSignature;
        this.jsonTemplate = jsonTemplate;
        this.typePatientProcessId = typePatientProcessId;
        this.typePatientProcessName = typePatientProcessName;
        this.typeEvaluationId = typeEvaluationId;
        this.typeEvaluationName = typeEvaluationName;
        this.evaluationContentId = evaluationContentId;
        this.evaluationContentName = evaluationContentName;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
    }
}
