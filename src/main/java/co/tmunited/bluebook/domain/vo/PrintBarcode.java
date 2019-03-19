package co.tmunited.bluebook.domain.vo;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by maykel on 6/20/2017.
 */
public class PrintBarcode {
    Long orderId;
    LocalDate date;
    String tube;
    String patientName;
    LocalDate dob;
    String barcode;
    String machine;
    String account;

    public PrintBarcode(Long orderId, LocalDate date, String tube, String lastName, String firstName, String middleInitial, ZonedDateTime dob, String barcode, String account) {
        this.orderId = orderId;
        this.date = date;
        this.tube = tube;
        this.patientName = firstName + (middleInitial == null ? " " : " " + middleInitial + " ") + lastName;
        this.dob = dob.toLocalDate();
        this.barcode = barcode;
        this.account = account;
    }

    public PrintBarcode(Long orderId, LocalDate date, String tube, String machine, String barcode, String account) {
        this.orderId = orderId;
        this.date = date;
        this.tube = tube;
        this.machine = machine;
        this.barcode = barcode;
        this.account = account;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTube() {
        return tube;
    }

    public void setTube(String tube) {
        this.tube = tube;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }
}
