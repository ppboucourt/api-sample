package co.tmunited.bluebook.domain.vo;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class ShiftVO implements Serializable {

    private long id;

    private String firstName;
    private String lastName;
    private ZonedDateTime createdDate;
    private Long employeeId;

    public ShiftVO(long id, String firtName, String lastName, ZonedDateTime createdDate, Long employeeId) {

        this.id = id;
        this.firstName = firtName;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.employeeId = employeeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
