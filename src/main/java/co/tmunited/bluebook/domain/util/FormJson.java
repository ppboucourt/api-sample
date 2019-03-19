package co.tmunited.bluebook.domain.util;

/**
 * Created by pboucourt on 4/10/2017.
 */
public class FormJson {

    private Long id;

    private String name;

    private Boolean loadManually;

    private Boolean patientSignatureRequired;

    private Boolean guarantorSignatureRequired;

    private Boolean allowAttachment;

    private Boolean allowRevocation;

    private Boolean enabled;

    private Boolean requiredLabRequisitions;

    private Integer expiresDays;

    private Boolean onlyOnePerpatient;

    private Boolean expires;

    private String contentHtml;

    private String content;

    private Long typePatientProcessId;

    private String typePatientProcessName;

    private Long typeFormRulesId;

    private String typeFormRulesName;

    private Long laboratoriesId;

    private String laboratoriesName;

    private Long facilityId;

    private String facilityName;

    private Boolean loadAutomatic;

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

    public Boolean getLoadManually() {
        return loadManually;
    }

    public void setLoadManually(Boolean loadManually) {
        this.loadManually = loadManually;
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

    public Boolean getAllowAttachment() {
        return allowAttachment;
    }

    public void setAllowAttachment(Boolean allowAttachment) {
        this.allowAttachment = allowAttachment;
    }

    public Boolean getAllowRevocation() {
        return allowRevocation;
    }

    public void setAllowRevocation(Boolean allowRevocation) {
        this.allowRevocation = allowRevocation;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Boolean getOnlyOnePerpatient() {
        return onlyOnePerpatient;
    }

    public void setOnlyOnePerpatient(Boolean onlyOnePerpatient) {
        this.onlyOnePerpatient = onlyOnePerpatient;
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

    public Long getTypeFormRulesId() {
        return typeFormRulesId;
    }

    public void setTypeFormRulesId(Long typeFormRulesId) {
        this.typeFormRulesId = typeFormRulesId;
    }

    public String getTypeFormRulesName() {
        return typeFormRulesName;
    }

    public void setTypeFormRulesName(String typeFormRulesName) {
        this.typeFormRulesName = typeFormRulesName;
    }

    public Long getLaboratoriesId() {
        return laboratoriesId;
    }

    public void setLaboratoriesId(Long laboratoriesId) {
        this.laboratoriesId = laboratoriesId;
    }

    public String getLaboratoriesName() {
        return laboratoriesName;
    }

    public void setLaboratoriesName(String laboratoriesName) {
        this.laboratoriesName = laboratoriesName;
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

    public FormJson(Long id, String name, Boolean loadManually, Boolean patientSignatureRequired, Boolean guarantorSignatureRequired, Boolean allowAttachment, Boolean allowRevocation, String status, Boolean enabled, Boolean requiredLabRequisitions, Integer expiresDays, Boolean onlyOnePerpatient, Boolean expires, String contentHtml, Long typePatientProcessId, String typePatientProcessName, Long typeFormRulesId, String typeFormRulesName, Long laboratoriesId, String laboratoriesName, Long facilityId, String facilityName) {
        this.id = id;
        this.name = name;
        this.loadManually = loadManually;
        this.patientSignatureRequired = patientSignatureRequired;
        this.guarantorSignatureRequired = guarantorSignatureRequired;
        this.allowAttachment = allowAttachment;
        this.allowRevocation = allowRevocation;
        this.enabled = enabled;
        this.requiredLabRequisitions = requiredLabRequisitions;
        this.expiresDays = expiresDays;
        this.onlyOnePerpatient = onlyOnePerpatient;
        this.expires = expires;
        this.contentHtml = contentHtml;
        this.typePatientProcessId = typePatientProcessId;
        this.typePatientProcessName = typePatientProcessName;
        this.typeFormRulesId = typeFormRulesId;
        this.typeFormRulesName = typeFormRulesName;
        this.laboratoriesId = laboratoriesId;
        this.laboratoriesName = laboratoriesName;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
    }

    public FormJson() {}

    public Boolean getLoadAutomatic() {
        return loadAutomatic;
    }

    public void setLoadAutomatic(Boolean loadAutomatic) {
        this.loadAutomatic = loadAutomatic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
