package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.vo.FormVO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Forms.
 */
@Entity
@Table(name = "forms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "forms")
public class Forms extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "patient_signature_required")
    private Boolean patientSignatureRequired;

    @Column(name = "guarantor_signature_required")
    private Boolean guarantorSignatureRequired;

    @Column(name = "allow_attachment")
    private Boolean allowAttachment;

    @Column(name = "allow_revocation")
    private Boolean allowRevocation;

    @Column(name = "status")
    private String status;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "expires_days")
    private Integer expiresDays;

    @Column(name = "only_one_perpatient")
    private Boolean onlyOnePerpatient;

    @Column(name = "expires")
    private Boolean expires;

    @Column(name = "content")
    private String content;

    @Column(name = "content_html")
    private String contentHtml;

    @ManyToOne(fetch = FetchType.LAZY)
    private TypeFormRules typeFormRules;

    @ManyToOne
    private TypePatientProcess typePatientProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    private Laboratories laboratories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id",referencedColumnName = "id")
    private Facility facility;


    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable( name = "form_level_care",
        joinColumns = {@JoinColumn(name = "forms_id", referencedColumnName = "ID")},
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

    public Forms name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPatientSignatureRequired() {
        return patientSignatureRequired;
    }

    public Forms patientSignatureRequired(Boolean patientSignatureRequired) {
        this.patientSignatureRequired = patientSignatureRequired;
        return this;
    }

    public void setPatientSignatureRequired(Boolean patientSignatureRequired) {
        this.patientSignatureRequired = patientSignatureRequired;
    }

    public Boolean isGuarantorSignatureRequired() {
        return guarantorSignatureRequired;
    }

    public Forms guarantorSignatureRequired(Boolean guarantorSignatureRequired) {
        this.guarantorSignatureRequired = guarantorSignatureRequired;
        return this;
    }

    public void setGuarantorSignatureRequired(Boolean guarantorSignatureRequired) {
        this.guarantorSignatureRequired = guarantorSignatureRequired;
    }

    public Boolean isAllowAttachment() {
        return allowAttachment;
    }

    public Forms allowAttachment(Boolean allowAttachment) {
        this.allowAttachment = allowAttachment;
        return this;
    }

    public void setAllowAttachment(Boolean allowAttachment) {
        this.allowAttachment = allowAttachment;
    }

    public Boolean isAllowRevocation() {
        return allowRevocation;
    }

    public Forms allowRevocation(Boolean allowRevocation) {
        this.allowRevocation = allowRevocation;
        return this;
    }

    public void setAllowRevocation(Boolean allowRevocation) {
        this.allowRevocation = allowRevocation;
    }

    public String getStatus() {
        return status;
    }

    public Forms status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Forms enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getExpiresDays() {
        return expiresDays;
    }

    public Forms expiresDays(Integer expiresDays) {
        this.expiresDays = expiresDays;
        return this;
    }

    public void setExpiresDays(Integer expiresDays) {
        this.expiresDays = expiresDays;
    }

    public Boolean isOnlyOnePerpatient() {
        return onlyOnePerpatient;
    }

    public Forms onlyOnePerpatient(Boolean onlyOnePerpatient) {
        this.onlyOnePerpatient = onlyOnePerpatient;
        return this;
    }

    public void setOnlyOnePerpatient(Boolean onlyOnePerpatient) {
        this.onlyOnePerpatient = onlyOnePerpatient;
    }

    public Boolean isExpires() {
        return expires;
    }

    public Forms expires(Boolean expires) {
        this.expires = expires;
        return this;
    }

    public void setExpires(Boolean expires) {
        this.expires = expires;
    }

    public String getContent() {
        return content;
    }

    public Forms content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TypeFormRules getTypeFormRules() {
        return typeFormRules;
    }

    public Forms typeFormRules(TypeFormRules typeFormRules) {
        this.typeFormRules = typeFormRules;
        return this;
    }

    public void setTypeFormRules(TypeFormRules typeFormRules) {
        this.typeFormRules = typeFormRules;
    }

    public TypePatientProcess getTypePatientProcess() {
        return typePatientProcess;
    }

    public Forms typePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
        return this;
    }

    public void setTypePatientProcess(TypePatientProcess typePatientProcess) {
        this.typePatientProcess = typePatientProcess;
    }

    public Laboratories getLaboratories() {
        return laboratories;
    }

    public Forms laboratories(Laboratories laboratories) {
        this.laboratories = laboratories;
        return this;
    }

    public void setLaboratories(Laboratories laboratories) {
        this.laboratories = laboratories;
    }

    public Facility getFacility() {
        return facility;
    }

    public Forms facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
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
        Forms forms = (Forms) o;
        if(forms.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, forms.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Forms{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", patientSignatureRequired='" + patientSignatureRequired + "'" +
            ", guarantorSignatureRequired='" + guarantorSignatureRequired + "'" +
            ", allowAttachment='" + allowAttachment + "'" +
            ", allowRevocation='" + allowRevocation + "'" +
            ", status='" + status + "'" +
            ", enabled='" + enabled + "'" +
            ", expiresDays='" + expiresDays + "'" +
            ", onlyOnePerpatient='" + onlyOnePerpatient + "'" +
            ", expires='" + expires + "'" +
            ", content='" + content + "'" +
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

    public Forms loadAutomatic(Boolean enabled) {
        this.loadAutomatic = enabled;
        return this;
    }

    public void setLoadAutomatic(Boolean enabled) {
        this.loadAutomatic = enabled;
    }

    public FormVO formToFormVO(Forms form) {

        FormVO formVO = new FormVO();
        formVO.setId(form.getId());
        formVO.setName(form.getName());
        formVO.setStatus(form.getStatus().toString());
        formVO.setPatientSignatureRequired(form.isPatientSignatureRequired());
        formVO.setGuarantorSignatureRequired(form.isGuarantorSignatureRequired());
        formVO.setAllowAttachment(form.isAllowAttachment());
        formVO.setOnlyOne(form.isOnlyOnePerpatient());
        formVO.setType("Consent");

        return formVO;
    }
}
