package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.util.FormJson;
import co.tmunited.bluebook.domain.vo.FormVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A EvaluationTemplate.
 */
@Entity
@Table(name = "evaluation_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evaluationtemplate")
public class EvaluationTemplate extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "patient_signature")
    private Boolean patientSignature;

    @Column(name = "only_one")
    private Boolean onlyOne;

    @Column(name = "billable")
    private Boolean billable;

    @Column(name = "force_staff_signature")
    private Boolean forceStaffSignature;

    @Column(name = "force_staff_review_signature")
    private Boolean forceStaffReviewSignature;

    @Column(name = "json_template")
    private String jsonTemplate;

    @ManyToOne
    private TypePatientProcess typePatientProcess;

    @ManyToOne
    private TypeEvaluation typeEvaluation;

    @ManyToOne
    private EvaluationContent evaluationContent;

    @ManyToOne
    private Facility facility;

    @ManyToMany
    @JoinTable(
        name = "evaluation_template_staff_signature_authority",
        joinColumns = {@JoinColumn(name = "evaluation_template_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> staffSignatureAuthority = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "evaluation_template_staff_review_signature_authority",
        joinColumns = {@JoinColumn(name = "evaluation_template_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> staffReviewSignatureAuthorities = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable( name = "evaluation_template_level_care",
        joinColumns = {@JoinColumn(name = "evaluation_templates_id", referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name = "level_cares_id", referencedColumnName = "ID")})
    private Set<TypeLevelCare> typeLevelCares = new HashSet<>();

    @Column(name = "load_automatic")
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

    public EvaluationTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public EvaluationTemplate enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isPatientSignature() {
        return patientSignature;
    }

    public EvaluationTemplate patientSignature(Boolean patientSignature) {
        this.patientSignature = patientSignature;
        return this;
    }

    public void setPatientSignature(Boolean patientSignature) {
        this.patientSignature = patientSignature;
    }

    public Boolean isOnlyOne() {
        return onlyOne;
    }

    public EvaluationTemplate onlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
        return this;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Boolean isBillable() {
        return billable;
    }

    public EvaluationTemplate billable(Boolean billable) {
        this.billable = billable;
        return this;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean isForceStaffSignature() {
        return forceStaffSignature;
    }

    public EvaluationTemplate forceStaffSignature(Boolean forceStaffSignature) {
        this.forceStaffSignature = forceStaffSignature;
        return this;
    }

    public void setForceStaffSignature(Boolean forceStaffSignature) {
        this.forceStaffSignature = forceStaffSignature;
    }

    public Boolean isForceStaffReviewSignature() {
        return forceStaffReviewSignature;
    }

    public EvaluationTemplate forceStaffReviewSignature(Boolean forceStaffReviewSignature) {
        this.forceStaffReviewSignature = forceStaffReviewSignature;
        return this;
    }

    public void setForceStaffReviewSignature(Boolean forceStaffReviewSignature) {
        this.forceStaffReviewSignature = forceStaffReviewSignature;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public EvaluationTemplate jsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
        return this;
    }

    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public TypePatientProcess getTypePatientProcess() {
        return typePatientProcess;
    }

    public EvaluationTemplate typePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
        return this;
    }

    public void setTypePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
    }

    public TypeEvaluation getTypeEvaluation() {
        return typeEvaluation;
    }

    public EvaluationTemplate typeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
        return this;
    }

    public void setTypeEvaluation(TypeEvaluation typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }

    public EvaluationContent getEvaluationContent() {
        return evaluationContent;
    }

    public EvaluationTemplate evaluationContent(EvaluationContent evaluationContent) {
        this.evaluationContent = evaluationContent;
        return this;
    }

    public void setEvaluationContent(EvaluationContent evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public Set<Authority> getStaffReviewSignatureAuthorities() {
        return staffReviewSignatureAuthorities;
    }

    public void setStaffReviewSignatureAuthorities(Set<Authority> staffReviewSignatureAuthorities) {
        this.staffReviewSignatureAuthorities = staffReviewSignatureAuthorities;
    }

    public Set<Authority> getStaffSignatureAuthority() {
        return staffSignatureAuthority;
    }

    public void setStaffSignatureAuthority(Set<Authority> staffSignatureAuthority) {
        this.staffSignatureAuthority = staffSignatureAuthority;
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
        EvaluationTemplate evaluationTemplate = (EvaluationTemplate) o;
        if(evaluationTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evaluationTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EvaluationTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enabled='" + enabled + "'" +
            ", patientSignature='" + patientSignature + "'" +
            ", onlyOne='" + onlyOne + "'" +
            ", billable='" + billable + "'" +
            ", forceStaffSignature='" + forceStaffSignature + "'" +
            ", forceStaffReviewSignature='" + forceStaffReviewSignature + "'" +
            ", jsonTemplate='" + jsonTemplate + "'" +
            ", loadAutomatic='" + loadAutomatic + "'" +
            '}';
    }

    public Set<TypeLevelCare> getTypeLevelCares() {
        return typeLevelCares;
    }

    public void setTypeLevelCares(Set<TypeLevelCare> typeLevelCares) {
        this.typeLevelCares = typeLevelCares;
    }

    public Boolean isLoadAutomatic() {
        return loadAutomatic;
    }

    public EvaluationTemplate loadAutomatic(Boolean enabled) {
        this.loadAutomatic = enabled;
        return this;
    }

    public void setLoadAutomatic(Boolean enabled) {
        this.loadAutomatic = enabled;
    }

    public FormVO evaluationTemplateToFormVO(EvaluationTemplate evaluationTemplate) {


        FormVO formVO = new FormVO();
        formVO.setId(evaluationTemplate.getId());
        formVO.setName(evaluationTemplate.getName());
        formVO.setPatientSignatureRequired(evaluationTemplate.isPatientSignature());
        formVO.setOnlyOne(evaluationTemplate.isOnlyOne());
        formVO.setType("Evaluation");

        return formVO;
    }
}
