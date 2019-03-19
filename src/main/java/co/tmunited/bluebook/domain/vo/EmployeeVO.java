package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Employee;
import co.tmunited.bluebook.domain.TypeEmployee;
import co.tmunited.bluebook.domain.User;
import co.tmunited.bluebook.domain.enumeration.Gender;

import java.io.Serializable;

/**
 * Created by Tech Support on 8/10/2017.
 */
public class EmployeeVO implements Serializable {

    private Long id;

    private String email;

    private String mobile;

    private String npiNumber;

    private String deaNumber;

    private String status;

    private String firstName;

    private String lastName;

    private String title;

    private String pin;

    private String signature;

    private Gender gender;

    private User user;

    private TypeEmployee typeEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNpiNumber() {
        return npiNumber;
    }

    public void setNpiNumber(String npiNumber) {
        this.npiNumber = npiNumber;
    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public void setDeaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }

    public void setTypeEmployee(TypeEmployee typeEmployee) {
        this.typeEmployee = typeEmployee;
    }

    public EmployeeVO(Employee employee) {
        this.id = employee.getId();
        this.email = employee.getEmail();
        this.mobile = employee.getMobile();
        this.npiNumber = employee.getNpiNumber();
        this.deaNumber = employee.getDeaNumber();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.title = employee.getTitle();
        this.pin = employee.getPin();
        this.gender = employee.getGender();
    }
}
