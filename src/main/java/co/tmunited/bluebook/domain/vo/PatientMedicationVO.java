package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Allergies;
import co.tmunited.bluebook.domain.enumeration.Gender;
import co.tmunited.bluebook.domain.enumeration.PatientMedicationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by alien and adriel
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientMedicationVO {

    private Long id;
    private String prescriberName;
    private String prescriberAddress;
    private String npi;
    private String lic;
    private String deaNumber;
    private String physicianTitle;
    private Long physicianId;

    private Boolean asNeeded;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String physicianFirstName;
    private String physicianLastName;
    private PatientMedicationStatus status;
    private Boolean signed;

    private String enteredEmployeeName;
    private String enteredEmployeeTitle;
    private ZonedDateTime enteredMedDate;

    //Patient data
    private String patientName;
    private ZonedDateTime patientBirthday;
    private String patientPhone;
    private Gender patientGender;
    private String patientAddress;
    private String mrNumber;
    private Long chartId;
    private List<Allergies> allergiesList;

    private Float quantity;
    private Integer days;
    private String signedBy;
    private ZonedDateTime signedDate;
    //Facility data
    private String facilityName;
    private String facilityPhone;
    private String facilityFax;
    private String facilityAddrStreet;
    private String facilityAddrCity;
    private String facilityAddrState;
    private String facilityAddrZip;
    private String facilityAddrCounty;
    private String facilityWebsite;

    //Medication
    private String medication;
    private ZonedDateTime medStartingDate;
    private ZonedDateTime medEndDate;
    private Long medHours;
    private String medDose;
    private String medDosegeForm;
    private String medAmountToDispense;
    private String medRefills;
    private String medVia;
    private String medRoute;
    private String medJustification;

    //Insurance
    private String insuranceName;
    private String policyNumber;
    private String groupNumber;
    private String groupName;
    private String planNumber;
    private LocalDate insuranceDob;
    private String insurancePhone;
    private String insuranceType;
    private String insuranceRelation;
    private String insuranceSubscriber;


    public PatientMedicationVO(Long id, String medication, Boolean asNeeded, ZonedDateTime startDate, ZonedDateTime endDate, String physicianFirstName, String physicianLastName) {
        this.id = id;
        this.medication = medication;
        this.asNeeded = asNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.physicianFirstName = physicianFirstName;
        this.physicianLastName = physicianLastName;
    }

    public PatientMedicationVO(Long id, String medication, Boolean asNeeded, ZonedDateTime startDate, ZonedDateTime endDate, String physicianFirstName, String physicianLastName, PatientMedicationStatus status) {
        this.id = id;
        this.medication = medication;
        this.asNeeded = asNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.physicianFirstName = physicianFirstName;
        this.physicianLastName = physicianLastName;
        this.status = status;
    }

    public PatientMedicationVO(Long id, String medication, Boolean asNeeded, ZonedDateTime startDate, ZonedDateTime endDate, String physicianFirstName, String physicianLastName, PatientMedicationStatus status, Boolean signed) {
        this.id = id;
        this.medication = medication;
        this.asNeeded = asNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.physicianFirstName = physicianFirstName;
        this.physicianLastName = physicianLastName;
        this.status = status;
        this.signed = signed;
    }


    public PatientMedicationVO(Long id, String medication, Boolean asNeeded, ZonedDateTime startDate, ZonedDateTime endDate, String physicianFirstName, String physicianLastName, PatientMedicationStatus status, Boolean signed, Long physicianId) {
        this.id = id;
        this.medication = medication;
        this.asNeeded = asNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.physicianFirstName = physicianFirstName;
        this.physicianLastName = physicianLastName;
        this.status = status;
        this.signed = signed;
        this.physicianId = physicianId;
    }

    public PatientMedicationVO(Long id, String prescriberName, String npi, String deaNumber, String physicianTitle,
                               String enteredEmployeeName, String enteredEmployeeTitle, ZonedDateTime enteredMedDate,
                               String patientName, ZonedDateTime patientBirthday, String patientPhone, Gender patientGender, String patientAddress,
                               String mrNumber, Long chartId, String facilityName,
                               String facilityPhone, String facilityAddrStreet, String facilityAddrCity, String facilityAddrState,
                               String facilityAddrZip, String facilityAddrCounty, String facilityWebsite,
                               String medication, String medDose, String medDosegeForm, String medAmountToDispense, String medRefills,
                               String medVia, String medRoute, String medJustification, ZonedDateTime medStartingDate, ZonedDateTime medEndDate,
                               Long medHours, String insuranceName, String policyNumber, String groupNumber,
                               String groupName, String planNumber, LocalDate insuranceDob, String insurancePhone, String insuranceType,
                               String insuranceRelation, String insuranceSubscriber) {
        this.id = id;
        this.prescriberName = prescriberName;
//        this.prescriberAddress = prescriberAddress;
        this.npi = npi;
//        this.lic = lic;
        this.deaNumber = deaNumber;
        this.physicianTitle = physicianTitle;
        this.enteredEmployeeName = enteredEmployeeName;
        this.enteredEmployeeTitle = enteredEmployeeTitle;
        this.patientName = patientName;
        this.patientBirthday = patientBirthday;
        this.patientPhone = patientPhone;
        this.patientGender = patientGender;
        this.patientAddress = patientAddress;
        this.enteredMedDate = enteredMedDate;
        this.mrNumber = mrNumber;
        this.chartId = chartId;
        this.facilityName = facilityName;
        this.facilityPhone = facilityPhone;
        this.facilityAddrStreet = facilityAddrStreet;
        this.facilityAddrCity = facilityAddrCity;
        this.facilityAddrState = facilityAddrState;
        this.facilityAddrZip = facilityAddrZip;
        this.facilityAddrCounty = facilityAddrCounty;
        this.facilityWebsite = facilityWebsite;
        this.medication = medication;
        this.medDose = medDose;
        this.medDosegeForm = medDosegeForm;
        this.medAmountToDispense = medAmountToDispense;
        this.medRefills = medRefills;
        this.medVia = medVia;
        this.medRoute = medRoute;
        this.medJustification = medJustification;
        this.medStartingDate = medStartingDate;
        this.medEndDate = medEndDate;
        this.medHours = medHours;
        this.insuranceName = insuranceName;
        this.policyNumber = policyNumber;
        this.groupNumber = groupNumber;
        this.groupName = groupName;
        this.planNumber = planNumber;
        this.insuranceDob = insuranceDob;
        this.insurancePhone = insurancePhone;
        this.insuranceType = insuranceType;
        this.insuranceRelation = insuranceRelation;
        this.insuranceSubscriber = insuranceSubscriber;
    }

    public Long getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(Long physicianId) {
        this.physicianId = physicianId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrescriberName() {
        return prescriberName;
    }

    public void setPrescriberName(String prescriberName) {
        this.prescriberName = prescriberName;
    }

    public String getPrescriberAddress() {
        return prescriberAddress;
    }

    public void setPrescriberAddress(String prescriberAddress) {
        this.prescriberAddress = prescriberAddress;
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public String getLic() {
        return lic;
    }

    public void setLic(String lic) {
        this.lic = lic;
    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public void setDeaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public ZonedDateTime getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(ZonedDateTime patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public ZonedDateTime getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(ZonedDateTime signedDate) {
        this.signedDate = signedDate;
    }

    public Gender getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Gender patientGender) {
        this.patientGender = patientGender;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityPhone() {
        return facilityPhone;
    }

    public void setFacilityPhone(String facilityPhone) {
        this.facilityPhone = facilityPhone;
    }

    public String getFacilityFax() {
        return facilityFax;
    }

    public void setFacilityFax(String facilityFax) {
        this.facilityFax = facilityFax;
    }

    public String getFacilityAddrStreet() {
        return facilityAddrStreet;
    }

    public void setFacilityAddrStreet(String facilityAddrStreet) {
        this.facilityAddrStreet = facilityAddrStreet;
    }

    public String getFacilityAddrCity() {
        return facilityAddrCity;
    }

    public void setFacilityAddrCity(String facilityAddrCity) {
        this.facilityAddrCity = facilityAddrCity;
    }

    public String getFacilityAddrState() {
        return facilityAddrState;
    }

    public void setFacilityAddrState(String facilityAddrState) {
        this.facilityAddrState = facilityAddrState;
    }

    public String getFacilityAddrZip() {
        return facilityAddrZip;
    }

    public void setFacilityAddrZip(String facilityAddrZip) {
        this.facilityAddrZip = facilityAddrZip;
    }

    public String getFacilityWebsite() {
        return facilityWebsite;
    }

    public void setFacilityWebsite(String facilityWebsite) {
        this.facilityWebsite = facilityWebsite;
    }

    public String getFacilityAddrCounty() {
        return facilityAddrCounty;
    }

    public void setFacilityAddrCounty(String facilityAddrCounty) {
        this.facilityAddrCounty = facilityAddrCounty;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public ZonedDateTime getMedStartingDate() {
        return medStartingDate;
    }

    public void setMedStartingDate(ZonedDateTime medStartingDate) {
        this.medStartingDate = medStartingDate;
    }

    public ZonedDateTime getMedEndDate() {
        return medEndDate;
    }

    public void setMedEndDate(ZonedDateTime medEndDate) {
        this.medEndDate = medEndDate;
    }

    public Long getMedHours() {
        return medHours;
    }

    public void setMedHours(Long medHours) {
        this.medHours = medHours;
    }

    public String getMedDose() {
        return medDose;
    }

    public void setMedDose(String medDose) {
        this.medDose = medDose;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getEnteredEmployeeName() {
        return enteredEmployeeName;
    }

    public void setEnteredEmployeeName(String enteredEmployeeName) {
        this.enteredEmployeeName = enteredEmployeeName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public LocalDate getInsuranceDob() {
        return insuranceDob;
    }

    public void setInsuranceDob(LocalDate insuranceDob) {
        this.insuranceDob = insuranceDob;
    }

    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceRelation() {
        return insuranceRelation;
    }

    public void setInsuranceRelation(String insuranceRelation) {
        this.insuranceRelation = insuranceRelation;
    }

    public String getInsuranceSubscriber() {
        return insuranceSubscriber;
    }

    public void setInsuranceSubscriber(String insuranceSubscriber) {
        this.insuranceSubscriber = insuranceSubscriber;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getMrNumber() {
        return mrNumber;
    }

    public void setMrNumber(String mrNumber) {
        this.mrNumber = mrNumber;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public List<Allergies> getAllergiesList() {
        return allergiesList;
    }

    public void setAllergiesList(List<Allergies> allergiesList) {
        this.allergiesList = allergiesList;
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

    public String getMedDosegeForm() {
        return medDosegeForm;
    }

    public void setMedDosegeForm(String medDosegeForm) {
        this.medDosegeForm = medDosegeForm;
    }

    public String getMedAmountToDispense() {
        return medAmountToDispense;
    }

    public void setMedAmountToDispense(String medAmountToDispense) {
        this.medAmountToDispense = medAmountToDispense;
    }

    public String getMedRefills() {
        return medRefills;
    }

    public void setMedRefills(String medRefills) {
        this.medRefills = medRefills;
    }

    public String getMedVia() {
        return medVia;
    }

    public void setMedVia(String medVia) {
        this.medVia = medVia;
    }

    public String getEnteredEmployeeTitle() {
        return enteredEmployeeTitle;
    }

    public void setEnteredEmployeeTitle(String enteredEmployeeTitle) {
        this.enteredEmployeeTitle = enteredEmployeeTitle;
    }

    public ZonedDateTime getEnteredMedDate() {
        return enteredMedDate;
    }

    public void setEnteredMedDate(ZonedDateTime enteredMedDate) {
        this.enteredMedDate = enteredMedDate;
    }

    public String getPhysicianTitle() {
        return physicianTitle;
    }

    public void setPhysicianTitle(String physicianTitle) {
        this.physicianTitle = physicianTitle;
    }

    public String getMedRoute() {
        return medRoute;
    }

    public void setMedRoute(String medRoute) {
        this.medRoute = medRoute;
    }

    public String getMedJustification() {
        return medJustification;
    }

    public void setMedJustification(String medJustification) {
        this.medJustification = medJustification;
    }
}
