package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.PatientOrder;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by maykel on 6/16/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientScheduleDataVO implements Serializable{

    String firstName;
    String lastName;
    String middleName;
    Long patientId;
    ZonedDateTime dob;
    String barcode;
    LocalDate scheduleDate;
    boolean collected;
    String pFirstName;
    String pLastName;
    Long orderId;

    public PatientScheduleDataVO(String firstName, String lastName, String middleName, Long patientId,
                                 ZonedDateTime dob, String barcode, LocalDate scheduleDate, boolean collected,
                                 String pFirstName, String pLastName, Long orderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.patientId = patientId;
        this.dob = dob;
        this.barcode = barcode;
        this.scheduleDate = scheduleDate;
        this.collected = collected;
        this.pFirstName = pFirstName;
        this.pLastName = pLastName;
        this.orderId = orderId;
    }

    public PatientScheduleDataVO(String firstName, String lastName, String middleName, Long patientId,
                                 ZonedDateTime dob, String barcode, LocalDate scheduleDate, boolean collected,
                                 Long orderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.patientId = patientId;
        this.dob = dob;
        this.barcode = barcode;
        this.scheduleDate = scheduleDate;
        this.collected = collected;
        this.orderId = orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getpFirstName() {
        return pFirstName;
    }

    public void setpFirstName(String pFirstName) {
        this.pFirstName = pFirstName;
    }

    public String getpLastName() {
        return pLastName;
    }

    public void setpLastName(String pLastName) {
        this.pLastName = pLastName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatientScheduleDataVO patientScheduleDataVO = (PatientScheduleDataVO) o;
        if (patientScheduleDataVO.barcode == null || barcode == null) {
            return false;
        }
        return Objects.equals(barcode, patientScheduleDataVO.barcode);
    }
}
