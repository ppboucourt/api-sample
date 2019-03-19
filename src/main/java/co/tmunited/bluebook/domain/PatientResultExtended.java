package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;

/**
 * A PatientResultExtended.
 */
public class PatientResultExtended extends PatientResult {

    private static final long serialVersionUID = 1L;

    @Transient
    private String uuid;

    @Transient
    private String fileName;

    @Transient
    private String base64Pdf;

    @JsonProperty
    public String getBase64Pdf() {
        return base64Pdf;
    }

    @JsonProperty
    public void setBase64Pdf(String base64Pdf) {
        this.base64Pdf = base64Pdf;
    }

    public PatientResult getParent() {

        PatientResult patientResult = new PatientResult();
        patientResult.setAccount(getAccount());
        patientResult.setResultDets(getResultDets());
        patientResult.setAbnormal(isAbnormal());
        patientResult.setAccessionNumber(getAccessionNumber());
        patientResult.setcollectionDate(getcollectionDate());
        patientResult.setDob(getDob());
        patientResult.setPatientName(getPatientName());
        patientResult.setReviewedStatus(isReviewedStatus());
        patientResult.setStatus(getStatus());


        return patientResult;
    }

    @JsonProperty
    public String getFileName() {
        return fileName;
    }

    @JsonProperty
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty
    public String getUuid() {
        return uuid;
    }

    @JsonProperty
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
