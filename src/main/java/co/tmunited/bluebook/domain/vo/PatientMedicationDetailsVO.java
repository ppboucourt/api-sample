package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.enumeration.PatientMedicationStatus;

import java.time.ZonedDateTime;

/**
 * Created by adriel on 7/17/17.
 */
public class PatientMedicationDetailsVO {

    private Long pmId;//
    private Long pmpId;//
    private String medication;
    private Boolean asNeeded;
    private ZonedDateTime createDate;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String physicianFirstName;
    private String physicianLastName;
    private PatientMedicationStatus status;
    private Boolean signed;

    private String dispenseAmount;
    private String numberRefills;
    private String route;
    private String administerAmount;
    private String dosageForm;
    private String dose;
    private String frecuency;
    private String orderedBy;
    private String npi;
    private String dea;
    private String enteredBy;
    private ZonedDateTime enteredDate;
    private String via;

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public Long getPmpId() {
        return pmpId;
    }

    public void setPmpId(Long pmpId) {
        this.pmpId = pmpId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Boolean getAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(Boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPhysicianFirstName() {
        return physicianFirstName;
    }

    public void setPhysicianFirstName(String physicianFirstName) {
        this.physicianFirstName = physicianFirstName;
    }

    public String getPhysicianLastName() {
        return physicianLastName;
    }

    public void setPhysicianLastName(String physicianLastName) {
        this.physicianLastName = physicianLastName;
    }

    public PatientMedicationStatus getStatus() {
        return status;
    }

    public void setStatus(PatientMedicationStatus status) {
        this.status = status;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getDispenseAmount() {
        return dispenseAmount;
    }

    public void setDispenseAmount(String dispenseAmount) {
        this.dispenseAmount = dispenseAmount;
    }

    public String getNumberRefills() {
        return numberRefills;
    }

    public void setNumberRefills(String numberRefills) {
        this.numberRefills = numberRefills;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAdministerAmount() {
        return administerAmount;
    }

    public void setAdministerAmount(String administerAmount) {
        this.administerAmount = administerAmount;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(String frecuency) {
        this.frecuency = frecuency;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public String getDea() {
        return dea;
    }

    public void setDea(String dea) {
        this.dea = dea;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public ZonedDateTime getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(ZonedDateTime enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }
}
