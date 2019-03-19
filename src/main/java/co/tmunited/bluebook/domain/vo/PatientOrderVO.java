package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.PatientOrder;

import java.time.ZonedDateTime;

/**
 * Created by adriel on 7/13/17.
 */
public class PatientOrderVO {

    private Long patientOrderId;
    private String barCode;
    private boolean collected;
    private String physician;

    private Long chartId;


    private String patientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private ZonedDateTime dateBirth;


    public Long getPatientOrderId() {
        return patientOrderId;
    }

    public void setPatientOrderId(Long patientOrderId) {
        this.patientOrderId = patientOrderId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(ZonedDateTime dateBirth) {
        this.dateBirth = dateBirth;
    }
}
