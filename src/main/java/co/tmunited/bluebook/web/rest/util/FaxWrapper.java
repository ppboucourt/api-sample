package co.tmunited.bluebook.web.rest.util;

/**
 * Created by adriel on 7/21/17.
 */
public class FaxWrapper {
    private Long patientMedicationId;
    private String serviceProviderName;
    private String faxNumber;
    private String domain;
    private String token;

    private Long chartId;

    public FaxWrapper() {
    }

    public FaxWrapper(Long patientEvaluationId, String serviceProviderName, String faxNumber, String domain) {
        this.patientMedicationId = patientEvaluationId;
        this.serviceProviderName = serviceProviderName;
        this.faxNumber = faxNumber;
        this.domain = domain;
    }

    public Long getPatientMedicationId() {
        return patientMedicationId;
    }

    public void setPatientMedicationId(Long patientMedicationId) {
        this.patientMedicationId = patientMedicationId;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
