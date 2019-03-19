package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.PatientResultDet;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A PatientResultVO.
 */
public class PatientResultVO implements Serializable {

    private Long id;

    private String accessionNumber;

    private LocalDate collectionDate;

    private String patientName;

    private Set<PatientResultDet> patientResultDets;

    private String status;

    private boolean abnormal;

    private Integer totalFiles;

    public PatientResultVO(Long id, String accessionNumber, LocalDate collectionDate, String patientName, String status,
                           boolean abnormal) {
        this.id = id;
        this.accessionNumber = accessionNumber;
        this.collectionDate = collectionDate;
        this.patientName = patientName;
        this.status = status;
        this.abnormal = abnormal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Set<PatientResultDet> getPatientResultDets() {
        return patientResultDets;
    }

    public void setPatientResultDets(Set<PatientResultDet> patientResultDets) {
        this.patientResultDets = patientResultDets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAbnormal() {
        return abnormal;
    }

    public void setAbnormal(boolean abnormal) {
        this.abnormal = abnormal;
    }

    public Integer getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Integer totalFiles) {
        this.totalFiles = totalFiles;
    }
}
