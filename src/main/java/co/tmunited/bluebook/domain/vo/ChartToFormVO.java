package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Contacts;
import co.tmunited.bluebook.domain.Signature;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Pp on 7/11/2017.
 */
public class ChartToFormVO {

    private Long id;

    private FormStatus status;

    private String jsonData;

    private String name;

    private String htmlData;

    private Long formId;

    private Boolean patientSignatureRequired;

    private Boolean guarantorSignatureRequired;

    private Boolean allowAttachment;

    private Boolean allowRevocation;

    private Integer expiresDays;

    private Boolean onlyOne;

    private Boolean expire;

    private Long typePatientProcessId;

    private String contentHtml;

    private Boolean loadAutomatic;

    private Long chart;

    private Signature signaturePatient;

    private Signature signatureGuarantor;

    private Signature revocationPatient;

    private Signature revocationGuarantor;

    private String guarantor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormStatus getStatus() {
        return status;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
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

    public Integer getExpiresDays() {
        return expiresDays;
    }

    public void setExpiresDays(Integer expiresDays) {
        this.expiresDays = expiresDays;
    }

    public Boolean getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Boolean getExpire() {
        return expire;
    }

    public void setExpire(Boolean expire) {
        this.expire = expire;
    }

    public Long getTypePatientProcessId() {
        return typePatientProcessId;
    }

    public void setTypePatientProcessId(Long typePatientProcessId) {
        this.typePatientProcessId = typePatientProcessId;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Boolean getLoadAutomatic() {
        return loadAutomatic;
    }

    public void setLoadAutomatic(Boolean loadAutomatic) {
        this.loadAutomatic = loadAutomatic;
    }

    public Long getChart() {
        return chart;
    }

    public void setChart(Long chart) {
        this.chart = chart;
    }

    public Signature getSignaturePatient() {
        return signaturePatient;
    }

    public void setSignaturePatient(Signature signaturePatient) {
        this.signaturePatient = signaturePatient;
    }

    public Signature getSignatureGuarantor() {
        return signatureGuarantor;
    }

    public void setSignatureGuarantor(Signature signatureGuarantor) {
        this.signatureGuarantor = signatureGuarantor;
    }

    public Signature getRevocationPatient() {
        return revocationPatient;
    }

    public void setRevocationPatient(Signature revocationPatient) {
        this.revocationPatient = revocationPatient;
    }

    public Signature getRevocationGuarantor() {
        return revocationGuarantor;
    }

    public void setRevocationGuarantor(Signature revocationGuarantor) {
        this.revocationGuarantor = revocationGuarantor;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public ChartToFormVO(Long id, FormStatus status, String jsonData, String name,
                         String htmlData, Long formId, Boolean patientSignatureRequired,
                         Boolean guarantorSignatureRequired, Boolean allowAttachment,
                         Boolean allowRevocation, Integer expiresDays, Boolean onlyOne,
                         Boolean expire, Long typePatientProcessId, String contentHtml,
                         Boolean loadAutomatic, Signature signaturePatient, Signature signatureGuarantor,
                         Signature revocationPatient, Signature revocationGuarantor, String guarantor) {
        this.id = id;
        this.status = status;
        this.jsonData = jsonData;
        this.name = name;
        this.htmlData = htmlData;
        this.formId = formId;
        this.patientSignatureRequired = patientSignatureRequired;
        this.guarantorSignatureRequired = guarantorSignatureRequired;
        this.allowAttachment = allowAttachment;
        this.allowRevocation = allowRevocation;
        this.expiresDays = expiresDays;
        this.onlyOne = onlyOne;
        this.expire = expire;
        this.typePatientProcessId = typePatientProcessId;
        this.contentHtml = contentHtml;
        this.loadAutomatic = loadAutomatic;
        this.signaturePatient = signaturePatient;
        this.signatureGuarantor = signatureGuarantor;
        this.revocationPatient = revocationPatient;
        this.revocationGuarantor = revocationGuarantor;
        this.guarantor = guarantor;
    }

    public ChartToFormVO(Long id, FormStatus status, String jsonData, String name,
                         String htmlData, Long formId, Boolean patientSignatureRequired,
                         Boolean guarantorSignatureRequired, Boolean allowAttachment,
                         Boolean allowRevocation, Integer expiresDays, Boolean onlyOne,
                         Boolean expire, Long typePatientProcessId, String contentHtml,
                         Boolean loadAutomatic, Signature signaturePatient, Signature signatureGuarantor,
                         Signature revocationPatient, Signature revocationGuarantor, String guarantor, Chart chart) {
        this.id = id;
        this.status = status;
        this.jsonData = jsonData;
        this.name = name;
        this.htmlData = htmlData;
        this.formId = formId;
        this.patientSignatureRequired = patientSignatureRequired;
        this.guarantorSignatureRequired = guarantorSignatureRequired;
        this.allowAttachment = allowAttachment;
        this.allowRevocation = allowRevocation;
        this.expiresDays = expiresDays;
        this.onlyOne = onlyOne;
        this.expire = expire;
        this.typePatientProcessId = typePatientProcessId;
        this.contentHtml = contentHtml;
        this.loadAutomatic = loadAutomatic;
        this.signaturePatient = signaturePatient;
        this.signatureGuarantor = signatureGuarantor;
        this.revocationPatient = revocationPatient;
        this.revocationGuarantor = revocationGuarantor;
        this.guarantor = guarantor;
        this.chart = chart.getId();
    }

    public ChartToFormVO() {}
}
