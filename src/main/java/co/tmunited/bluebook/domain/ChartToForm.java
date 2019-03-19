package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.vo.FormVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ChartToForm.
 */
@Entity
@Table(name = "chart_to_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "charttoform")
public class ChartToForm extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FormStatus status;

    @Column(name = "json_data")
    private String jsonData;

    @Column(name = "name")
    private String name;

    @Column(name="html_data")
    private String htmlData;

    @Column(name = "form_id")
    private Long formId;

    @Column(name = "patient_signature_required")
    private Boolean patientSignatureRequired;

    @Column(name = "guarantor_signature_required")
    private Boolean guarantorSignatureRequired;

    @Column(name = "allow_attachment")
    private Boolean allowAttachment;

    @Column(name = "allow_revocation")
    private Boolean allowRevocation;

    @Column(name = "expires_days")
    private Integer expiresDays;

    @Column(name = "only_one")
    private Boolean onlyOne;

    @Column(name = "expire")
    private Boolean expire;

    @Column(name = "type_patient_process_id")
    private Long typePatientProcessId;

    @Column(name = "content_html")
    private String contentHtml;

    @Column(name = "load_automatic")
    private Boolean loadAutomatic;

    @Column(name = "migrated")
    private Boolean migrated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_id", referencedColumnName="ID", insertable = false, updatable = false)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Signature signaturePatient;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Signature signatureGuarantor;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Signature revocationPatient;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Signature revocationGuarantor;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contacts guarantor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormStatus getStatus() {
        return status;
    }

    public ChartToForm status(FormStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public String getJsonData() {
        return jsonData;
    }

    public ChartToForm jsonData(String jsonData) {
        this.jsonData = jsonData;
        return this;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getName() {
        return name;
    }

    public ChartToForm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartToForm chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
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

    public String getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData;
    }

    public Contacts getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(Contacts guarantor) {
        this.guarantor = guarantor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartToForm chartToForm = (ChartToForm) o;
        if(chartToForm.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartToForm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartToForm{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", jsonData='" + jsonData + "'" +
            ", name='" + name + "'" +
            ", htmlData='" + htmlData + "'" +
            '}';
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

    public FormVO chartFormToFormVO(ChartToForm chartToForm) {

        FormVO formVO = new FormVO();
        formVO.setId(chartToForm.getId());
        formVO.setName(chartToForm.getName());
        formVO.setStatus(chartToForm.getStatus()!= null?chartToForm.getStatus().toString(): "");
        formVO.setPatientSignatureRequired(chartToForm.getPatientSignatureRequired()!= null?chartToForm.getPatientSignatureRequired(): false);
        formVO.setGuarantorSignatureRequired(chartToForm.getGuarantorSignatureRequired()!= null?chartToForm.getGuarantorSignatureRequired(): false);
        formVO.setAllowAttachment(chartToForm.getAllowAttachment()!=null?chartToForm.getAllowAttachment(): false);
        formVO.setOnlyOne(chartToForm.getOnlyOne()!=null?chartToForm.getOnlyOne():false);
        formVO.setPatientSignature(chartToForm.getSignaturePatient());
        formVO.setGuarantorSignature(chartToForm.getSignatureGuarantor());
        formVO.setCreatedDate(chartToForm.getCreatedDate());
        formVO.setCreatedBy(chartToForm.getCreatedBy());
        formVO.setFormId(chartToForm.getFormId());
        formVO.setType("Consent");

        return formVO;
    }

    public Boolean getExpire() {
        return expire;
    }

    public void setExpire(Boolean expire) {
        this.expire = expire;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Boolean getMigrated() {
        return migrated;
    }

    public void setMigrated(Boolean migrated) {
        this.migrated = migrated;
    }
}
