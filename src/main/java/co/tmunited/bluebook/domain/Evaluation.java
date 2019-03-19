package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.EvaluationSignatureService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.inject.Inject;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;

/**
 * A Evaluation.
 */
@Entity
@Table(name = "evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evaluation")
public class Evaluation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "billable")
    private Boolean billable;

    @Column(name = "json_template")
    private String jsonTemplate;

    @Column(name = "only_one")
    private Boolean onlyOne;

    @Column(name = "evaluation_template_id")
    private Long evaluationTemplateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EvaluationStatus status;

    @ManyToOne
    private TypePatientProcess typePatientProcess;

    @ManyToOne
    private TypeEvaluation typeEvaluation;

    @ManyToOne
    private EvaluationContent evaluationContent;

    @JoinColumn(name = "chart_id", referencedColumnName="ID", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @Column(name = "json_data")
    private String jsonData;

    @Column(name = "roles_sign")
    private String rolesSign;

    @Column(name = "roles_review")
    private String rolesReview;

    @Column(name = "patient_signature")
    private Boolean patientSignature;

    @Column(name = "json_data_tmp")
    private String jsonDataTmp;

    @Column(name = "json_template_tmp")
    private String jsonTemplateTmp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Evaluation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Evaluation enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isBillable() {
        return billable;
    }

    public Evaluation billable(Boolean billable) {
        this.billable = billable;
        return this;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public Evaluation jsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
        return this;
    }

    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public Boolean isOnlyOne() {
        return onlyOne;
    }

    public Evaluation onlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
        return this;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Long getEvaluationTemplateId() {
        return evaluationTemplateId;
    }

    public Evaluation evaluationTemplateId(Long evaluationTemplateId) {
        this.evaluationTemplateId = evaluationTemplateId;
        return this;
    }

    public void setEvaluationTemplateId(Long evaluationTemplateId) {
        this.evaluationTemplateId = evaluationTemplateId;
    }

    public EvaluationStatus getStatus() {
        return status;
    }

    public Evaluation status(EvaluationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EvaluationStatus status) {
        this.status = status;
    }

    public TypePatientProcess getTypePatientProcess() {
        return typePatientProcess;
    }

    public Evaluation typePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
        return this;
    }

    public void setTypePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
    }

    public TypeEvaluation getTypeEvaluation() {
        return typeEvaluation;
    }

    public Evaluation typeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
        return this;
    }

    public void setTypeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }

    public EvaluationContent getEvaluationContent() {
        return evaluationContent;
    }

    public Evaluation evaluationContent(EvaluationContent evaluationContent) {
        this.evaluationContent = evaluationContent;
        return this;
    }

    public void setEvaluationContent(EvaluationContent evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public Chart getChart() {
        return chart;
    }

    public Evaluation chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
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

    public Boolean isPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(Boolean patientSignature) {
        this.patientSignature = patientSignature;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evaluation evaluation = (Evaluation) o;
        if(evaluation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evaluation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enabled='" + enabled + "'" +
            ", billable='" + billable + "'" +
            ", jsonTemplate='" + jsonTemplate + "'" +
            ", onlyOne='" + onlyOne + "'" +
            ", evaluationTemplateId='" + evaluationTemplateId + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public FormVO evaluationToFormVO(Evaluation evaluation, EvaluationSignatureVO evaluationSignature) {
        FormVO formVO = new FormVO();
        formVO.setId(evaluation.getId());
        formVO.setName(evaluation.getName());
        formVO.setStatus(evaluation.getStatus().toString());
        formVO.setOnlyOne(evaluation.isOnlyOne());
        formVO.setPatientSignature((evaluationSignature != null) ? evaluationSignature.getPatientSignature() : new Signature());
        formVO.setCreatedDate(evaluation.getCreatedDate());
        formVO.setCreatedBy(evaluation.getCreatedBy());
        formVO.setEvaluationTemplateId(evaluation.getEvaluationTemplateId());

        formVO.setType("Evaluation");

        return formVO;
    }

    public String getJsonDataTmp() {
        return jsonDataTmp;
    }

    public void setJsonDataTmp(String jsonDataTmp) {
        this.jsonDataTmp = jsonDataTmp;
    }

    public String getJsonTemplateTmp() {
        return jsonTemplateTmp;
    }

    public void setJsonTemplateTmp(String jsonTemplateTmp) {
        this.jsonTemplateTmp = jsonTemplateTmp;
    }
}
