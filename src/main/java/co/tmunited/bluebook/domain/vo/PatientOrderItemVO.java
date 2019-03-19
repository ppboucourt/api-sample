package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * A PatientOrderItemVO.
 */
public class PatientOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private boolean collected;

    private String groupNumberId;

    private String vendor;

    private String groupNumberSource;

    private boolean sent;

    private LocalDate collectionDate;

    private ZonedDateTime completed;

    private String forReview;

    private Long chartId;

    private Chart chart;

    private Set<Icd10> icd10s;

    private Patient patient;

    private Set<PatientMedication> patientMedications;

    private PatientOrderTest patientOrderTest;

    private String labCompendiumCode;

    private String signedBy;

    private String npiSignedBy;

    public PatientOrderItemVO(Long id, String groupNumberId, boolean sent, Long chartId, PatientOrderTest patientOrderTest,
                              String labCompendiumCode, String signedBy, String npiSignedBy, LocalDate collectionDate) {
        this.id = id;
        this.groupNumberId = groupNumberId;
        this.sent = sent;
        this.chartId = chartId;
        this.patientOrderTest = patientOrderTest;
        this.labCompendiumCode = labCompendiumCode;
        this.signedBy = signedBy;
        this.npiSignedBy = npiSignedBy;
        this.collectionDate = collectionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getGroupNumberId() {
        return groupNumberId;
    }

    public void setGroupNumberId(String groupNumberId) {
        this.groupNumberId = groupNumberId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getGroupNumberSource() {
        return groupNumberSource;
    }

    public void setGroupNumberSource(String groupNumberSource) {
        this.groupNumberSource = groupNumberSource;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public ZonedDateTime getCompleted() {
        return completed;
    }

    public void setCompleted(ZonedDateTime completed) {
        this.completed = completed;
    }

    public String getForReview() {
        return forReview;
    }

    public void setForReview(String forReview) {
        this.forReview = forReview;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Set<Icd10> getIcd10s() {
        return icd10s;
    }

    public void setIcd10s(Set<Icd10> icd10s) {
        this.icd10s = icd10s;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<PatientMedication> getPatientMedications() {
        return patientMedications;
    }

    public void setPatientMedications(Set<PatientMedication> patientMedications) {
        this.patientMedications = patientMedications;
    }

    public PatientOrderTest getPatientOrderTest() {
        return patientOrderTest;
    }

    public void setPatientOrderTest(PatientOrderTest patientOrderTest) {
        this.patientOrderTest = patientOrderTest;
    }

    public String getLabCompendiumCode() {
        return labCompendiumCode;
    }

    public void setLabCompendiumCode(String labCompendiumCode) {
        this.labCompendiumCode = labCompendiumCode;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public String getNpiSignedBy() {
        return npiSignedBy;
    }

    public void setNpiSignedBy(String npiSignedBy) {
        this.npiSignedBy = npiSignedBy;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }
}
